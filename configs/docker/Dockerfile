FROM python:3.8.8 AS BASE
LABEL Name=default maintainer="InReDD WebPortal API" Version=3.2.2

COPY ./wsgi.py /inredd-api-webportal/wsgi.py

WORKDIR /inredd-api-webportal

RUN python3 -m pip install --upgrade pip

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    pip3 install virtualenv

RUN virtualenv /venv

RUN apt update && \
    apt install -y ffmpeg

COPY ./configs/docker/requirements.txt ./configs/docker/requirements.txt
RUN pip install -r ./configs/docker/requirements.txt && rm ./configs/docker/requirements.txt

COPY ./configs/docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

RUN pip install -U uWSGI

# RUN apt-get update
RUN apt-get --allow-releaseinfo-change update
RUN apt-get install -y libgl1-mesa-dev

FROM base AS run

WORKDIR /inredd-web-api
EXPOSE 3003

# Healthcheck
COPY ./configs/docker/healthcheck.sh /healthcheck.sh
RUN mkdir /logs && touch /logs/control.log && chmod +x /healthcheck.sh
HEALTHCHECK --interval=1m --timeout=30s --start-period=45s \
CMD /healthcheck.sh || bash -c 'kill -s 15 -1 && (sleep 10; kill -s 9 -1)'

COPY ./configs/docker/entrypoint.sh ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

COPY ./configs/docker/entrypoint-dev.sh ./entrypoint-dev.sh
RUN chmod +x ./entrypoint-dev.sh

USER appuser

ENV PATH="/home/appuser/.local/bin:${PATH}"

#Copying source files
COPY --chown=appuser:sudo . /inredd-webservices

FROM run AS prod

ENTRYPOINT ["./entrypoint.sh"]

FROM run AS dev

ENTRYPOINT ["./entrypoint-dev.sh"]