package com.example.dao.impl;

import com.example.dao.ConnectionAdapter;
import com.example.dao.PublicationRepository;
import com.example.dao.RoleRepository;
import com.example.dao.UserRepository;
import com.example.entity.Publication;
import com.example.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserRepositoryImpl implements UserRepository {
    private final Connection connection = ConnectionAdapter.getConnection();
    private final RoleRepository rr = new RoleRepositoryImpl();
    private final PublicationRepository pr = new PublicationRepositoryImpl();
    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);

    private static final String FIND_USER_BY_USERNAME = "SELECT * FROM users WHERE \"username\"=?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE \"email\"=?";
    private static final String FIND_PASSWORD_BY_EMAIL = "SELECT password FROM users WHERE \"email\"=?";
    private static final String INSERT = "INSERT INTO users (id, username, email, password, \"roleID\", balance, \"isBlocked\") VALUES (DEFAULT, ?, ?, ?, DEFAULT, DEFAULT, DEFAULT);";
    private static final String UPDATE_USER_BALANCE = "UPDATE users SET balance = ? WHERE id = ?";
    private static final String SELECT_PUBLICATIONS = "SELECT \"publicationID\" FROM users_publications WHERE \"userID\"=?;";
    private static final String INSERT_PUBLICATION = "INSERT INTO users_publications(id, \"userID\", \"publicationID\") VALUES (DEFAULT, ?, ?);";
    private static final String FIND_ALL = "SELECT * FROM users ORDER BY id;";
    private static final String BLOCK = "UPDATE users SET \"isBlocked\" = true WHERE id = ?";
    private static final String UNBLOCK = "UPDATE users SET \"isBlocked\" = false WHERE id = ?";

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_USERNAME)) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                user = new User(
                        id,
                        username,
                        resultSet.getString("email"),
                        rr.findRoleById(resultSet.getInt("roleID")),
                        resultSet.getDouble("balance"),
                        getPublicationsByUserID(id),
                        resultSet.getBoolean("isBlocked")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return user;
    }

    private List<Publication> getPublicationsByUserID(int id) {
        List<Publication> publications = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_PUBLICATIONS)) {
//            ResultSet resultSet = connection.createStatement().executeQuery(String.format(SELECT_PUBLICATIONS, id));
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                publications.add(pr.findById(resultSet.getInt("publicationID")));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return publications;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
//            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_USER_BY_EMAIL, email));
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                user = new User(
                        id,
                        resultSet.getString("username"),
                        email,
                        rr.findRoleById(resultSet.getInt("roleID")),
                        resultSet.getDouble("balance"),
                        getPublicationsByUserID(id),
                        resultSet.getBoolean("isBlocked")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return user;
    }

    @Override
    public boolean insertUser(User entity, String password) {
        int res = 0;
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
//            res = connection.createStatement().executeUpdate(
//                    String.format(Locale.US, INSERT,
//                            entity.getUsername(),
//                            entity.getEmail(),
//                            password)
//            );

            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getEmail());
            ps.setString(3, password);
            res = ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }

    @Override
    public String getPasswordByEmail(String email) {
        String password = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_PASSWORD_BY_EMAIL)) {
//            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_PASSWORD_BY_EMAIL, email));
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                password = resultSet.getString("password");
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return password;
    }

    @Override
    public boolean updateUserBalance(User user, double amount) {
        int res = 0;
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USER_BALANCE)) {
//            res = connection.createStatement().executeUpdate(
//                    String.format(Locale.US, UPDATE_USER_BALANCE,
//                            user.getBalance() + amount,
//                            user.getId())
//            );
            BigDecimal balance = BigDecimal.valueOf(user.getBalance() + amount);
            balance = balance.setScale(2, RoundingMode.HALF_UP);
            ps.setDouble(1, balance.doubleValue());
            ps.setInt(2, user.getId());
            res = ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }

    @Override
    public boolean subscribe(User user, int publicationId) {
        int res = 0;
        try (PreparedStatement ps = connection.prepareStatement(INSERT_PUBLICATION)) {
//            res = connection.createStatement().executeUpdate(
//                    String.format(INSERT_PUBLICATION,
//                            user.getId(),
//                            publicationId)
//            );
            ps.setInt(1, user.getId());
            ps.setInt(2, publicationId);
            res = ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                users.add(
                        new User(
                                id,
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                rr.findRoleById(resultSet.getInt("roleID")),
                                resultSet.getDouble("balance"),
                                getPublicationsByUserID(id),
                                resultSet.getBoolean("isBlocked")
                        )
                );
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return users;
    }

    @Override
    public boolean block(User user) {
        return blockUnblock(user, BLOCK);
    }

    @Override
    public boolean unblock(User user) {
        return blockUnblock(user, UNBLOCK);
    }

    private boolean blockUnblock(User user, String blockUnblock) {
        int res = 0;
        try (PreparedStatement ps = connection.prepareStatement(blockUnblock)) {
            ps.setInt(1, user.getId());
            res = ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
        return res == 1;
    }
}
