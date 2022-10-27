package com.example.dao.impl;

import com.example.dao.ConnectionAdapter;
import com.example.dao.PublicationRepository;
import com.example.dao.TopicRepository;
import com.example.entity.Publication;
import com.example.entity.Topic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class PublicationRepositoryImpl implements PublicationRepository {
    private final Connection connection = ConnectionAdapter.getConnection();
    private final TopicRepository tr = new TopicRepositoryImpl();
    private static final Logger logger = LogManager.getLogger(PublicationRepositoryImpl.class);

    private static final String FIND_ALL = "SELECT * FROM publications ORDER BY id DESC";
    private static final String GET_TOPICS_BY_PUBLICATION_ID
            = "SELECT \"topicID\" FROM publications_topics WHERE \"publicationID\"=%d";
    private static final String FIND_BY_ID = "SELECT * FROM publications WHERE \"id\"=%d";
    private static final String FIND_BY_TITLE = "SELECT * FROM publications WHERE \"title\"='%s'";
    private static final String FIND_ALL_BY_TOPIC = "SELECT \"publicationID\" FROM publications_topics WHERE \"topicID\"=%d";
    private static final String INSERT_INTO_PUBLICATIONS = "INSERT INTO publications(id, title, price) VALUES (DEFAULT, '%s', %f);";
    private static final String INSERT_TOPICS = "INSERT INTO publications_topics(id, \"publicationID\", \"topicID\") VALUES (DEFAULT, %d, %d);";
    private static final String DELETE = "DELETE FROM publications WHERE \"id\"=%d;";
    private static final String UPDATE = "UPDATE publications SET title = '%s', price = %.2f WHERE \"id\"=%d;";
    private static final String DELETE_TOPICS = "DELETE FROM publications_topics WHERE \"publicationID\"=%d;";


    @Override
    public List<Publication> findAll() {
        List<Publication> publications = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(FIND_ALL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                publications.add(
                        new Publication(
                                id,
                                resultSet.getString("title"),
                                resultSet.getDouble("price"),
                                getTopicsByPublicationId(id)));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return publications;
    }

    private List<Topic> getTopicsByPublicationId(int id) {
        List<Topic> topics = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(GET_TOPICS_BY_PUBLICATION_ID, id));
            while (resultSet.next()) {
                topics.add(tr.findById(resultSet.getInt("topicID")));
            }
            topics.sort(Comparator.comparing(Topic::getName));
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return topics;
    }

    @Override
    public List<Publication> findAllByTopic(Topic topic) {
        List<Publication> publications = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_ALL_BY_TOPIC, topic.getId()));
            while (resultSet.next()) {
                publications.add(findById(resultSet.getInt("publicationID")));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return publications;
    }

    @Override
    public Publication findById(int id) {
        Publication publication = null;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_BY_ID, id));
            while (resultSet.next()) {
                publication = new Publication(
                        id,
                        resultSet.getString("title"),
                        resultSet.getDouble("price"),
                        getTopicsByPublicationId(id));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return publication;
    }

    @Override
    public boolean insertPublication(Publication entity) {
        int res = 0;
        try {
            connection.setAutoCommit(false);
            res = connection.createStatement().executeUpdate(
                    String.format(Locale.US, INSERT_INTO_PUBLICATIONS, entity.getTitle(), entity.getPrice())
            );
            int entityID = findByTitle(entity.getTitle()).getId();
            for (Topic topic : entity.getTopics()) {
                res += connection.createStatement().executeUpdate(
                        String.format(INSERT_TOPICS, entityID, topic.getId())
                );
            }
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1 + entity.getTopics().size();
    }

    @Override
    public Publication findByTitle(String title) {
        Publication publication = null;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_BY_TITLE, title));
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                publication = new Publication(
                        id,
                        title,
                        resultSet.getDouble("price"),
                        getTopicsByPublicationId(id));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return publication;
    }

    @Override
    public boolean delete(int publicationId) {
        int res = 0;
        try {
            res = connection.createStatement().executeUpdate(
                    String.format(DELETE, publicationId)
            );
            System.out.println(publicationId);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }

    @Override
    public boolean update(int id, String title, double price, List<Topic> topics) {
        int res = 0;
        try {
            connection.setAutoCommit(false);
            res = connection.createStatement().executeUpdate(
                    String.format(Locale.US, UPDATE, title, price, id)
            );
            connection.createStatement().executeUpdate(
                    String.format(DELETE_TOPICS, id)
            );
            for (Topic topic : topics)
                res += connection.createStatement().executeUpdate(
                        String.format(INSERT_TOPICS, id, topic.getId())
                );
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1 + topics.size();
    }
}
