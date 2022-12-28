package com.adapters.inbound.http;

import com.adapters.inbound.http.Routing.Api.User.Farm;

public interface Routing {

  String DEVICE_ID_VARIABLE = "deviceId";
  String TOKEN_VARIABLE = "token";
  String FARM_ID_VARIABLE = "farmId";
  String INTERVAL_ID_VARIABLE = "intervalId";
  String RULE_ID_VARIABLE = "ruleId";
  String DEVICE_ID_PLACEHOLDER = "{" + DEVICE_ID_VARIABLE + "}";
  String FARM_ID_PLACEHOLDER = "{" + FARM_ID_VARIABLE + "}";
  String INTERVAL_ID_PLACEHOLDER = "{" + INTERVAL_ID_VARIABLE + "}";
  String RULE_ID_PLACEHOLDER = "{" + RULE_ID_VARIABLE + "}";
  String TOKEN_PLACEHOLDER = "{" + TOKEN_VARIABLE + "}";

  interface Api {

    String VALUE = "/api";
    String PATH = VALUE;

    interface Admin {

      String VALUE = "/admin";
      String PATH = Api.PATH + VALUE;

      interface Token {

        String VALUE = "/token";
        String PATH = Admin.PATH + VALUE;

        interface TokenId {

          String VALUE = "/" + TOKEN_PLACEHOLDER;
          String PATH = Token.PATH + VALUE;

          interface Farm {

            String VALUE = "/farm";
            String PATH = TokenId.PATH + VALUE;
          }

          interface Pupils {

            String VALUE = "/pupils";
            String PATH = TokenId.PATH + VALUE;
          }
        }
      }
    }

    interface User {

      String VALUE = "/user";
      String PATH = Api.PATH + VALUE;

      interface Token {

        String VALUE = "/token";
        String PATH = User.PATH + VALUE;

        interface TokenId {

          String VALUE = "/" + TOKEN_PLACEHOLDER;
          String PATH = Token.PATH + VALUE;
        }
      }

      interface Farm {

        String VALUE = "/farm";
        String PATH = User.PATH + VALUE;

        interface FarmId {

          String VALUE = "/" + FARM_ID_PLACEHOLDER;
          String PATH = Farm.PATH + VALUE;
        }
      }
    }
  }


  interface Management {

    String VALUE = "/management";
    String PATH = VALUE;

    interface Algorithm {

      String VALUE = "/algorithm";
      String PATH = Management.PATH + VALUE;

      interface Interval {

        String VALUE = "/interval";
        String PATH = Algorithm.PATH + VALUE;

        interface IntervalId {

          String VALUE = "/" + INTERVAL_ID_PLACEHOLDER;
          String PATH = Interval.PATH + VALUE;

          interface Rule {

            String VALUE = "/rule";
            String PATH = IntervalId.PATH + VALUE;
          }
        }
      }

      interface Rule {

        String VALUE = "/rule";
        String PATH = Algorithm.PATH + VALUE;

        interface RuleId {

          String VALUE = "/" + RULE_ID_PLACEHOLDER;
          String PATH = Rule.PATH + VALUE;
        }
      }
    }

    interface DevicesModel {

      String VALUE = "/devicesModel";
      String PATH = Management.PATH + VALUE;
    }

    interface ResponseOptions {

      String VALUE = "/responseOptions";
      String PATH = Management.PATH + VALUE;
    }

    interface Farms {

      String VALUE = "/farms";
      String PATH = Management.PATH + VALUE;

      interface FarmId {

        String VALUE = "/" + FARM_ID_PLACEHOLDER;
        String PATH = Farms.PATH + VALUE;

        interface Measurement {

          String VALUE = "/measurement";
          String PATH = FarmId.PATH + VALUE;

          interface Devices {

            String VALUE = "/devices";
            String PATH = Measurement.PATH + VALUE;
          }
        }

        interface Consumption {

          String VALUE = "/consumption";
          String PATH = FarmId.PATH + VALUE;

          interface Devices {

            String VALUE = "/devices";
            String PATH = Consumption.PATH + VALUE;
          }
        }

        interface Algorithm {

          String VALUE = "/algorithm";
          String PATH = Farm.FarmId.PATH + VALUE;

          interface Interval {

            String VALUE = "/interval";
            String PATH = Algorithm.PATH + VALUE;

          }
        }

        interface Parameters {

          String VALUE = "/parameters";
          String PATH = FarmId.PATH + VALUE;

          interface Name {

            String VALUE = "/name";
            String PATH = Parameters.PATH + VALUE;
          }

          interface Algorithm {

            String VALUE = "/algorithm";
            String PATH = Parameters.PATH + VALUE;
          }

          interface Running {

            String VALUE = "/running";
            String PATH = Parameters.PATH + VALUE;
          }
        }
      }
    }
  }

  interface Measurement {

    String VALUE = "/measurement";
    String PATH = VALUE;

    interface Farms {

      String VALUE = "/farms";
      String PATH = Measurement.PATH + VALUE;

      interface FarmId {

        String VALUE = "/" + FARM_ID_PLACEHOLDER;
        String PATH = Farms.PATH + VALUE;

        interface Statistics {

          String VALUE = "/statistics";
          String PATH = FarmId.PATH + VALUE;

          interface Period {

            String VALUE = "/period";
            String PATH = Statistics.PATH + VALUE;
          }

          interface Sum {

            String VALUE = "/sum";
            String PATH = Statistics.PATH + VALUE;
          }
        }
      }
    }

    interface Devices {

      String VALUE = "/devices";
      String PATH = Measurement.PATH + VALUE;

      interface DeviceId {

        String VALUE = "/" + DEVICE_ID_PLACEHOLDER;
        String PATH = Devices.PATH + VALUE;

        interface Last {

          String VALUE = "/last";
          String PATH = DeviceId.PATH + VALUE;
        }

        interface Range {

          String VALUE = "/range";
          String PATH = DeviceId.PATH + VALUE;
        }

        interface Statistics {

          String VALUE = "/statistics";
          String PATH = DeviceId.PATH + VALUE;

          interface Sum {

            String VALUE = "/sum";
            String PATH = Statistics.PATH + VALUE;
          }
        }

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

    interface Farms {

      String VALUE = "/farms";
      String PATH = Consumption.PATH + VALUE;

      interface FarmId {

        String VALUE = "/" + FARM_ID_PLACEHOLDER;
        String PATH = Farms.PATH + VALUE;

        interface Statistics {

          String VALUE = "/statistics";
          String PATH = FarmId.PATH + VALUE;

          interface Sum {

            String VALUE = "/sum";
            String PATH = Statistics.PATH + VALUE;
          }

          interface Period {

            String VALUE = "/period";
            String PATH = Statistics.PATH + VALUE;
          }
        }
      }
    }

    interface Devices {

      String VALUE = "/devices";
      String PATH = Consumption.PATH + VALUE;

      interface DeviceId {

        String VALUE = "/" + DEVICE_ID_PLACEHOLDER;
        String PATH = Devices.PATH + VALUE;

        interface Last {

          String VALUE = "/last";
          String PATH = DeviceId.PATH + VALUE;
        }

        interface Range {

          String VALUE = "/range";
          String PATH = DeviceId.PATH + VALUE;
        }

        interface Statistics {

          String VALUE = "/statistics";
          String PATH = DeviceId.PATH + VALUE;

          interface Sum {

            String VALUE = "/sum";
            String PATH = Statistics.PATH + VALUE;
          }
        }

        interface Parameters {

          String VALUE = "/parameters";
          String PATH = DeviceId.PATH + VALUE;

          interface IsOn {

            String VALUE = "/isOn";
            String PATH = Parameters.PATH + VALUE;

          }

          interface Priority {

            String VALUE = "/priority";
            String PATH = Parameters.PATH + VALUE;

          }

          interface PowerConsumption {

            String VALUE = "/powerConsumption";
            String PATH = Parameters.PATH + VALUE;

          }
        }
      }
    }
  }
}
