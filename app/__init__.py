# ----------------------------------------------------------------------------------------------------------------------
#   Copyright InReDD WebPortal API - USP RP.
#   This software is confidential and property of InReDD - USP RP.
#   Your distribution or disclosure of your content is not permitted without express permission from InReDD - USP RP.
#   This file contains proprietary information.
# ----------------------------------------------------------------------------------------------------------------------
from flask import Blueprint
from flask_restx import Api

from .main.controller.index_controller import api as index
from .main.controller.user_controller import api as user

URL_PREFIX = '/api/v1'
BASE_PATH = '/inreddweb'

blueprint = Blueprint('api', __name__, url_prefix=URL_PREFIX)
api = Api(blueprint,
    title='InReDD WebPortal - API',
    version='1.0',
    description='InReDD WebPortal - API',
    doc='/doc/'
)

api.add_namespace(index, path=BASE_PATH)
api.add_namespace(user, path=BASE_PATH)