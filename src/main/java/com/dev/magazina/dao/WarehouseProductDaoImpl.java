package com.dev.magazina.dao;

import com.dev.magazina.model.WarehouseProduct;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class WarehouseProductDaoImpl implements WarehouseProductDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(WarehouseProduct wp) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(wp);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean compare(WarehouseProduct wp) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WarehouseProduct> query = builder.createQuery(WarehouseProduct.class);
        Root<WarehouseProduct> root = query.from(WarehouseProduct.class);
        query.select(root);
        query.where(builder.and(
                builder.equal(root.get("warehouse"), wp.getWarehouse().getId()),
                builder.equal(root.get("product"), wp.getProduct().getId())));
        List<WarehouseProduct> wprod = session.createQuery(query).getResultList();
        if (wprod.size() >= 1) {
            return true;
        }

        return false;
    }

    @Override
    public WarehouseProduct getWarehouse(WarehouseProduct wp) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WarehouseProduct> query = builder.createQuery(WarehouseProduct.class);
        Root<WarehouseProduct> root = query.from(WarehouseProduct.class);
        query.select(root);
        query.where(builder.and(builder.equal(root.get("warehouse"), wp.getWarehouse().getId()), builder.equal(root.get("product"), wp.getProduct().getId())));
        List<WarehouseProduct> wprod = session.createQuery(query).getResultList();
        return wprod.get(0);


    }
}