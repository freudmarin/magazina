package com.dev.magazina.service;

import com.dev.magazina.model.MeasuringUnit;

import java.util.List;

public interface MeasuringUnitService {
    List<MeasuringUnit> findAll();

    MeasuringUnit findById(int id);
}
