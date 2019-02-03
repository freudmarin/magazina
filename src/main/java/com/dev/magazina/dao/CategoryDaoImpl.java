package com.dev.magazina.dao;

import com.dev.magazina.model.Category;
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
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<Category> query = session.getCriteriaBuilder().createQuery(Category.class); //dao implementim i databazes
        query.select(query.from(Category.class));
        List<Category> categories = session.createQuery(query).getResultList();

        session.close();

        return categories;
    }

    @Override
    public Category findById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        Category category = session.createQuery(query).getSingleResult();
        Hibernate.initialize(category.getProducts());

        session.close();

        return category;
    }

    @Override
    public void save(Category category) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(category);
        session.getTransaction().commit();
        session.close();
        session.flush();
    }

    @Override
    public void delete(Category category) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(category);

        session.getTransaction().commit();
        session.close();
    }
}
