package com.dev.magazina.service;

import com.dev.magazina.dao.AgentDao;
import com.dev.magazina.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentDao agentDao;

    @Override
    public List<Agent> findAll() {
        return null;
    }

    @Override
    public Agent findById(int id) {
        return null;
    }

    @Override
    public void save(Agent agent) {

    }

    @Override
    public void delete(Agent agent) {

    }
}
