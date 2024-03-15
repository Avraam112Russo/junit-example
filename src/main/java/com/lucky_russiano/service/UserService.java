package com.lucky_russiano.service;

import com.lucky_russiano.DAO.UserDao;
import com.lucky_russiano.entity.MyUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public class UserService {
    private final List<MyUser>list = new ArrayList<>();
    private Map<Long, MyUser> myUserMap = new HashMap<>();
    private final UserDao userDao;

    public List<MyUser> getAllUsers(){
        return new ArrayList<>(list);
    }
    public void addUserToList(MyUser... myUser){
        this.list.addAll(Arrays.asList(myUser));
    }
    public Map<Long, MyUser> convertUserToMap(MyUser... user){
        Arrays.asList(user)
                .stream().forEach(myUser -> myUserMap.put(myUser.getId(), myUser));
        return this.myUserMap;
    }
    public Optional<MyUser>login(String username, String pass){
        if (username == null || pass == null) {
            throw new IllegalArgumentException();
        }
        Optional<MyUser> mayBeUser = list.stream()
                .filter(myUser -> myUser.getUsername().equals(username) && myUser.getPassword().equals(pass))
                .findFirst();
        if (mayBeUser.isPresent()){
            return mayBeUser;
        }
        return Optional.empty();
    }
    public boolean deleteById(Long userId){
        return userDao.deleteUserById(userId);
    }
}
