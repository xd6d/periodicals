package com.example.service.impl;

import com.example.dao.PublicationRepository;
import com.example.dao.TopicRepository;
import com.example.dao.impl.PublicationRepositoryImpl;
import com.example.dao.impl.TopicRepositoryImpl;
import com.example.entity.Publication;
import com.example.entity.Topic;
import com.example.service.PublicationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PublicationServiceImpl implements PublicationService {
    PublicationRepository pr = new PublicationRepositoryImpl();
    TopicRepository tr = new TopicRepositoryImpl();

    @Override
    public List<Publication> getAllPublications() {
        return pr.findAll();
    }

    @Override
    public List<Publication> getPublicationsByTopic(Topic topic) {
        return pr.findAllByTopic(topic);
    }

    @Override
    public List<Publication> selectPublicationsBySearch(String search, List<Publication> publications) {
        return publications
                .stream()
                .filter(p -> p.getTitle().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Publication> selectPublicationsByTopic(Topic topic, List<Publication> publications) {
        return publications
                .stream()
                .filter(p -> p.getTopics().contains(topic))
                .collect(Collectors.toList());
    }

    @Override
    public boolean createPublication(String title, double price, String[] topicIds) {
        List<Topic> topics = new ArrayList<>();
        if (topicIds != null)
            Arrays.stream(topicIds).forEach(id -> topics.add(tr.findById(Integer.parseInt(id))));
        Publication publication = new Publication(title, price, topics);
        return pr.insertPublication(publication);
    }

    @Override
    public Publication getPublicationById(int id) {
        return pr.findById(id);
    }

    @Override
    public boolean delete(String publicationId) {
        int id = -1;
        if (publicationId != null && publicationId.matches("^[+-]?[\\d]+$"))
            id = Integer.parseInt(publicationId);
        return pr.delete(id);
    }

    @Override
    public boolean edit(String publicationIdSrt, String title, String price, String[] topicIds) {
        List<Topic> topics = new ArrayList<>();
        if (topicIds != null)
            Arrays.stream(topicIds).forEach(id -> topics.add(tr.findById(Integer.parseInt(id))));
        return pr.update(Integer.parseInt(publicationIdSrt), title, Double.parseDouble(price), topics);
    }
}
