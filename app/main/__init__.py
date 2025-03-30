# ----------------------------------------------------------------------------------------------------------------------
#   Copyright InReDD WebPortal API - USP RP.
#   This software is confidential and property of InReDD - USP RP.
#   Your distribution or disclosure of your content is not permitted without express permission from InReDD - USP RP.
#   This file contains proprietary information.
# ----------------------------------------------------------------------------------------------------------------------
import logging
import os

from flask import Flask
from flask_cors import CORS
from .util.log_utils import LogUtils

log_utils = LogUtils.instance()
FILE_NAME = "__init__.py"

def create_app(config_name):
    """
    Init application API with flask.
    Args:
        config_name:
    Returns:
    """
    app = Flask(__name__)
    CORS(app)
    logging.basicConfig(level=logging.INFO)

    return app
