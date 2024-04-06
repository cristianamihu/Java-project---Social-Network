package com.example.lab7.repository;

import com.example.lab7.domain.FriendRequest;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class FriendRequestDBRepo implements Repository<Long, FriendRequest> {
    private final String url;
    private final String username;
    private final String password;

    public FriendRequestDBRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<FriendRequest> findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM requests WHERE id = ?");) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long idUser1 = resultSet.getLong("idUser1");
                    Long idUser2 = resultSet.getLong("idUser2");
                    String send_at = resultSet.getString("send_at");
                    LocalDate send_atParsed = LocalDate.parse(send_at);
                    FriendRequest friendRequest = new FriendRequest(idUser1, idUser2, send_atParsed);
                    friendRequest.setId(id);
                    return Optional.ofNullable(friendRequest);
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
    public Iterable<FriendRequest> findAll() {
        Set<FriendRequest> friendshipRequests = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from requests");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = (long)resultSet.getInt("id");
                Long id_user = (long)resultSet.getInt("idUser1");
                Long id_requestedFriend =(long) resultSet.getInt("idUser2");
                String dateRequest = resultSet.getString("send_at");
                LocalDate date_parsed = LocalDate.parse(dateRequest);
                String status = resultSet.getString("status");

                FriendRequest friendship_request = new FriendRequest(id_user,id_requestedFriend,date_parsed);
                friendship_request.setId(id);
                friendship_request.setStatus(status);
                friendshipRequests.add(friendship_request);
            }
            return friendshipRequests;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<FriendRequest> save (FriendRequest entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO requests (idUser1, idUser2, send_at, status) VALUES (?, ?, ?, ?)");) {
            preparedStatement.setInt(1, (int)entity.getIdUser1());
            preparedStatement.setInt(2, (int)entity.getIdUser2());
            preparedStatement.setString(3, entity.getSendRequest().toString());
            preparedStatement.setString(4, entity.getStatus());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 0 ? Optional.empty() : Optional.ofNullable(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<FriendRequest> remove(Long id) {
        Optional<FriendRequest> user = this.findOne(id);
        int affectedRows = 0;
        if (user.isPresent()) {
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM requests WHERE id = ?");) {
                preparedStatement.setString(1, id.toString());
                affectedRows = preparedStatement.executeUpdate();
                return affectedRows == 0 ? Optional.empty() : user;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<FriendRequest> update(FriendRequest entity) {
        try(Connection connection= DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE requests SET idUser1 = ?, idUser2 = ?, send_at = ?, status = ? WHERE id = ?");) {
            preparedStatement.setInt(1, (int)entity.getIdUser1());
            preparedStatement.setInt(2, (int)entity.getIdUser2());
            preparedStatement.setString(3, entity.getSendRequest().toString());
            preparedStatement.setString(4, entity.getStatus());
            preparedStatement.setLong(5, entity.getId());
            int affectedRows=preparedStatement.executeUpdate();
            return affectedRows == 0 ? Optional.ofNullable(entity) : Optional.empty();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public long size() {
        int size = 0;
        for (FriendRequest friendship : this.findAll()) {
            size++;
        }
        return size;
    }
}