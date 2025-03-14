# ----------------------------------------------------------------------------------------------------------------------
#   Copyright InReDD - USP RP.
#   This software is confidential and property of InReDD - USP RP.
#   Your distribution or disclosure of your content is not permitted without express permission from InReDD - USP RP.
#   This file contains proprietary information.
# ----------------------------------------------------------------------------------------------------------------------
from functools import wraps

from ..util.log_utils import LogUtils
log_utils = LogUtils.instance()

def logging_get_request(file_name, method, name, req=None, print_input=True, print_output=True):
    """
    Decorator to log the input and output of Flask GET request.
    """
    def log_decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            if print_input and req is not None:
                log_utils.debug(file_name, method, name + ' Params Request', req.args)
            else:
                log_utils.debug(file_name, method, name + ' Payload suppressed')
            wrap_ret = func(*args, **kwargs)
            if print_output:
                log_utils.debug(file_name, method, name + ' response data', wrap_ret)
            else:
                log_utils.debug(file_name, method, name + ' response data suppressed')
            return wrap_ret
        return wrapper
    return log_decorator