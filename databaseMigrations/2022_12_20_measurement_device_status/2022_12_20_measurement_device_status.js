const measurementDevicesFilter = {};
const measurementDevicesUpdate = {
  $set: {
    "isOn": "true",
  }
}

db.getCollection('measurementDeviceEntity').updateMany(measurementDevicesFilter, measurementDevicesUpdate)

const farmFilter = {};
const farmUpdate = {
  $set: {
    "running": "true",
  }
}

db.getCollection('farmEntity').updateMany(farmFilter, farmUpdate)