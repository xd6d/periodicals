package com.example.service.impl;

import com.example.dao.PublicationRepository;
import com.example.dao.TopicRepository;
import com.example.dao.impl.PublicationRepositoryImpl;
import com.example.dao.impl.TopicRepositoryImpl;
import com.example.entity.Publication;
import com.example.entity.Topic;
import com.example.entity.User;
import com.example.service.PublicationService;

import java.util.*;
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
    public boolean createPublication(String title, String priceStr, String[] topicIds) {
        if (title == null || priceStr == null || !priceStr.matches("^[+]?[\\d]+[.,]?[\\d]{0,2}$"))
            return false;
        double price = Double.parseDouble(priceStr);
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
    public Publication getPublicationById(String id) {
        Publication publication = null;
        if (id != null && id.matches("^[+-]?[\\d]+$"))
            publication = getPublicationById(Integer.parseInt(id));
        if (publication == null)
            throw new IllegalArgumentException("Incorrect publication id.");
        return publication;
    }

    @Override
    public boolean delete(String publicationId) {
        if (publicationId == null || !publicationId.matches("^[+-]?[\\d]+$"))
            return false;
        int id = Integer.parseInt(publicationId);
        return pr.delete(id);
    }

    @Override
    public boolean edit(String publicationIdSrt, String title, String price, String[] topicIds) {
        if (publicationIdSrt == null || title == null || price == null
                || !publicationIdSrt.matches("^[+-]?[\\d]+$") || !price.matches("^[+]?[\\d]+[.,]?[\\d]{0,2}$"))
            return false;
        List<Topic> topics = new ArrayList<>();
        if (topicIds != null)
            Arrays.stream(topicIds).forEach(id -> topics.add(tr.findById(Integer.parseInt(id))));
        return pr.update(Integer.parseInt(publicationIdSrt), title, Double.parseDouble(price), topics);
    }

    @Override
    public List<Publication> providePublications(String topicId, String search, String sortName, String reversedName) {
        List<Publication> publications = getAllPublications();
        return sortPublications(topicId, search, sortName, reversedName, publications);
    }

    @Override
    public List<Publication> provideUsersPublications(User user, String topicId, String search, String sortName, String reversedName) {
        List<Publication> publications = user.getPublications();
        return sortPublications(topicId, search, sortName, reversedName, publications);
    }

    private List<Publication> sortPublications(String topicId, String search, String sortName, String reversedName, List<Publication> publications) {
        Topic topic = null;
        if (topicId != null && topicId.matches("^[+-]?[\\d]+$")) {
            topic = tr.findById(Integer.parseInt(topicId));
        }
        if (topic != null)
            publications = selectPublicationsByTopic(topic, publications);
        if (search != null)
            publications = selectPublicationsBySearch(search, publications);
        if (sortName == null) {
            publications.sort(Comparator.comparingInt(Publication::getId).reversed());
            return publications;
        }
        if (sortName.equals("title"))
            publications.sort(Comparator.comparing(Publication::getTitle));
        if (sortName.equals("price"))
            publications.sort(Comparator.comparingDouble(Publication::getPrice));
        if (reversedName == null || reversedName.equals("false"))
            return publications;
        if (reversedName.equals("true"))
            Collections.reverse(publications);
        return publications;
    }
}
