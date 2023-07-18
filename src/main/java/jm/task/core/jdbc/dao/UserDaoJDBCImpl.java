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
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` VARCHAR(45) NOT NULL,\n" +
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
        command = "insert into users (name, lastName, age) values('" + name + "', '" + lastName + "', " + age + ");";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(command);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        command = "delete from users where id=" + id + ";";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(command);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        command = "select * from users;";
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(command); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setAge((byte) resultSet.getInt("age"));
                user.setLastName(resultSet.getString("lastName"));
                users.add(user);
            }
            System.out.println(users.toString());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        command = "TRUNCATE from users";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(command);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
