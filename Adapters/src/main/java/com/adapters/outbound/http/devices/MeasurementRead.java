package com.adapters.outbound.http.devices;

import com.domain.model.actions.MeasurementReadAction;
import com.domain.model.measurement.MeasurementDevice;
import java.io.Serializable;

public interface MeasurementRead extends Serializable {

  MeasurementReadAction toDomain(MeasurementDevice measurementDevice);
}
