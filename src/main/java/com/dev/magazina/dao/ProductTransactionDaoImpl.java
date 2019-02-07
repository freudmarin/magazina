package com.dev.magazina.dao;

import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.ProductTransactionUnit;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductTransactionDaoImpl implements ProductTransactionDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ProductTransaction> findAll() {
        Session session = sessionFactory.openSession();

        CriteriaQuery<ProductTransaction> query = session.getCriteriaBuilder().createQuery(ProductTransaction.class);
        query.select(query.from(ProductTransaction.class));
        List<ProductTransaction> productTransactions = session.createQuery(query).getResultList();

        session.close();

        return productTransactions;
    }

    @Override
    public ProductTransaction findById(int id) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductTransaction> query = builder.createQuery(ProductTransaction.class);
        Root<ProductTransaction> root = query.from(ProductTransaction.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        ProductTransaction productTransaction = session.createQuery(query).getSingleResult();
        Hibernate.initialize(productTransaction.getProductTransactionUnits());


        session.close();

        return productTransaction;
    }

    @Override
    public void save(ProductTransaction productTransaction, List<ProductTransactionUnit> ptus) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(productTransaction);
    }


    @Override
    public void delete(ProductTransaction productTransaction) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(productTransaction);
        session.getTransaction().commit();
        session.close();
    }


    public void setProductTransactionUnit(ProductTransactionUnit ptu) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductTransactionUnit> query = builder.createQuery(ProductTransactionUnit.class);
        Root<ProductTransaction> root = query.from(ProductTransaction.class);

//        ProductTransaction productTransaction = session.createQuery(query).getSingleResult();

        session.close();


    }

//
}
