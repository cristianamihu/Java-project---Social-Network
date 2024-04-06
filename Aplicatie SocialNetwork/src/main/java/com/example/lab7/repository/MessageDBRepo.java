package com.example.lab7.repository;

import com.example.lab7.domain.FriendRequest;
import com.example.lab7.domain.Message;
import com.example.lab7.domain.User;
import com.example.lab7.domain.validator.ValidationException;
import com.example.lab7.domain.validator.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class MessageDBRepo implements Repository<Long,Message> {
    private final String url;
    private final String username;
    private final String password;

    public MessageDBRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public Optional<Message> findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM messages WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long message_from = resultSet.getLong("message_from");
                    Long message_to = resultSet.getLong("message_to");
                    String sent = resultSet.getString("sent");
                    String message_text = resultSet.getString("message_text");

                    LocalTime sent_parsed = LocalTime.parse(sent);

                    Message message = new Message(message_from, message_to, sent_parsed, message_text);
                    message.setId(id);
                    return Optional.ofNullable(message);
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long message_from = resultSet.getLong("message_from");
                Long message_to = resultSet.getLong("message_to");
                String sent = resultSet.getString("sent");
                String message_text = resultSet.getString("message_text");

                LocalTime sent_parsed = LocalTime.parse(sent);

                Message message = new Message(message_from, message_to, sent_parsed, message_text);
                message.setId(id);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public long size() {
        long size = 0;
        for(Message message: this.findAll())
            size++;
        return size;
    }

    @Override
    public Optional<Message> save(Message entity) {
        String sql = "INSERT INTO messages (message_from, message_to, sent, message_text) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, entity.getFrom());
            ps.setLong(2, entity.getTo());
            ps.setString(3, entity.getWhenWasSended().toString());
            ps.setString(4, entity.getText());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                    return Optional.of(entity);
                } else {
                    throw new SQLException("Failed to get the generated ID for the message.");
                }
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> remove(Long aLong) {
        return null;
    }

    @Override
    public Optional<Message> update(Message entity) {
        return null;
    }
}
