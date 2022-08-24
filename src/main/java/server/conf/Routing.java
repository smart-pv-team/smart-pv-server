package server.conf;

public interface Routing {

  String DEVICE_ID_VARIABLE = "deviceId";
  String FARM_ID_VARIABLE = "farmId";
  String DEVICE_ID_PLACEHOLDER = "{" + DEVICE_ID_VARIABLE + "}";
  String FARM_ID_PLACEHOLDER = "{" + FARM_ID_VARIABLE + "}";

  interface Management {

    String VALUE = "/management";
    String PATH = VALUE;

    interface Farms {

      String VALUE = "/farms";
      String PATH = Management.PATH + VALUE;

      interface FarmId {

        String VALUE = "/" + FARM_ID_PLACEHOLDER;
        String PATH = Farms.PATH + VALUE;

        interface Parameters {

          String VALUE = "/parameters";
          String PATH = FarmId.PATH + VALUE;

          interface Name {

            String VALUE = "/name";
            String PATH = Parameters.PATH + VALUE;
          }
        }
      }
    }
  }

  interface Measurement {

    String VALUE = "/measurement";
    String PATH = VALUE;

    interface Devices {

      String VALUE = "/devices";
      String PATH = Measurement.PATH + VALUE;

      interface DeviceId {

        String VALUE = "/" + DEVICE_ID_PLACEHOLDER;
        String PATH = Devices.PATH + VALUE;

        interface Parameters {

          String VALUE = "/parameters";
          String PATH = DeviceId.PATH + VALUE;

          interface Name {

            String VALUE = "/name";
            String PATH = Parameters.PATH + VALUE;
          }
        }
      }
    }
  }

  interface Consumption {

    String VALUE = "/consumption";
    String PATH = VALUE;

    interface Devices {

      String VALUE = "/devices";
      String PATH = Consumption.PATH + VALUE;

      interface DeviceId {

        String VALUE = "/" + DEVICE_ID_PLACEHOLDER;
        String PATH = Devices.PATH + VALUE;

        interface Parameters {

          String VALUE = "/parameters";
          String PATH = DeviceId.PATH + VALUE;

          interface IsOn {

            String VALUE = "/isOn";
            String PATH = Parameters.PATH + VALUE;

          }
        }
      }
    }
  }
}
