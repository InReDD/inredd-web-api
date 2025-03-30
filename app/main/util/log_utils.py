# ----------------------------------------------------------------------------------------------------------------------
#   Copyright InReDD - USP RP.
#   This software is confidential and property of InReDD - USP RP.
#   Your distribution or disclosure of your content is not permitted without express permission from InReDD - USP RP.
#   This file contains proprietary information.
# ----------------------------------------------------------------------------------------------------------------------
import logging
import logging.handlers
from datetime import datetime
import os
import json
import inspect

PROJECT = "inredd-api-webportal"

TIMESTAMP_FORMAT = "%Y-%m-%d %H:%M:%S"
TIMESTAMP = datetime.now().strftime(TIMESTAMP_FORMAT)


class LogUtils:
    """
    Singleton utility class for logs.
    """
    _instance = None

    def __init__(self):

        self.create_directory()

        self.logger = logging.getLogger(__name__)
        self.logger.setLevel(logging.DEBUG)

        stream_devnull = open(os.devnull, 'w')
        c_handler = logging.StreamHandler(stream_devnull)
        f_handler = logging.handlers.RotatingFileHandler("./logs/" + PROJECT + ".log", maxBytes=1073741824,
                                                         backupCount=3)

        c_handler.setLevel(logging.DEBUG)
        f_handler.setLevel(logging.INFO)

        # Add handlers to the logger
        self.logger.addHandler(c_handler)
        self.logger.addHandler(f_handler)

    @classmethod
    def instance(cls):
        """
        Responsible for guaranteeing only a unique instance of this class (Singleton).
        :return:
        """
        if cls._instance is None:
            cls._instance = cls()
        return cls._instance

    def info(self, file_name: str, message: str, extra: str = None, func_name: str = None):
        """
        Generate information log.
        :param file_name:
        :param message:
        :param extra:
        :param func_name:
        :return:
        """
        if func_name is None:
            func_name = inspect.stack()[1][3]

        extra_dict = {"file_name": file_name, "func_name": func_name}

        try:
            if extra is None:
                self.logger.info(json.dumps(
                    {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "INFO",
                     "message": message, "data": extra_dict}, default=str))
            else:
                self.logger.info(json.dumps(
                    {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "INFO",
                     "message": message, "extra": json.dumps(str(extra), default=str), "data": extra_dict},
                    default=str))

        except Exception as e:
            self.logger.error(json.dumps(
                {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "ERROR",
                 "message": str(e)}))

    def warning(self, file_name: str, message: str, extra: str = None, func_name: str = None):
        """
        Generate warning log.
        :param file_name:
        :param message:
        :param extra:
        :param func_name:
        :return:
        """
        if func_name is None:
            func_name = inspect.stack()[1][3]

        extra_dict = {"file_name": file_name, "func_name": func_name}
        try:
            if extra is None:
                self.logger.warning(json.dumps(
                    {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "WARNING",
                     "message": message, "data": extra_dict}, default=str))
            else:
                self.logger.warning(json.dumps(
                    {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "WARNING",
                     "message": message, "extra": json.dumps(str(extra), default=str), "data": extra_dict},
                    default=str))

        except Exception as e:
            self.logger.error(json.dumps(
                {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "ERROR",
                 "message": str(e)}))

    def error(self, file_name: str, message: str, extra: str = None, func_name: str = None):
        """
        Generate error log.
        :param file_name:
        :param message:
        :param extra:
        :param func_name:
        :return:
        """
        if func_name is None:
            func_name = inspect.stack()[1][3]

        extra_dict = {"file_name": file_name, "func_name": func_name}
        try:
            if extra is None:
                self.logger.error(json.dumps(
                    {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "ERROR",
                     "message": message, "data": extra_dict}, default=str))
            else:
                self.logger.error(json.dumps(
                    {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "ERROR",
                     "message": message, "extra": json.dumps(str(extra), default=str), "data": extra_dict},
                    default=str))

        except Exception as e:
            self.logger.error(json.dumps(
                {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "ERROR",
                 "message": str(e)}))

    def debug(self, file_name: str, message: str, extra: str = None, func_name: str = None):
        """
        Generate debug log.
        :param file_name:
        :param message:
        :param extra:
        :param func_name:
        :return:
        """
        if func_name is None:
            func_name = inspect.stack()[1][3]

        extra_dict = {"file_name": file_name, "func_name": func_name}

        try:
            if extra is None:
                self.logger.debug(json.dumps(
                    {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "DEBUG",
                     "message": message, "data": extra_dict}, default=str))
            else:
                self.logger.debug(json.dumps(
                    {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "DEBUG",
                     "message": message, "extra": json.dumps(str(extra), default=str), "data": extra_dict},
                    default=str))

        except Exception as e:
            self.logger.error(json.dumps(
                {"timestamp": datetime.now().strftime(TIMESTAMP_FORMAT), "project": PROJECT, "LEVEL": "ERROR",
                 "message": str(e)}))

    def create_directory(self):
        """
        Create a logs directory
        """
        path = "/logs"

        try:
            if not os.path.isdir(path):
                os.mkdir(path)
        except OSError:
            print("Creation of the directory %s failed" % path)
        else:
            print("Successfully created the directory %s " % path)