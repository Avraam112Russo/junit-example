package com.lucky_russiano.service.dao;

import com.lucky_russiano.DAO.UserDao;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class UserDaoSpy extends UserDao {
    private final UserDao userDao;
    private final Map<Long, Boolean> answers = new HashMap<>();

    @Override
    public boolean deleteUserById(Long userId) {
        return answers.getOrDefault(userId, userDao.deleteUserById(userId));
    }
}
