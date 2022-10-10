const consumptionDevicesFilter = {};
const consumptionDevicesUpdate = {
  $set: {
    lastStatusChange: new Date()
  }
}

db.getCollection('consumptionDeviceEntity').updateMany(consumptionDevicesFilter, consumptionDevicesUpdate)
