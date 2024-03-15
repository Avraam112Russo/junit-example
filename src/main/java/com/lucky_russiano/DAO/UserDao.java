package com.lucky_russiano.DAO;

import lombok.SneakyThrows;

import java.sql.DriverManager;

public class UserDao {

    @SneakyThrows
    public boolean deleteUserById(Long userId){
        DriverManager.getConnection("fake-url", "some username", "some password");
        return true;
    }
}
