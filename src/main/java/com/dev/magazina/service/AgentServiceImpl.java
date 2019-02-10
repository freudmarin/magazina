package com.dev.magazina.service;

import com.dev.magazina.dao.AgentDao;
import com.dev.magazina.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentDao agentDao;

    @Override
    public List<Agent> findAll() {
        return agentDao.findAll();
    }

    @Override
    public List<Agent> findByType(String type) {
        return agentDao.findByType(type);
    }

    @Override
    public Agent findById(int id) {
        return agentDao.findById(id);
    }

    @Override
    public void save(Agent agent) {
        agentDao.save(agent);

    }

    @Override
    public void delete(Agent agent) {
        agentDao.delete(agent);
    }
}
