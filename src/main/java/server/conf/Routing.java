package server.conf;

public interface Routing {

  String DEVICE_ID_VARIABLE = "id";
  String DEVICE_ID_PLACEHOLDER = "{" + DEVICE_ID_VARIABLE + "}";

  interface Parameters {

    String VALUE = "/parameters";
    String PATH = VALUE;

    interface Device {

      String VALUE = "/device";
      String PATH = Parameters.PATH + VALUE;

      interface DeviceId {

        String VALUE = "/" + DEVICE_ID_PLACEHOLDER;
        String PATH = Device.PATH + VALUE;

        interface IsOn {

          String VALUE = "/isOn";
          String PATH = DeviceId.PATH + VALUE;
        }
      }

    }
  }

}
