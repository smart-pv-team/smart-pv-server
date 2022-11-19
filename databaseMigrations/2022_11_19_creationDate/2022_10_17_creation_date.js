const consumptionDevicesFilter = {};
const consumptionDevicesUpdate = {
  $set: {
    "creationDate": new Date(2022, 7, 1)
  }
}

db.getCollection('consumptionDeviceEntity').updateMany(consumptionDevicesFilter, consumptionDevicesUpdate)

const measurementDevicesFilter = {};
const measurementDevicesUpdate = {
  $set: {
    "creationDate": new Date(2022, 7, 1)
  }
}

db.getCollection('measurementDeviceEntity').updateMany(measurementDevicesFilter, measurementDevicesUpdate)