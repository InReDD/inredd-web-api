#!/bin/bash

SERVERNAME=$(cat /etc/hostname)
export SERVERNAME

uwsgi --socket 0.0.0.0:18082 --protocol=http -w wsgi:app --enable-threads --workers 16 --websockets-max-size 16384

exec "$@"