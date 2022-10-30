package com.example.dao;

import com.example.entity.Publication;
import com.example.entity.Topic;

import java.util.List;

public interface PublicationRepository {
    List<Publication> findAll();

    List<Publication> findAllByTopic(Topic topic);

    Publication findById(int id);

    boolean insertPublication(Publication entity);

    Publication findByTitle(String title);

    boolean delete(int publicationId);

    boolean update(Publication entity);
}
