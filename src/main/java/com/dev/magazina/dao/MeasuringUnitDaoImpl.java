package com.dev.magazina.dao;

import com.dev.magazina.model.MeasuringUnit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
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
            session.close();
            return mus;
        }
    }
}
