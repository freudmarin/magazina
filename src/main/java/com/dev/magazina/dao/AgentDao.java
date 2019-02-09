package com.dev.magazina.dao;

import com.dev.magazina.model.Agent;

import java.util.List;

public interface AgentDao {
    List<Agent> findAll();

    Agent findById(int id);

    void save(Agent agent);

    void delete(Agent agent);
}
