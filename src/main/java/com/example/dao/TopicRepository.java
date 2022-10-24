package com.example.dao;


import com.example.entity.Topic;

import java.util.List;

public interface TopicRepository {
    List<Topic> findAll();
    Topic findById(int id);
    boolean insertTopic(Topic entity);
    Topic findByName(String name);
}
