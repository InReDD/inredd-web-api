from flask_restx import Namespace, Resource, Api

from ..util.log_utils import LogUtils
from ..util.decorator import logging_get_request

log_utils = LogUtils.instance()

api = Namespace('index', description='Inredd - Basics.')

api.model("indexResponse", {
    'file': {'type': 'string', 'type': 'An image file'}
})

# Settings
FILE_NAME = "index_controller.py"

@api.route('/')
class HelloWorld(Resource):
    @api.response(200, 'Connection test')
    @api.doc('Inredd Index - Connection test')
    @logging_get_request(FILE_NAME, "get", "Index", print_input=False)
    def get(self):
        return {'message': "Connection OK"}