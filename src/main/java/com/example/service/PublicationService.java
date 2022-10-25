package com.example.service;

import com.example.entity.Publication;
import com.example.entity.Topic;
import com.example.entity.User;

import java.util.List;

public interface PublicationService {
    List<Publication> getAllPublications();

    List<Publication> getPublicationsByTopic(Topic topic);

    List<Publication> selectPublicationsBySearch(String search, List<Publication> publications);

    List<Publication> selectPublicationsByTopic(Topic topic, List<Publication> publications);

    boolean createPublication(String title, String price, String[] topicIds);

    Publication getPublicationById(int id);

    Publication getPublicationById(String id);

    boolean delete(String publicationId);

    boolean edit(String publicationIdSrt, String title, String price, String[] topics);

    List<Publication> providePublications(String topicId, String search, String sortName, String reversedName);

    List<Publication> provideUsersPublications(User user, String topicId, String search, String sortName, String reversedName);
}
