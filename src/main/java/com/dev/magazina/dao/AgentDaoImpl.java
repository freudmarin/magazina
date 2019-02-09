package com.dev.magazina.dao;

import com.dev.magazina.model.Agent;
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
public class AgentDaoImpl implements AgentDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Agent> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<Agent> query = session.getCriteriaBuilder().createQuery(Agent.class); //dao implementim i databazes
        query.select(query.from(Agent.class));
        List<Agent> agents = session.createQuery(query).getResultList();
        session.close();
        return agents;
    }

    @Override
    public Agent findById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Agent> query = builder.createQuery(Agent.class);
        Root<Agent> root = query.from(Agent.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        Agent agent = session.createQuery(query).getSingleResult();
//        Hibernate.initialize(agent.getProducts());

        session.close();

        return agent;
    }

    @Override
    public void save(Agent agent) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(agent);
        session.getTransaction().commit();
        session.close();
        session.flush();
    }

    @Override
    public void delete(Agent agent) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(agent);
        session.getTransaction().commit();
        session.close();
    }


}
