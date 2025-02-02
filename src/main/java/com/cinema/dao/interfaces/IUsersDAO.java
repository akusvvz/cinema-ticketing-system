package com.cinema.dao.interfaces;

import com.cinema.entities.Users;
import java.util.List;

public interface IUsersDAO {
    List<Users> getAllUsers();
    Users getUserById(int id);
    boolean addUser(Users user);
    Users authenticateUser(String email, String password);
    boolean updateUser(Users user);
    boolean deleteUser(int id);
}
