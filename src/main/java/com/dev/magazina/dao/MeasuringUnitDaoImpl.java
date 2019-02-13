package com.dev.magazina.dao;

import com.dev.magazina.model.Category;
import com.dev.magazina.model.MeasuringUnit;
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
public class MeasuringUnitDaoImpl implements MeasuringUnitDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MeasuringUnit> findAll() {
        {
            Session session = sessionFactory.openSession();
            CriteriaQuery<MeasuringUnit> query = session.getCriteriaBuilder().createQuery(MeasuringUnit.class); //dao implementim i databazes
            query.select(query.from(MeasuringUnit.class));
            List<MeasuringUnit> mus = session.createQuery(query).getResultList();
//            for (MeasuringUnit mu : mus) {
//                Hibernate.initialize(mu.getProducts());
//            }
            session.close();
            return mus;
        }
    }


    @Override
    public MeasuringUnit findById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MeasuringUnit> query = builder.createQuery(MeasuringUnit.class);
        Root<MeasuringUnit> root = query.from(MeasuringUnit.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        MeasuringUnit mu = session.createQuery(query).getSingleResult();
        Hibernate.initialize(mu.getProducts());

        session.close();

        return mu;
    }
}
