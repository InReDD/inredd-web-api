# Please add a Dockerfile
FROM ubuntu:20.04 AS base
LABEL Name=default maintainer="Inredd WebServices API" Version=1.0.1

ENV DEBIAN_FRONTEND=noninteractive

#update system and install basic libs
RUN apt-get update && \
    apt-get -y install\
    build-essential \
    curl \
    ffmpeg \
    git \
    ca-certificates \
    gcc \
    libglib2.0-0 \
    libsm6 \
    libxext6 \
    libxrender-dev \
    sudo \
    gnupg2 \
    libjpeg-dev \
    python3-dev  \
    python3-pip  \
    software-properties-common \
    zlib1g-dev && \
    rm -rf /var/lib/apt/lists/*
EXPOSE 3002
