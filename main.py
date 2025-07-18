# main.py - VERSIÓN FINAL UNIFICADA
from typing import List
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy.orm import Session
import random
import time
import json
# --- NUEVAS IMPORTACIONES PARA DETECCIÓN ---
from PIL import Image
import numpy as np
from ultralytics import YOLO
from database import get_db
import auth

# --- Importaciones de nuestro proyecto ---
import models
import schemas
import auth
# main.py
from database import engine, get_db
from fastapi import FastAPI, Depends, HTTPException, UploadFile, File, Form
import shutil
import os

# ----------------------------------------------------
# CONFIGURACIÓN INICIAL DE LA APLICACIÓN
# ----------------------------------------------------

models.Base.metadata.create_all(bind=engine)
app = FastAPI(title="AgroDrone Analytics API")
# ... (Middleware y carga de modelo YOLO) ...
UPLOADS_DIR = "uploads"
os.makedirs(UPLOADS_DIR, exist_ok=True)
# -----------------------------------------
@app.get("/")
def read_root():
    return {"status": "ok", "message": "¡Bienvenido a la API de AgroDrone Analytics!"}
# -----------------------------------------
# Middleware para CORS (permite que tu app Flutter se conecte)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# --- CARGA DEL MODELO YOLO ---
# El modelo se carga una sola vez cuando el servidor se inicia.
# Asegúrate de que la ruta a tu modelo 'best.pt' sea correcta.
try:
    detection_model = YOLO("runs/detect/train/weights/best.pt")
    print("✅ Modelo YOLO cargado correctamente.")
except Exception as e:
    print(f"❌ Error al cargar el modelo YOLO: {e}")
    detection_model = None


# ----------------------------------------------------
# SECCIÓN 1: LÓGICA DE DETECCIÓN DE OBJETOS
# ----------------------------------------------------

def yolo_result_to_dict(results):
    detections = []
    if not results or not results[0].boxes:
        return detections

    for box in results[0].boxes:
        x1, y1, x2, y2 = box.xyxy[0].tolist()
        conf = float(box.conf[0])
        cls = int(box.cls[0])
        detections.append({
            "x1": x1, "y1": y1, "x2": x2, "y2": y2,
            "confidence": conf, "class_id": cls
        })
    return detections


@app.post("/detect/")
async def detect(file: UploadFile = File(...)):
    if not detection_model:
        raise HTTPException(status_code=500, detail="El modelo de detección no está disponible.")
    try:
        img = Image.open(file.file).convert("RGB")
        img_np = np.array(img)
        results = detection_model(img_np, conf=0.1)
        detections = yolo_result_to_dict(results)
        # La respuesta ahora es más simple
        return {"detections": detections, "type": "image"}
    except Exception as e:
        print(f"❌ Error detectando imagen: {e}")
        raise HTTPException(status_code=500, detail=f"Error al procesar la imagen: {e}")

    # ... (El resto de tus endpoints de usuarios y planes de vuelo se mantienen igual) ...

# ----------------------------------------------------
# SECCIÓN 2: LÓGICA DE USUARIOS
# ----------------------------------------------------
UPLOADS_DIR = "uploads"
os.makedirs(UPLOADS_DIR, exist_ok=True)
@app.get("/api/users/me", response_model=schemas.UserProfile)
def read_users_me(current_user: models.User = Depends(auth.get_current_user)):
    return current_user

# --- Endpoints de usuarios (estos se quedan exactamente igual) ---
@app.post("/api/register", response_model=schemas.UserResponse, status_code=201)
def register_user(user_data: schemas.UserCreate, db: Session = Depends(get_db)):
    # ... (código se mantiene igual)
    db_user_email = db.query(models.User).filter(models.User.email == user_data.email).first()
    if db_user_email:
        raise HTTPException(status_code=400, detail="El correo electrónico ya está registrado")
    db_user_username = db.query(models.User).filter(models.User.username == user_data.username).first()
    if db_user_username:
        raise HTTPException(status_code=400, detail="El nombre de usuario ya existe")
    hashed_password = auth.get_password_hash(user_data.password)
    new_user = models.User(
        first_name=user_data.first_name, last_name=user_data.last_name, email=user_data.email,
        gender=user_data.gender, address=user_data.address, state=user_data.state, city=user_data.city,
        username=user_data.username, hashed_password=hashed_password, experience_years=user_data.experience_years,
        referral_code=user_data.referral_code, role=user_data.role, job_type=user_data.job_type,
        production_system=user_data.production_system, managed_animals=json.dumps(user_data.managed_animals)
    )
    db.add(new_user)
    db.commit()
    db.refresh(new_user)
    return new_user


@app.post("/api/login", response_model=schemas.Token)
def login_for_access_token(form_data: schemas.UserLogin, db: Session = Depends(get_db)):
    # ... (código se mantiene igual)
    user = auth.authenticate_user(db, username=form_data.username, password=form_data.password)
    if not user:
        raise HTTPException(status_code=401, detail="Nombre de usuario o contraseña incorrectos",
                            headers={"WWW-Authenticate": "Bearer"})
    access_token = auth.create_access_token(data={"sub": user.username})
    return {"access_token": access_token, "token_type": "bearer"}


# ... (El resto de tus endpoints de recuperación de contraseña se quedan aquí)

@app.post("/api/request-password-reset")
def request_password_reset(email_data: schemas.EmailSchema, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(models.User.email == email_data.email).first()
    if not user:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    otp = str(random.randint(100000, 999999))
    user.otp_code = otp
    user.otp_expiry = int(time.time()) + 600
    db.commit()
    print(f"OTP para {user.email} es: {otp}")
    return {"message": "Se ha enviado un código de verificación.", "otp": otp}


# --- ¡NUEVO ENDPOINT SOLO PARA VERIFICAR EL OTP! ---
@app.post("/api/verify-otp")
def verify_otp(data: schemas.OtpVerify, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(models.User.email == data.email).first()
    if not user:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")

    # Comprueba si el OTP es correcto y no ha expirado
    if user.otp_code != data.otp or int(time.time()) > user.otp_expiry:
        raise HTTPException(status_code=400, detail="El código OTP es inválido o ha expirado")

    return {"message": "OTP verificado correctamente."}


# --- ENDPOINT ACTUALIZADO PARA CAMBIAR LA CONTRASEÑA ---
# Ahora se llama '/reset-password' y requiere el OTP de nuevo por seguridad.
@app.post("/api/reset-password")
def reset_password(data: schemas.ResetPasswordSchema, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(models.User.email == data.email).first()
    if not user:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")

    # Por seguridad, volvemos a verificar el OTP aquí
    if user.otp_code != data.otp or int(time.time()) > user.otp_expiry:
        raise HTTPException(status_code=400, detail="El código OTP es inválido o ha expirado")

    user.hashed_password = auth.get_password_hash(data.new_password)
    user.otp_code = None
    user.otp_expiry = None
    db.commit()

    return {"message": "Contraseña actualizada correctamente."}

# ----------------------------------------------------
# SECCIÓN 3: LÓGICA DE PLANES DE VUELO
# ----------------------------------------------------

@app.post("/api/flight-plans/", response_model=schemas.FlightPlanResponse, status_code=201)
def create_flight_plan(
    flight_plan_data: schemas.FlightPlanCreate,
    db: Session = Depends(get_db),
    current_user: models.User = Depends(auth.get_current_user) # <-- Endpoint protegido
):
    # Convierte la lista de objetos Point a una lista de diccionarios para guardarla como JSON
    points_as_dicts = [p.model_dump() for p in flight_plan_data.points]
    points_json_string = json.dumps(points_as_dicts)

    new_flight_plan = models.FlightPlan(
        name=flight_plan_data.name,
        points=points_json_string,
        start_time=flight_plan_data.start_time,
        end_time=flight_plan_data.end_time,
        scan_frequency=flight_plan_data.scan_frequency,
        animal_count=flight_plan_data.animal_count,
        owner_id=current_user.id  # Asigna el plan al usuario actual
    )
    db.add(new_flight_plan)
    db.commit()
    db.refresh(new_flight_plan)

    # --- AÑADE ESTA LÍNEA AQUÍ ---
    # Antes de devolver la respuesta, convierte el string de puntos a una lista
    new_flight_plan.points = json.loads(new_flight_plan.points)
    # -----------------------------

    return new_flight_plan


@app.get("/api/flight-plans/", response_model=List[schemas.FlightPlanResponse])
def get_all_user_flight_plans(
    db: Session = Depends(get_db),
    current_user: models.User = Depends(auth.get_current_user)
):
    flight_plans = db.query(models.FlightPlan).filter(
        models.FlightPlan.owner_id == current_user.id
    ).order_by(models.FlightPlan.created_at.desc()).all()

    for plan in flight_plans:
        plan.points = json.loads(plan.points)
    return flight_plans

# --- ¡NUEVO ENDPOINT! SOLO PARA HOME SCREEN (devuelve los últimos 2) ---
@app.get("/api/flight-plans/latest", response_model=List[schemas.FlightPlanResponse])
def get_latest_user_flight_plans(
    db: Session = Depends(get_db),
    current_user: models.User = Depends(auth.get_current_user)
):
    flight_plans = db.query(models.FlightPlan).filter(
        models.FlightPlan.owner_id == current_user.id
    ).order_by(models.FlightPlan.created_at.desc()).limit(2).all()

    for plan in flight_plans:
        plan.points = json.loads(plan.points)
    return flight_plans



@app.post("/api/flight-plans/{flight_plan_id}/details", response_model=schemas.FlightPlanResponse)
def upload_flight_plan_details(
    flight_plan_id: int,
    name: str = Form(...),
    file: UploadFile = File(...),
    db: Session = Depends(get_db),
    current_user: models.User = Depends(auth.get_current_user)
):
    # ... (código de este endpoint se mantiene igual)
    db_flight_plan = db.query(models.FlightPlan).filter(models.FlightPlan.id == flight_plan_id, models.FlightPlan.owner_id == current_user.id).first()
    if not db_flight_plan:
        raise HTTPException(status_code=404, detail="Plan de vuelo no encontrado")
    file_path = os.path.join(UPLOADS_DIR, file.filename)
    with open(file_path, "wb") as buffer:
        shutil.copyfileobj(file.file, buffer)
    db_flight_plan.name = name
    db_flight_plan.image_path = file_path
    db.commit()
    db.refresh(db_flight_plan)
    db_flight_plan.points = json.loads(db_flight_plan.points)
    return db_flight_plan
