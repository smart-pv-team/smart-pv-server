package com.domain.ports.management.farm;

import com.domain.model.actions.Action;
import com.domain.model.actions.ConsumptionOnOffAction;
import com.domain.model.actions.ConsumptionReadAction;
import com.domain.model.actions.MeasurementReadAction;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.Device;
import com.domain.model.measurement.MeasurementDevice;

public interface DeviceGateway {

  void request(Device device, Action action);

  ConsumptionReadAction requestDevicesStatus(ConsumptionDevice consumptionDevice);

  MeasurementReadAction requestMeasurement(MeasurementDevice measurementDevice);

  ConsumptionOnOffAction requestOnOff(ConsumptionDevice consumptionDevice, Boolean turnOn);

  void requestAction(ConsumptionDevice consumptionDevice, String action);
}
