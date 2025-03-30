from sqlalchemy import Column, Integer, String
from database import Base

class User(Base):
    __tablename__ = 'Users'

    idUser = Column(Integer, primary_key=True)
    firstName = Column(String(50), unique=True)

    def __init__(self, firstName):
        self.firstName = firstName

    def __repr__(self):
        return f'<User {self.firstName!r}>'