package com.dev.magazina.service;

import com.dev.magazina.dao.WarehouseDao;
import com.dev.magazina.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseDao warehouseDao;

    @Override
    public List<Warehouse> findAll() {
        return warehouseDao.findAll();
    }

    @Override
    public Warehouse findById(int id) {
        Warehouse warehouse = warehouseDao.findById(id);

        if (warehouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse Not Found");
        }

        return warehouse;
    }

    @Override
    public Warehouse findById(Integer id) {
        Warehouse warehouse = (id != null) ? warehouseDao.findById(id) : warehouseDao.findFirst();

        if (warehouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse Not Found");
        }

        return warehouse;
    }

    @Override
    public Warehouse findByName(String name) {
        return warehouseDao.findByName(name);
    }

    @Override
    public Warehouse findFirst() {
        Warehouse warehouse = warehouseDao.findFirst();

        if (warehouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse Not Found");
        }

        return warehouse;
    }

    @Override
    public void save(Warehouse warehouse) {
        warehouseDao.save(warehouse);
    }

    @Override
    public void delete(Warehouse warehouse) {
        warehouseDao.delete(warehouse);
    }
}
