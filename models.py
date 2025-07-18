# models.py

from sqlalchemy import Column, Integer, String, DateTime, ForeignKey, TEXT
from sqlalchemy.orm import relationship
from datetime import datetime

from database import Base

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    first_name = Column(String(255), index=True)
    last_name = Column(String(255), index=True)
    email = Column(String(255), unique=True, index=True, nullable=False)
    gender = Column(String(50))
    address = Column(String(500))
    state = Column(String(100))
    city = Column(String(100))
    username = Column(String(255), unique=True, index=True, nullable=False)
    hashed_password = Column(String(255), nullable=False)
    experience_years = Column(Integer)
    referral_code = Column(String(100), nullable=True)
    role = Column(String(100))
    job_type = Column(String(100))
    production_system = Column(String(100))
    managed_animals = Column(String(1024), nullable=True)
    otp_code = Column(String(10), nullable=True)
    otp_expiry = Column(Integer, nullable=True)

    # La columna 'total_animals_detected' ha sido eliminada.

    flight_plans = relationship("FlightPlan", back_populates="owner")


class FlightPlan(Base):
    __tablename__ = "flight_plans"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(255), index=True, default="Mi Plan de Vuelo")
    points = Column(TEXT, nullable=False)
    start_time = Column(String(50))
    end_time = Column(String(50))
    scan_frequency = Column(Integer)
    animal_count = Column(Integer)
    image_path = Column(String(500), nullable=True)
    created_at = Column(DateTime, default=datetime.utcnow)
    owner_id = Column(Integer, ForeignKey("users.id"))

    owner = relationship("User", back_populates="flight_plans")
