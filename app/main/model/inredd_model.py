# ----------------------------------------------------------------------------------------------------------------------
#   Copyright InReDD - USP RP.
#   This software is confidential and property of InReDD - USP RP.
#   Your distribution or disclosure of your content is not permitted without express permission from InReDD - USP RP.
#   This file contains proprietary information.
# ----------------------------------------------------------------------------------------------------------------------
from flask_restx import Namespace, fields

class InreddTO:
    """
    Transfer object for Inredd Web API.
    """
    DESCRIPTION_NAME = 'Name to identify the source.'
    api = Namespace('inredd', description='Inredd Operations.')

    user_response = api.model("UserListResponse", {
        'file': fields.Raw(required=True, description='Image file for detection')
    })
