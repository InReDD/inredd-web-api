FROM python:3.8.8
LABEL Name=default maintainer="InReDD WebPortal API" Version=3.2.2

COPY ./wsgi.py /inredd-api-webportal/wsgi.py

WORKDIR ./inredd-api-webportal

RUN python3 -m pip install --upgrade pip

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    pip3 install virtualenv

RUN virtualenv /venv

RUN apt update && \
    apt install -y ffmpeg

COPY ./app/main/configuration/docker/requirements.txt ./app/main/configuration/docker/requirements.txt
RUN pip install -r ./app/main/configuration/docker/requirements.txt && rm ./app/main/configuration/docker/requirements.txt

COPY ./app/main/configuration/docker/api/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

RUN pip install -U uWSGI

# RUN apt-get update
RUN apt-get --allow-releaseinfo-change update
RUN apt-get install -y libgl1-mesa-dev

# Healthcheck
COPY ./app/main/configuration/docker/api/healthcheck.sh /healthcheck.sh
RUN mkdir /logs && touch /logs/control.log && chmod +x /healthcheck.sh
HEALTHCHECK --interval=1m --timeout=30s --start-period=45s \
    CMD /healthcheck.sh || bash -c 'kill -s 15 -1 && (sleep 10; kill -s 9 -1)'

RUN echo "--------------------- Finish ---------------------"
ENTRYPOINT [ "/entrypoint.sh" ]