package com.example.dao.impl;

import com.example.dao.ConnectionAdapter;
import com.example.dao.TopicRepository;
import com.example.entity.Topic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryImpl implements TopicRepository {
    private final Connection connection = ConnectionAdapter.getConnection();
    private static final Logger logger = LogManager.getLogger(TopicRepositoryImpl.class);

    private static final String FIND_ALL = "SELECT * FROM topics ORDER BY name";
    private static final String FIND_BY_ID = "SELECT * FROM topics WHERE \"id\"=%d";
    private static final String FIND_BY_NAME = "SELECT * FROM topics WHERE \"name\"='%s'";
    private static final String INSERT = "INSERT INTO topics(id, name) VALUES (DEFAULT, '%s');";

    @Override
    public List<Topic> findAll() {
        List<Topic> topics = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(FIND_ALL);
            while (resultSet.next()) {
                topics.add(new Topic(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return topics;
    }

    @Override
    public Topic findById(int id) {
        Topic topic = null;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_BY_ID, id));
            while (resultSet.next()) {
                topic = new Topic(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return topic;
    }

    @Override
    public boolean insertTopic(Topic entity) {
        int res = 0;
        try {
            res = connection.createStatement().executeUpdate(
                    String.format(INSERT, entity.getName())
            );
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }

    @Override
    public Topic findByName(String name) {
        Topic topic = null;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_BY_NAME, name));
            while (resultSet.next()) {
                topic = new Topic(resultSet.getInt("id"), name);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return topic;
    }
}
