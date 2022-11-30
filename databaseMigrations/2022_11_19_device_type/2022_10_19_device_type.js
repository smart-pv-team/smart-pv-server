const consumptionDevicesFilter = {};
const consumptionDevicesUpdate = {
  $set: {
    "deviceModel": "SUPLA_ROW01",
    "endpoints.0.responseClass": "SUPLA_CONSUMPTION_READ_ROW01",
    "endpoints.1.responseClass": "SUPLA_CONSUMPTION_ONOFF_ROW01",
    "endpoints.2.responseClass": "SUPLA_CONSUMPTION_ONOFF_ROW01"
  }
}

db.getCollection('consumptionDeviceEntity').updateMany(consumptionDevicesFilter, consumptionDevicesUpdate)

const measurementDevicesFilter = {};
const measurementDevicesUpdate = {
  $set: {
    "deviceModel": "SUPLA_MEW01",
    "endpoints.0.responseClass": "SUPLA_METER_READ_MEW01",
  }
}

db.getCollection('measurementDeviceEntity').updateMany(measurementDevicesFilter, measurementDevicesUpdate)