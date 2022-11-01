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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PublicationRepositoryImpl implements PublicationRepository {
    private final Connection connection = ConnectionAdapter.getConnection();
    private final TopicRepository tr = new TopicRepositoryImpl();
    private static final Logger logger = LogManager.getLogger(PublicationRepositoryImpl.class);

    private static final String FIND_ALL = "SELECT * FROM publications ORDER BY id DESC";
    private static final String GET_TOPICS_BY_PUBLICATION_ID
            = "SELECT \"topicID\" FROM publications_topics WHERE \"publicationID\"=?";
    private static final String FIND_BY_ID = "SELECT * FROM publications WHERE \"id\"=?";
    private static final String FIND_BY_TITLE = "SELECT * FROM publications WHERE \"title\"=?";
    private static final String FIND_BY_TITLEUK = "SELECT * FROM publications WHERE \"titleUK\"=?";
    private static final String FIND_ALL_BY_TOPIC = "SELECT \"publicationID\" FROM publications_topics WHERE \"topicID\"=?";
    private static final String INSERT_INTO_PUBLICATIONS = "INSERT INTO publications(id, title, price, \"titleUK\") VALUES (DEFAULT, ?, ?, ?);";
    private static final String INSERT_TOPICS = "INSERT INTO publications_topics(id, \"publicationID\", \"topicID\") VALUES (DEFAULT, ?, ?);";
    private static final String DELETE = "DELETE FROM publications WHERE \"id\"=?;";
    private static final String UPDATE = "UPDATE publications SET title = ?, price = ?, \"titleUK\" = ? WHERE \"id\"=?;";//3
    private static final String DELETE_TOPICS = "DELETE FROM publications_topics WHERE \"publicationID\"=?;";


    @Override
    public List<Publication> findAll() {
        List<Publication> publications = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                publications.add(
                        new Publication(
                                id,
                                resultSet.getString("title"),
                                resultSet.getString("titleUK"),
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
        try (PreparedStatement ps = connection.prepareStatement(GET_TOPICS_BY_PUBLICATION_ID)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
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
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_BY_TOPIC)) {
            ps.setInt(1, topic.getId());
            ResultSet resultSet = ps.executeQuery();
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
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                publication = new Publication(
                        id,
                        resultSet.getString("title"),
                        resultSet.getString("titleUK"),
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
        int res = 1;
        try (PreparedStatement psInsertPubl = connection.prepareStatement(INSERT_INTO_PUBLICATIONS);
             PreparedStatement psInsertTopics = connection.prepareStatement(INSERT_TOPICS)) {
            connection.setAutoCommit(false);
            psInsertPubl.setString(1, entity.getTitle());
            psInsertPubl.setDouble(2, entity.getPrice());
            psInsertPubl.setString(3, entity.getTitleUK());
            psInsertPubl.executeUpdate();

            int entityID = findByTitle(entity.getTitle()).getId();
            for (Topic topic : entity.getTopics()) {
                psInsertTopics.setInt(1, entityID);
                psInsertTopics.setInt(2, topic.getId());
                psInsertTopics.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            res = 0;
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }

    @Override
    public Publication findByTitle(String title) {
        Publication publication = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_TITLE)) {
            ps.setString(1, title);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                publication = new Publication(
                        id,
                        title,
                        resultSet.getString("titleUK"),
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
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, publicationId);
            res = ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }

    @Override
    public boolean update(Publication entity) {
        int res = 1;
        try (PreparedStatement psUpdate = connection.prepareStatement(UPDATE);
             PreparedStatement psDeleteTopics = connection.prepareStatement(DELETE_TOPICS);
             PreparedStatement psInsertTopics = connection.prepareStatement(INSERT_TOPICS)) {
            connection.setAutoCommit(false);
            psUpdate.setString(1, entity.getTitle());
            psUpdate.setDouble(2, entity.getPrice());
            psUpdate.setString(3, entity.getTitleUK());
            psUpdate.setInt(4, entity.getId());
            psUpdate.executeUpdate();

            psDeleteTopics.setInt(1, entity.getId());
            psDeleteTopics.executeUpdate();
            for (Topic topic : entity.getTopics()) {
                psInsertTopics.setInt(1, entity.getId());
                psInsertTopics.setInt(2, topic.getId());
                psInsertTopics.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            res = 0;
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }
}
