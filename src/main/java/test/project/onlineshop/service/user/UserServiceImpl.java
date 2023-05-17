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
//        TODO: Переписать нормально
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (user.getEmail().matches(emailPattern)){
            Optional<User> tempUser = userRepository.findUserByEmail(user.getEmail());
            if (!tempUser.isPresent()){
                return userRepository.save(user);
            }else {
                throw new RuntimeException("User email: " + user.getEmail() + " exist!");
            }

        }else {
            throw new IllegalArgumentException("Invalid email pattern");
        }
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
