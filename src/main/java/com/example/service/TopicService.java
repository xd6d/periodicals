package com.example.service;

import com.example.entity.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();

    List<Topic> getAllAndSortByName();

    Topic getTopicById(int id);

    Topic getTopicByStringId(String strId);

    boolean createTopic(Topic topic);

    List<Topic> provideTopics(String sortName, String locale);

    List<Topic> sortTopics(String sortName, List<Topic> topics);

    List<Topic> getAllByLocale(String locale);
}
