package com.domain.ports.farm;

import com.domain.model.actions.Action;
import com.domain.model.actions.ConsumptionOnOffAction;
import com.domain.model.actions.ConsumptionReadAction;
import com.domain.model.actions.MeasurementReadAction;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.farm.Device;
import com.domain.model.measurement.MeasurementDevice;

public interface DeviceGateway {

  void request(Device device, Action action);

  ConsumptionReadAction requestDevicesStatus(ConsumptionDevice consumptionDevice);

  MeasurementReadAction requestMeasurement(MeasurementDevice measurementDevice);

  ConsumptionOnOffAction requestOnOff(ConsumptionDevice consumptionDevice, Boolean turnOn);
}
