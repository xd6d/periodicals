package com.example.service.impl;

import com.example.entity.Publication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublicationServiceImplTest {
    private final PublicationServiceImpl publicationService = new PublicationServiceImpl();
    private final TopicServiceImpl topicService = new TopicServiceImpl();
    private Publication testPublication = new Publication("testPublication", 0.99);

    @Test
    void getAllPublications() {
        assertNotNull(publicationService.getAllPublications());
    }

    @Test
    void getPublicationsByTopic() {
        assertNotNull(publicationService.getPublicationsByTopic(topicService.getTopicById(1)));
    }

    @Test
    void selectPublicationsBySearch() {
        assertNotNull(publicationService.selectPublicationsBySearch("publ", publicationService.getAllPublications()));
    }

    @Test
    void selectPublicationsByTopic() {
        assertNotNull(publicationService.selectPublicationsByTopic(topicService.getTopicById(1), publicationService.getAllPublications()));
    }

    @Test
    void createPublication() {
        assertTrue(publicationService.createPublication(testPublication.getTitle(), testPublication.getPrice(), new String[]{}));
    }

    @Test
    void getPublicationById() {
        assertNotNull(publicationService.getPublicationById(6));
        assertNull(publicationService.getPublicationById(1));
    }

    @Test
    void delete() {
        assertTrue(publicationService.delete("25"));
        assertFalse(publicationService.delete(""));
        assertFalse(publicationService.delete(null));
    }
}