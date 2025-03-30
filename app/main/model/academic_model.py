from sqlalchemy import Column, Integer, String, Text, LargeBinary
from database import Base

class Academic(Base):
    __tablename__ = 'academic'
    __table_args__ = {'schema': 'inredd_schema'}

    iduser = Column(Integer, primary_key=True)
    contact = Column(String(50))
    idaddress = Column(Integer)
    title = Column(String(50))
    institution = Column(String(255))
    lattesid = Column(String(255))
    profilepicture = Column(LargeBinary) 
    biography = Column(Text)

    def __init__(self, iduser, contact, idaddress, title, institution, lattesid, profilepicture, biography):
        self.iduser = iduser
        self.contact = contact
        self.idaddress = idaddress
        self.title = title
        self.institution = institution
        self.lattesid = lattesid
        self.profilepicture = profilepicture
        self.biography = biography

    def __repr__(self):
        resultMessage = f"<Academic idUser={self.iduser}, "
        resultMessage += f"contact={self.contact}, "
        resultMessage += f"title={self.title}, "
        resultMessage += f"institution={self.institution}, "
        resultMessage += f"lattesid={self.lattesID}, "
        resultMessage += f"biography={self.biography}>"
        return resultMessage

    def to_dict(self):
        # Note: profilepicture is binary data; you might want to handle it differently
        return {
            "iduser": self.iduser,
            "contact": self.contact,
            "idaddress": self.idaddress,
            "title": self.title,
            "institution": self.institution,
            "lattesid": self.lattesid,
            "biography": self.biography,
        }
