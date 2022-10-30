package com.example.dao.impl;

import com.example.dao.ConnectionAdapter;
import com.example.dao.RoleRepository;
import com.example.entity.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRepositoryImpl implements RoleRepository {
    private final Connection connection = ConnectionAdapter.getConnection();
    private static final Logger logger = LogManager.getLogger(RoleRepositoryImpl.class);

    private static final String FIND_ROLE_BY_ID = "SELECT * FROM roles WHERE \"id\"=?";

    @Override
    public Role findRoleById(int id) {
        Role role = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_ROLE_BY_ID)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                role = new Role(id, resultSet.getString("role"));
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return role;
    }
}
