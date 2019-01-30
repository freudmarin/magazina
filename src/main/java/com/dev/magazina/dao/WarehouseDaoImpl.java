package com.dev.magazina.dao;

import com.dev.magazina.model.Warehouse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class WarehouseDaoImpl implements WarehouseDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Warehouse> findAll() {
        Session session = sessionFactory.openSession();

        CriteriaQuery<Warehouse> query = session.getCriteriaBuilder().createQuery(Warehouse.class);
        query.select(query.from(Warehouse.class));
        List<Warehouse> warehouses = session.createQuery(query).getResultList();

        session.close();

        return warehouses;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Warehouse findById(int id) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Warehouse> query = builder.createQuery(Warehouse.class);
        Root<Warehouse> root = query.from(Warehouse.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        List<Warehouse> warehouses = session.createQuery(query).getResultList();

        session.close();

        return warehouses.isEmpty() ? null : warehouses.get(0);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Warehouse findByName(String name) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Warehouse> query = builder.createQuery(Warehouse.class);
        Root<Warehouse> root = query.from(Warehouse.class);
        query.select(root).where(builder.equal(root.get("name"), name));
        List<Warehouse> warehouses = session.createQuery(query).getResultList();

        session.close();

        return warehouses.isEmpty() ? null : warehouses.get(0);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Warehouse findFirst() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Warehouse> query = builder.createQuery(Warehouse.class);
        Root<Warehouse> root = query.from(Warehouse.class);
        query.select(root);
        List<Warehouse> warehouses = session.createQuery(query).getResultList();

        session.close();

        return warehouses.isEmpty() ? null : warehouses.get(0);
    }

    @Override
    public void save(Warehouse warehouse) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.saveOrUpdate(warehouse);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Warehouse warehouse) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(warehouse);

        session.getTransaction().commit();
        session.close();
    }
}
