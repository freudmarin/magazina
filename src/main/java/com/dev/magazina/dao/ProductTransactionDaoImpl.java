package com.dev.magazina.dao;

import com.dev.magazina.model.*;
import com.dev.magazina.service.ProductTransactionUnitService;
import com.dev.magazina.service.WarehouseProductService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductTransactionDaoImpl implements ProductTransactionDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private WarehouseProductService warehouseProductService;
    @Autowired
    private ProductTransactionUnitService productTransactionUnitService;

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
    @SuppressWarnings("Duplicates")
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
    @SuppressWarnings("Duplicates")
    public List<ProductTransaction> findByType(String type, Warehouse warehouse) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductTransaction> query = builder.createQuery(ProductTransaction.class);
        Root<ProductTransaction> root = query.from(ProductTransaction.class);
        query.select(root).where(builder.and(builder.equal(root.get("type"), type), builder.equal(root.get("warehouse"), warehouse.getId())));
        List<ProductTransaction> productTransactions = session.createQuery(query).getResultList();
//        Hibernate.initialize(productTransaction.getProductTransactionUnits());
        session.close();
        return productTransactions;
    }

    @Override
    public void save(ProductTransaction productTransaction) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(productTransaction);
        session.getTransaction().commit();
        session.close();
    }


    @Override
    public void delete(ProductTransaction productTransaction) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        double currentAmount = 0;
        if (productTransaction.getProductTransactionUnits().size() >= 1) {
            for (ProductTransactionUnit ptu : productTransaction.getProductTransactionUnits()) {
                Warehouse warehouse = ptu.getProductTransaction().getWarehouse();
                Product product = ptu.getProduct();
                double amount = ptu.getAmount();
                WarehouseProduct wp = new WarehouseProduct(warehouse, product, amount);
                if (warehouseProductService.compare(wp)) {
                    WarehouseProduct currentwp = warehouseProductService.getWarehouse(wp);
                    if (ptu.getProductTransaction().getType().equalsIgnoreCase("F")) {
                        currentAmount = currentwp.getAmount() - ptu.getAmount();
                    } else {
                        currentAmount = ptu.getAmount() + currentwp.getAmount();

                    }

                    currentwp.setAmount(currentAmount);
                    warehouseProductService.save(currentwp);
                }
                productTransactionUnitService.delete(ptu);
            }
        }
        session.delete(productTransaction);
        session.getTransaction().commit();
        session.close();
    }


    public void setProductTransactionUnit(ProductTransactionUnit ptu) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductTransactionUnit> query = builder.createQuery(ProductTransactionUnit.class);
        Root<ProductTransaction> root = query.from(ProductTransaction.class);
        session.close();


    }


//    @Override
//    @SuppressWarnings("Duplicates")
//    public ProductTransaction findById(int id) {
//        Session session = sessionFactory.openSession();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<ProductTransaction> query = builder.createQuery(ProductTransaction.class);
//        Root<ProductTransaction> root = query.from(ProductTransaction.class);
//        query.select(root).where(builder.equal(root.get("id"), id));
//        List<ProductTransaction> products = session.createQuery(query).getResultList();
//
//        session.close();
//
//        return products.isEmpty() ? null : products.get(0);
//    }

}
