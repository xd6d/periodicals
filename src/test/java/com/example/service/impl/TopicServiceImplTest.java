package com.example.service.impl;


import com.example.entity.Topic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopicServiceImplTest {
    private final TopicServiceImpl topicService = new TopicServiceImpl();
    private final Topic testTopic = new Topic(0, "testTopic", "testTopic");

    @Test
    void getAllTopics() {
        assertNotNull(topicService.getAllTopics());
    }

    @Test
    void createTopic() {
        assertFalse(topicService.createTopic(testTopic));
    }

    @Test
    void getTopicById() {
        assertNotNull(topicService.getTopicById(1));
        assertNull(topicService.getTopicById(-1));
    }

    @Test
    void getTopicByStringId() {
        assertNotNull(topicService.getTopicByStringId("1"));
        assertNull(topicService.getTopicByStringId("-1"));
        assertNull(topicService.getTopicByStringId(""));
        assertNull(topicService.getTopicByStringId(null));
    }
}