#!/usr/bin/zsh
#TODO hide db conn credentials

mongosh localhost:27017/smartPV 2022_12_20_measurement_device_status.js
#mongosh "mongodb+srv://smartpv.dr74ruo.mongodb.net/smartpv" --apiVersion 1 --username xxx --password xxx 2022_12_20_measurement_device_status.js