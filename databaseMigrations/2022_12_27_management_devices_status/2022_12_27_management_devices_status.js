const consumptionDevicesFilter = {};
const consumptionDevicesUpdate = {
  $unset: {
    "controlParameters.minHysteresis": "",
    "controlParameters.maxHysteresis": ""
  },
  $set: {
    "controlParameters.lastStatus": "DEFAULT",
  }
}

db.getCollection('consumptionDeviceEntity').updateMany(consumptionDevicesFilter, consumptionDevicesUpdate)
