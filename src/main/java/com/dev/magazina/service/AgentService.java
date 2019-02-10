package com.dev.magazina.service;

import com.dev.magazina.model.Agent;
import com.dev.magazina.model.Category;

import java.util.List;

public interface AgentService {
    List<Agent> findAll();

    List<Agent> findByType(String type);

    Agent findById(int id);

    void save(Agent agent);

    void delete(Agent agent);


}
