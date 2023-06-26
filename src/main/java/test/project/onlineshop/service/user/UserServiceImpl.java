package test.project.onlineshop.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.AuthRequest;
import test.project.onlineshop.dto.UserRequest;
import test.project.onlineshop.entity.Role;
import test.project.onlineshop.entity.User;
import test.project.onlineshop.exception.RoleNotFound;
import test.project.onlineshop.exception.UserExistException;
import test.project.onlineshop.exception.UserNotFoundException;
import test.project.onlineshop.repository.RoleRepository;
import test.project.onlineshop.repository.UserRepository;
import test.project.onlineshop.security.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public final AuthenticationManager authenticationManager;

    public final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           AuthenticationManager authenticationManager,  JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, String> registration(UserRequest user) {
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (user.getEmail().matches(emailPattern)){
            Optional<User> tempUser = userRepository.findUserByEmail(user.getEmail());
            Optional<Role> userRole = roleRepository.findRoleByRoleName("ROLE_USER");
            if (tempUser.isPresent()){
                throw new UserExistException("User email: " + user.getEmail() + " exist!");
            }else {
                if (userRole.isPresent()) {
                    User savedUser = userRepository.save(
                            new User(
                                    user.getFirstName(),
                                    user.getSecondName(),
                                    user.getEmail(),
                                    passwordEncoder.encode(user.getPassword()),
                                    userRole.get())
                    );
                    Map<String, String> response = new HashMap<>();
                    response.put("firsName", savedUser.getFirstName());
                    response.put("secondName", savedUser.getSecondName());
                    response.put("email", savedUser.getEmail());
                    response.put("token", jwtTokenProvider.generateToken(
                            savedUser.getEmail(),
                            savedUser.getRoleId()
                    ));
                    return response;
                }else {
                    throw new RoleNotFound("Role: " + userRole.get().getRoleName() + " not found!");
                }
            }
        }else {
            throw new IllegalArgumentException("Invalid email pattern!");
        }
    }

    @Override
    public Map<String, String> login(AuthRequest authRequest) {
        Optional<User> loginedUser = userRepository.findUserByEmail(authRequest.getEmail());
        if (loginedUser.isPresent()) {
            if (passwordEncoder.matches(authRequest.getPassword(), loginedUser.get().getPassword())) {
                Map<String, String> response = new HashMap<>();
                response.put("firstName", loginedUser.get().getFirstName());
                response.put("secondName", loginedUser.get().getSecondName());
                response.put("email", authRequest.getEmail());
                response.put("token",
                        jwtTokenProvider.generateToken(
                                authRequest.getEmail(),
                                loginedUser.get().getRoleId()
                        )
                );
                return response;
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        }else {
            throw new UserNotFoundException("User with email: " + authRequest.getEmail() + "not exist!");
        }
    }
}
