const consumptionDevicesFilter = {};
const consumptionDevicesUpdate = {
  $set: {
    "deviceModel": "SUPLA_MEW01"
  }
}

db.getCollection('consumptionDeviceEntity').updateMany(consumptionDevicesFilter, consumptionDevicesUpdate)

const measurementDevicesFilter = {};
const measurementDevicesUpdate = {
  $set: {
    "deviceModel": "SUPLA_ROW01"
  }
}

db.getCollection('measurementDeviceEntity').updateMany(measurementDevicesFilter, measurementDevicesUpdate)