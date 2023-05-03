package test.project.onlineshop.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.entity.User;
import test.project.onlineshop.exception.UserNotFoundException;
import test.project.onlineshop.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUserId(Integer userId) {
        Optional<User> user = userRepository.findUserByUserId(userId);
        if (user.isPresent()){
            return user.get();
        }else{
            throw new UserNotFoundException("User not found!");
        }
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateEmail(Integer userId, String email) {
        userRepository.updateEmail(userId, email);
    }

    @Override
    public void deleteUserByUserId(Integer userId) {
        userRepository.deleteUserByUserId(userId);
    }
}
