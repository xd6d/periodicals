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
                .filter(p -> p.getTitle().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT)) ||
                        p.getTitleEN().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT)))
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
    public boolean createPublication(String title, String titleUK, String priceStr, String[] topicIds) {
        if (title == null || priceStr == null || titleUK==null || !priceStr.matches("^[+]?[\\d]+[.,]?[\\d]{0,2}$"))
            return false;
        double price = Double.parseDouble(priceStr);
        List<Topic> topics = new ArrayList<>();
        if (topicIds != null)
            Arrays.stream(topicIds).forEach(id -> topics.add(tr.findById(Integer.parseInt(id))));
        Publication publication = new Publication(title, titleUK, price, topics);
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
            throw new IllegalArgumentException("incorrect_publication_id");
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
        return pr.update(new Publication(Integer.parseInt(publicationIdSrt), title, Double.parseDouble(price), topics));
    }

    @Override
    public boolean edit(String publicationIdSrt, String title, String titleUK, String price, String[] topicIds) {
        if (publicationIdSrt == null || title == null || titleUK == null || price == null
                || !publicationIdSrt.matches("^[+-]?[\\d]+$") || !price.matches("^[+]?[\\d]+[.,]?[\\d]{0,2}$"))
            return false;
        List<Topic> topics = new ArrayList<>();
        if (topicIds != null)
            Arrays.stream(topicIds).forEach(id -> topics.add(tr.findById(Integer.parseInt(id))));
        return pr.update(new Publication(Integer.parseInt(publicationIdSrt), title, titleUK, Double.parseDouble(price), topics));
    }

    @Override
    public List<Publication> providePublications(String topicId, String search, String sortName, String reversedName, String locale) {
        List<Publication> publications = getAllPublicationsByLocale(locale);
        return sortPublications(topicId, search, sortName, reversedName, publications);
    }

    @Override
    public List<Publication> getAllPublicationsByLocale(String locale) {
        List<Publication> publications = pr.findAll();
        if (locale.equals("uk"))
            publications.forEach(p -> {
                p.setTitle(p.getTitleUK());
                p.getTopics().forEach(t -> t.setName(t.getNameUK()));
            });
        return publications;
    }

    @Override
    public List<Publication> provideUsersPublications(User user, String topicId, String search, String sortName, String reversedName) {
        List<Publication> publications = user.getPublications();
        return sortPublications(topicId, search, sortName, reversedName, publications);
    }

    @Override
    public List<Publication> provideUsersPublications(User user, String topicId, String search, String sortName, String reversedName, String locale) {
        List<Publication> publications = user.getPublications();
        if (locale.equals("uk"))
            publications.forEach(p -> {
                p.setTitle(p.getTitleUK());
                p.getTopics().forEach(t -> t.setName(t.getNameUK()));
            });
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
            publications.sort(Comparator.comparing(Publication::getTitle, String::compareToIgnoreCase));
        if (sortName.equals("price"))
            publications.sort(Comparator.comparingDouble(Publication::getPrice));
        if (reversedName == null || reversedName.equals("false"))
            return publications;
        if (reversedName.equals("true"))
            Collections.reverse(publications);
        return publications;
    }
}
