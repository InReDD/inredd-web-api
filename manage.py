# ----------------------------------------------------------------------------------------------------------------------
#   Copyright InReDD - USP RP.
#   This software is confidential and property of InReDD - USP RP.
#   Your distribution or disclosure of your content is not permitted without express permission from InReDD - USP RP.
#   This file contains proprietary information.
# ----------------------------------------------------------------------------------------------------------------------
import logging
logging.info("Starting Inredd-Webservices...")

from app import blueprint
from app.main import create_app

app = create_app('development')
app.register_blueprint(blueprint)
app.app_context().push()

if __name__ == '__main__':
    app.run()