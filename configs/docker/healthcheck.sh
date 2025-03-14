#!/bin/bash
SERVERNAME=$(cat /etc/hostname)

if [[ $(find /logs/inredd-api-webportal.log -type f -mmin +2) ]]
then
  exit 1;
else
  exit 0;
fi