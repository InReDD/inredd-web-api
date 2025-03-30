from flask_restx import Namespace, fields

from sqlalchemy import Column, Integer, String
from database import Base

class User(Base):
    __tablename__ = 'User'
    __table_args__ = {'schema': 'inredd_schema'}

    iduser = Column(Integer, primary_key=True)
    firstname = Column(String(45))
    lastname = Column(String(45))
    #email = Column(String(45))
    contact = Column(String(45))

    def __init__(self, iduser, firstname, lastname, contact):
        self.iduser = iduser
        self.firstname = firstname
        self.lastname = lastname
        self.contact = contact

    def __repr__(self):
        resultMessage += f'<User {self.firstname!r}>'
        resultMessage += f'<Id {self.iduser!r}>'
        resultMessage += f'<LastName {self.lastname!r}>'
        resultMessage += f'<Contact {self.contact!r}>'
        # resultMessage += f'<Email {self.email!r}>' 
        

        return resultMessage
    
    def to_dict(self):
        return {
            "iduser": self.iduser,
            "firstname": self.firstname,
            "lastname": self.lastname,
        #    "email": self.email,
            "contact": self.contact
        }
class UserTO:
    api = Namespace('inredd', description='User - Operations')

    user_response = api.model("UserListResponse", {
        'firstName': fields.Raw(required=True, description='User - firstName')
    })
