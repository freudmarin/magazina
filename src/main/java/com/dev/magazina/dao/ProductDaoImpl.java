package com.dev.magazina.dao;

import com.dev.magazina.model.Category;
import com.dev.magazina.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Product> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<Product> query = session.getCriteriaBuilder().createQuery(Product.class);
        query.select(query.from(Product.class));
        List<Product> products = session.createQuery(query).getResultList();

        session.close();

        return products;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<Product> findAllByCategory(Category category) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root).where(builder.equal(root.get("category_id"), category.getId()));
        List<Product> products = session.createQuery(query).getResultList();

        session.close();

        return products;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Product findById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        List<Product> products = session.createQuery(query).getResultList();

        session.close();

        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Product findByName(String name) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root).where(builder.equal(root.get("name"), name));
        List<Product> products = session.createQuery(query).getResultList();

        session.close();

        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public void save(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(product);

        session.getTransaction().commit();
        session.close();
    }
}
