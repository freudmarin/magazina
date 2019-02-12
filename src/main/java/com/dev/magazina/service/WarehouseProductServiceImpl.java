package com.dev.magazina.service;

import com.dev.magazina.dao.WarehouseProductDao;
import com.dev.magazina.model.WarehouseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseProductServiceImpl implements WarehouseProductService {
    @Autowired
    private WarehouseProductDao wpDao;

    @Override
    public void save(WarehouseProduct wp) {
        wpDao.save(wp);
    }

    @Override
    public WarehouseProduct getWarehouse(WarehouseProduct wp) {
        return wpDao.getWarehouse(wp);
    }

    @Override
    public boolean compare(WarehouseProduct wp) {
        return wpDao.compare(wp);
    }

    @Override
    public List<WarehouseProduct> findAll() {
        return wpDao.findAll();
    }
}
