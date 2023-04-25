package test.project.onlineshop.service.user;

import test.project.onlineshop.entity.User;

import java.util.List;

public interface UserService {

    User findUserByUserId(Integer userId);

    List<User> findAll();

    User addNewUser(User user);

//    void updateUserInitials(Integer userId, String firstName, String secondName);

    void updateEmail(Integer userId, String email);

    void deleteUserByUserId(Integer userId);
}
