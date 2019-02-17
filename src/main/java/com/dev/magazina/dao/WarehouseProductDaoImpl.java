package com.dev.magazina.dao;

import com.dev.magazina.model.Category;
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


    @Override
    public List<WarehouseProduct> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<WarehouseProduct> query = session.getCriteriaBuilder().createQuery(WarehouseProduct.class); //dao implementim i databazes
        query.select(query.from(WarehouseProduct.class));
        List<WarehouseProduct> categories = session.createQuery(query).getResultList();

        session.close();

        return categories;
    }

    @Override
    public List<WarehouseProduct> findById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WarehouseProduct> query = builder.createQuery(WarehouseProduct.class);
        Root<WarehouseProduct> root = query.from(WarehouseProduct.class);
        query.select(root).where(builder.equal(root.get("warehouse"), id));
        List<WarehouseProduct> wp = session.createQuery(query).getResultList();
//        Hibernate.initialize(agent.getProducts());

        session.close();

        return wp;
    };
}