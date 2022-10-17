const measurementFilter = {};
const measurementUpdate = {}

db.getCollection('measurementEntity').aggregate( [
  {
    $lookup:
        {
          from: "farmId",
          localField: "item",
          foreignField: "sku",
          as: "inventory_docs"
        }
  }
] )

//db.getCollection('consumptionDeviceEntity').updateMany(consumptionDevicesFilter, consumptionDevicesUpdate)
