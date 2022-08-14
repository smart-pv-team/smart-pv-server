package server.conf;

public interface Routing {

  String DEVICE_ID_VARIABLE = "deviceId";
  String FARM_ID_VARIABLE = "farmId";
  String DEVICE_ID_PLACEHOLDER = "{" + DEVICE_ID_VARIABLE + "}";
  String FARM_ID_PLACEHOLDER = "{" + FARM_ID_VARIABLE + "}";

  interface Measurement {

    String VALUE = "/measurement";
    String PATH = VALUE;

    interface Farm {

      String VALUE = "/farm";
      String PATH = Measurement.VALUE + VALUE;

      interface FarmId {

        String VALUE = "/" + FARM_ID_PLACEHOLDER;
        String PATH = Farm.PATH + VALUE;

      }
    }

    interface Device {

      String VALUE = "/device";
      String PATH = Measurement.VALUE + VALUE;

      interface DeviceId {

        String VALUE = "/" + DEVICE_ID_PLACEHOLDER;
        String PATH = Device.PATH + VALUE;
      }
    }
  }

  interface Consumption {

    String VALUE = "/consumption";
    String PATH = VALUE;

    interface Parameters {

      String VALUE = "/parameters";
      String PATH = VALUE;

      interface DeviceId {

        String VALUE = "/" + DEVICE_ID_PLACEHOLDER;
        String PATH = Parameters.PATH + VALUE;

        interface IsOn {

          String VALUE = "/isOn";
          String PATH = DeviceId.PATH + VALUE;
        }
      }
    }
  }


}
