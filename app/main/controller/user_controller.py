# ----------------------------------------------------------------------------------------------------------------------
#   Copyright InReDD - USP RP.
#   This software is confidential and property of InReDD - USP RP.
#   Your distribution or disclosure of your content is not permitted without express permission from InReDD - USP RP.
#   This file contains proprietary information.
# ----------------------------------------------------------------------------------------------------------------------
from flask import abort, request
from flask_restx import Resource

from ..model.user_model import User, UserTO
from ..model.academic_model import Academic

from ..util.log_utils import LogUtils
from ..util.decorator import logging_get_request

from werkzeug.security import check_password_hash  # For password hashing check

log_utils = LogUtils.instance()
user_response = UserTO.user_response
api = UserTO.api
FILE_NAME = "user_controller.py"
INTERNAL_SERVER_ERROR = 500

@api.route('/login')
class UserController(Resource):
    """Controller to Login a User."""
    @api.response(200, 'User successfully logged in')
    @api.doc('Login documentation')
    @logging_get_request(FILE_NAME, "post", "UserController", print_input=False)
    def post(self):
        """
        Log in a user by validating the provided credentials.
        :return: User data if successful, error otherwise
        """
        try:
            # fget data from request
            data = request.get_json()
            username = data.get('username')
            password = data.get('password')

            if not username or not password:
                abort(400, "Username and password are required")

            # query the user in the database
            user = User.query.filter_by(username=username).first()

            if not user or not check_password_hash(user.password_hash, password):
                abort(401, "Invalid username or password")

            return user.to_dict(), 200

        except Exception as e:
            abort(INTERNAL_SERVER_ERROR, str(e.args))
@api.route('/userinfo')
class UserController(Resource):
    @api.response(200, 'User successfully logged in')
    @api.doc('Login documentation')
    @logging_get_request(FILE_NAME, "get", "UserController", print_input=False)
    def get(self):
        """
        Retrieve complete user information.
        Expects a 'username' parameter as a query parameter.
        :return: User data if found, error otherwise
        """
        try:
            firstname = request.args.get('firstname')
            if not firstname:
                abort(400, "Username query parameter is required")

            # Query the user in the database
            user = User.query.filter_by(firstname=firstname).first() 
           
             # Query the user academic in the database 
            iduser = user.iduser
            academic = Academic.query.filter_by(iduser=iduser).first()

            user_dict = user.to_dict()
            academic_dic = academic.to_dict()
            
            response_dict = {**user_dict, **academic_dic}

            if not user:
                abort(404, "User not found")

            return response_dict, 200

        except Exception as e:
            abort(INTERNAL_SERVER_ERROR, str(e.args))