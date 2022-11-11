package com.domain.model.actions;


import com.domain.model.measurement.MeasurementDevice;

public record MeasurementReadAction(Float measuredEnergy, MeasurementDevice measurementDevice) {

}

