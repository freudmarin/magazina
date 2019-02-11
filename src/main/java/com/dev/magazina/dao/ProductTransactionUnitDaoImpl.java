package com.dev.magazina.dao;

import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.ProductTransactionUnit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductTransactionUnitDaoImpl implements ProductTransactionUnitDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ProductTransactionUnit> findAll() {
        Session session = sessionFactory.openSession();

        CriteriaQuery<ProductTransactionUnit> query = session.getCriteriaBuilder().createQuery(ProductTransactionUnit.class);
        query.select(query.from(ProductTransactionUnit.class));
        List<ProductTransactionUnit> productTransactions = session.createQuery(query).getResultList();

        session.close();

        return productTransactions;
    }

    @Override
    public List<ProductTransactionUnit> findAllByProductTransaction(ProductTransaction productTransaction) {
        return null;
    }

    @Override
    public ProductTransactionUnit findById(int id) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductTransactionUnit> query = builder.createQuery(ProductTransactionUnit.class);
        Root<ProductTransactionUnit> root = query.from(ProductTransactionUnit.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        ProductTransactionUnit productTransaction = session.createQuery(query).getSingleResult();
        session.close();
        return productTransaction;
    }

    @Override
    public void save(ProductTransactionUnit productTransactionUnit) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(productTransactionUnit);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(ProductTransactionUnit productTransaction) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(productTransaction);
        session.getTransaction().commit();
        session.close();
    }
}
