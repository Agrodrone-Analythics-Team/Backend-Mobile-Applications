# schemas.py
from pydantic import BaseModel
from typing import List, Optional
from datetime import datetime


# Esquema para crear un usuario (recibe todos los datos del formulario)
class UserCreate(BaseModel):
    first_name: str
    last_name: str
    email: str
    gender: str
    address: str
    state: str
    city: str
    username: str
    password: str
    experience_years: int
    referral_code: Optional[str] = None
    role: str
    job_type: str
    production_system: str
    managed_animals: List[str]


# Esquema para responder (nunca devolver la contraseña)
class UserResponse(BaseModel):
    id: int
    username: str
    email: str
    first_name: str
    last_name: str

    class Config:
        from_attributes = True


# Esquema para el login
class Token(BaseModel):
    access_token: str
    token_type: str

# Esquema para el login (AÑADE ESTO)
class UserLogin(BaseModel):
    username: str
    password: str

# Esquema para solicitar reseteo de contraseña
class EmailSchema(BaseModel):
    email: str


# Esquema para confirmar el reseteo
class ResetPasswordSchema(BaseModel):
    email: str
    otp: str
    new_password: str

# Esquema para verificar solo el OTP
class OtpVerify(BaseModel):
    email: str
    otp: str

# Esquema para confirmar el reseteo
class ResetPasswordSchema(BaseModel):
    email: str
    otp: str
    new_password: str

# --- AÑADE ESTOS NUEVOS ESQUEMAS AL FINAL DEL ARCHIVO ---

# Esquema para un solo punto de coordenada
class Point(BaseModel):
    latitude: float
    longitude: float

# Esquema para crear un nuevo plan de vuelo
class FlightPlanCreate(BaseModel):
    name: Optional[str] = "Mi Plan de Vuelo"
    points: List[Point] # Espera una lista de puntos
    start_time: str
    end_time: str
    scan_frequency: int
    animal_count: int

# Esquema para devolver un plan de vuelo desde la API
class FlightPlanResponse(FlightPlanCreate):
    id: int
    owner_id: int
    created_at: datetime
    # --- AÑADE ESTA LÍNEA ---
    image_path: Optional[str] = None

    class Config:
        from_attributes = True # Reemplaza a orm_mode


class UserProfile(BaseModel):
    first_name: str
    address: Optional[str] = None
    city: Optional[str] = None
    state: Optional[str] = None

    class Config:
        from_attributes = True

