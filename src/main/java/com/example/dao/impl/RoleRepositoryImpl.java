package com.example.dao.impl;

import com.example.dao.ConnectionAdapter;
import com.example.dao.RoleRepository;
import com.example.entity.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRepositoryImpl implements RoleRepository {
    private final Connection connection = ConnectionAdapter.getConnection();
    private static final Logger logger = LogManager.getLogger(RoleRepositoryImpl.class);

    private static final String FIND_ROLE_BY_ID = "SELECT * FROM roles WHERE \"id\"=%d";

    @Override
    public Role findRoleById(int id) {
        Role role = null;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_ROLE_BY_ID, id));
            if (resultSet.next())
                role = new Role(id, resultSet.getString("role"));
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return role;
    }
}
