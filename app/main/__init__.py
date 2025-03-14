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
from dotenv import load_dotenv
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate

from .util.log_utils import LogUtils

log_utils = LogUtils.instance()
FILE_NAME = "__init__.py"
load_dotenv()

DATABASE_URL = os.getenv('DATABASE_URL', 'sqlite:///app.db')

db = SQLAlchemy()
migrate = Migrate()

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

    app.config['SQLALCHEMY_DATABASE_URI'] = DATABASE_URL
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
    
    db.init_app(app)
    migrate.init_app(app, db) # talvez remova
    return app
