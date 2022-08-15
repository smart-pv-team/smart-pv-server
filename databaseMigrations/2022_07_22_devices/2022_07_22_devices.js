db.getCollection('measurementDeviceEntity').remove({})
db.getCollection('measurementEntity').remove({})
db.getCollection('consumerDeviceEntity').remove({})
db.getCollection('farmEntity').remove({})

db.getCollection('farmEntity').insertMany(
    [
      {
        "name": "hala",
        "description": "goralmet"
      }
    ]
)

const farmId = db.getCollection('farmEntity').findOne(
    {name: "hala"})._id.toString()

db.getCollection('measurementDeviceEntity').insertMany(
    [
      {
        "farmId": `${farmId}`,
        "name": "FIRMA przyłącz B1 od 16.04.21",
        "ipAddress": "https://svr48.supla.org",
        "endpoints": [
          {
            "description": "Odczytaj pomiar mocy",
            "action": "READ",
            "endpoint": "/direct/796/F9UcCeMbvPH7C/read",
            "httpMethod": "PATCH",
            "httpHeaders": {
              "format": "json",
              "authorization": "Bearer Mzg5ZWNhY2I5ZjZmNTkzNjAxNDgyN2U2NWI2NzYyMmJjOGFmNTYxYWFmMDBlOWM4YjFmOTMxOTU0MjliNWVmMw.aHR0cHM6Ly9zdnI0OC5zdXBsYS5vcmc="
            },
            "responseClass": "SUPLA_ELECTRIC_METER"
          }
        ],
        "_class": "measuring.MeasuringDeviceEntity"
      },
      {
        "farmId": `${farmId}`,
        "name": "FIRMA przyłącz A2 od 19.10.21",
        "ipAddress": "https://svr48.supla.org",
        "endpoints": [
          {
            "description": "Odczytaj pomiar mocy",
            "action": "READ",
            "endpoint": "/direct/797/cuE_stqFnXHfwP8u/read",
            "httpMethod": "PATCH",
            "httpHeaders": {
              "format": "json",
              "authorization": "Bearer Mzg5ZWNhY2I5ZjZmNTkzNjAxNDgyN2U2NWI2NzYyMmJjOGFmNTYxYWFmMDBlOWM4YjFmOTMxOTU0MjliNWVmMw.aHR0cHM6Ly9zdnI0OC5zdXBsYS5vcmc="
            },
            "responseClass": "SUPLA_ELECTRIC_METER"
          }
        ],
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