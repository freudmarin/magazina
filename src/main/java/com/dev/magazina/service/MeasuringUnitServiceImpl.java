package com.dev.magazina.service;

import com.dev.magazina.dao.MeasuringUnitDao;
import com.dev.magazina.model.MeasuringUnit;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MeasuringUnitServiceImpl  implements  MeasuringUnitService{
    @Autowired
    private MeasuringUnitDao measuringUnitDao;

    @Override
    public List<MeasuringUnit> findAll() {
        return measuringUnitDao.findAll();
    }
}
