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

    @Override
    public List<Topic> provideTopics(String sortName, String locale) {
        List<Topic> topics = getAllByLocale(locale);
        return sortTopics(sortName, topics);
    }

    @Override
    public List<Topic> sortTopics(String sortName, List<Topic> topics) {
        if (sortName == null)
            return topics;
        if (sortName.equals("name"))
            topics.sort(Comparator.comparing(Topic::getName));
        return topics;
    }


    @Override
    public List<Topic> getAllByLocale(String locale) {
        List<Topic> topics = getAllTopics();
        if (locale.equals("uk"))
            topics.forEach(t -> t.setName(t.getNameUK()));
        return topics;
    }
}
