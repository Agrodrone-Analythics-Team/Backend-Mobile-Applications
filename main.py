from fastapi import FastAPI, UploadFile, File
from fastapi.middleware.cors import CORSMiddleware
from PIL import Image
import numpy as np
from ultralytics import YOLO
# python -m uvicorn main:app --reload --host 0.0.0.0 --port 8000
#  yolo detect train data=datasets/data.yaml model=yolov10x.pt imgsz=960 epochs=100 batch=8 workers=24 device=0
#  yolo detect val data=datasets/data.yaml model=runs/detect/train/weights/best.pt split=test

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

model = YOLO("runs/detect/train/weights/best.pt")

def yolo_result_to_dict(results):
    detections = []
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
    try:
        img = Image.open(file.file).convert("RGB")
        img_np = np.array(img)
        print(f"Imagen recibida shape: {img_np.shape}, dtype: {img_np.dtype}")
        results = model(img_np, conf=0.1)  # Baja el threshold
        print("Boxes:", results[0].boxes)
        detections = yolo_result_to_dict(results)
        return {"detections": detections, "type": "image"}
    except Exception as e:
        print("❌ Error detectando imagen:", e)
        return {"detections": [], "type": "image", "error": str(e)}
