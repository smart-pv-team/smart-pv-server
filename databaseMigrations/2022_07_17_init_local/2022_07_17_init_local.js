const conn = new Mongo();
const db = conn.getDB("smartPV");

db.getCollection('measuringDeviceEntity').remove({})
db.getCollection('consumerDeviceEntity').remove({})

db.getCollection('measuringDeviceEntity').insertMany(
    [
      {
        "ipAddress": "http://127.0.0.1:3005",
        "dataEndpoint": "/measurement/device/1",
        "_class": "measuring.MeasuringDeviceEntity"
      },
      {
        "ipAddress": "http://127.0.0.1:3005",
        "dataEndpoint": "/measurement/device/2",
        "_class": "measuring.MeasuringDeviceEntity"
      },
      {
        "ipAddress": "http://127.0.0.1:3005",
        "dataEndpoint": "/measurement/device/3",
        "_class": "measuring.MeasuringDeviceEntity"
      }
    ]
)

db.getCollection('consumerDeviceEntity').insertMany(
    [
      {
        "ipAddress": "http://127.0.0.1:3007",
        "dataEndpoint": "/consumer/device/1",
        "priority": 0,
        "isOn": false,
        "powerConsumption": 6,
        "minHysteresis": 2,
        "maxHysteresis": 3,
        "isLocked": false,
        "_class": "managing.ConsumerDeviceEntity"
      },
      {
        "ipAddress": "http://127.0.0.1:3007",
        "dataEndpoint": "/consumer/device/2",
        "priority": 0,
        "isOn": false,
        "powerConsumption": 5,
        "minHysteresis": 1,
        "maxHysteresis": 3,
        "isLocked": false,
        "_class": "managing.ConsumerDeviceEntity"
      }
    ]
)