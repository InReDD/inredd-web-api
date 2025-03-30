FROM python:3.8.8 AS BASE
LABEL Name=default maintainer="InReDD WebPortal API" Version=3.2.2

#set work dir
WORKDIR /inredd-api-webportal

#install pip and virtualenv
RUN python3 -m pip install --upgrade pip

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    pip3 install virtualenv
   
RUN apt update && \
    apt install -y ffmpeg
    
#add root pip libs
RUN virtualenv /venv
COPY ./configs/docker/requirements.txt ./configs/docker/requirements.txt
RUN pip install -r ./configs/docker/requirements.txt && rm ./configs/docker/requirements.txt

# RUN apt-get update
RUN apt-get --allow-releaseinfo-change update
RUN apt-get install -y libgl1-mesa-dev

#set work dir and ports
WORKDIR /inredd-web-api
EXPOSE 3004

# Healthcheck
COPY ./configs/docker/healthcheck.sh ./healthcheck.sh
RUN mkdir /logs && touch /logs/control.log && chmod +x ./healthcheck.sh
HEALTHCHECK --interval=1m --timeout=30s --start-period=45s \
CMD ./healthcheck.sh || bash -c 'kill -s 15 -1 && (sleep 10; kill -s 9 -1)'

#copy entrypoints
COPY ./configs/docker/entrypoint.sh ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

COPY ./configs/docker/entrypoint-dev.sh ./entrypoint-dev.sh
RUN chmod +x ./entrypoint-dev.sh

ENV PATH="/home/appuser/.local/bin:${PATH}"

# creating user
ARG USER_ID=1000
RUN useradd -m --no-log-init --system  --uid ${USER_ID} appuser \
    && echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers \
    && usermod -aG sudo appuser

# add pip libs    
RUN pip install -U uWSGI
USER appuser

#Copying source files
COPY --chown=appuser:sudo . .

FROM base AS prod

ENTRYPOINT ["./entrypoint.sh"]

FROM base AS dev

ENTRYPOINT ["./entrypoint-dev.sh"]