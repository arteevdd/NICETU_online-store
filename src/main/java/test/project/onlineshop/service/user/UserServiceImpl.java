package test.project.onlineshop.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.AuthRequest;
import test.project.onlineshop.dto.RegistrationRequest;
import test.project.onlineshop.entity.Role;
import test.project.onlineshop.entity.User;
import test.project.onlineshop.exception.RoleNotFoundException;
import test.project.onlineshop.exception.UserExistentException;
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

    public final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registration(RegistrationRequest user) {
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (user.getEmail().matches(emailPattern)){
            Optional<User> tempUser = userRepository.findUserByEmail(user.getEmail());
            Optional<Role> userRole = roleRepository.findRoleByRoleName("ROLE_USER");
            if (tempUser.isPresent()){
                throw new UserExistentException("User email: " + user.getEmail() + " exist!");
            }else {
                if (userRole.isPresent()) {
                    userRepository.save(
                            User.builder()
                                    .firstName(user.getFirstName())
                                    .secondName(user.getSecondName())
                                    .email(user.getEmail())
                                    .password(passwordEncoder.encode(user.getPassword()))
                                    .roleId(userRole.get())
                                    .build()
                    );
                }else {
                    throw new RoleNotFoundException("Role not found!");
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
                response.put("email", loginedUser.get().getEmail());
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
