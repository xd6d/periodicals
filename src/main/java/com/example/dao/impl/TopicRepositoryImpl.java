package com.example.dao.impl;

import com.example.dao.ConnectionAdapter;
import com.example.dao.TopicRepository;
import com.example.entity.Topic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryImpl implements TopicRepository {
    private final Connection connection = ConnectionAdapter.getConnection();
    private static final Logger logger = LogManager.getLogger(TopicRepositoryImpl.class);

    private static final String FIND_ALL = "SELECT * FROM topics ORDER BY name";
    private static final String FIND_BY_ID = "SELECT * FROM topics WHERE \"id\"=?";
    private static final String FIND_BY_NAME = "SELECT * FROM topics WHERE \"name\"=?";
    private static final String INSERT = "INSERT INTO topics(id, name) VALUES (DEFAULT, ?);";

    @Override
    public List<Topic> findAll() {
        List<Topic> topics = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                topics.add(new Topic(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("nameUK")));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return topics;
    }

    @Override
    public Topic findById(int id) {
        Topic topic = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                topic = new Topic(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("nameUK"));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return topic;
    }

    @Override
    public boolean insertTopic(Topic entity) {
        int res = 0;
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, entity.getName());
            res = ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }

    @Override
    public Topic findByName(String name) {
        Topic topic = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_NAME)) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                topic = new Topic(
                        resultSet.getInt("id"),
                        name,
                        resultSet.getString("nameUK"));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return topic;
    }
}
