package com.example.service.impl;

import com.example.dao.TopicRepository;
import com.example.dao.impl.TopicRepositoryImpl;
import com.example.entity.Topic;
import com.example.service.TopicService;

import java.util.Comparator;
import java.util.List;

public class TopicServiceImpl implements TopicService {
    private final TopicRepository tr = new TopicRepositoryImpl();


    @Override
    public List<Topic> getAllTopics() {
        return tr.findAll();
    }

    @Override
    public List<Topic> getAllAndSortByName() {
        List<Topic> topics = tr.findAll();
        topics.sort(Comparator.comparing(Topic::getName));
        return topics;
    }

    @Override
    public Topic getTopicById(int id) {
        return tr.findById(id);
    }

    @Override
    public Topic getTopicByStringId(String strId) {
        int id;
        if (strId != null && strId.matches("^[+-]?[\\d]+$"))
            id = Integer.parseInt(strId);
        else
            return null;
        return getTopicById(id);
    }

    @Override
    public boolean createTopic(Topic topic) {
        return tr.insertTopic(topic);
    }
}
