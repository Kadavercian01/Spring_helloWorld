package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    String command;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        command = "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(255) NOT NULL,\n" +
                "  `lastName` VARCHAR(255) NOT NULL,\n" +
                "  `age` TINYINT NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(command);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void dropUsersTable() {
        command = "drop table if exists users";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(command);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        command = "INSERT INTO users (name, lastName, age) Values(?, ?, ?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(command))
        {   preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, String.valueOf(age));
            int rows = preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        command = "delete from users where id = ?";
        try (PreparedStatement  preparedStatement = Util.getConnection().prepareStatement(command)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        command = "select * from users;";
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(command))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next() && !resultSet.wasNull()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            System.out.println(users.toString());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        command = "truncate table users";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(command);
            statement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
