package test.project.onlineshop.service.user;

import test.project.onlineshop.dto.AuthRequest;
import test.project.onlineshop.dto.UserRequest;

import java.util.Map;

public interface UserService {

    void registration(UserRequest user);

    Map<String, String> login(AuthRequest authRequest);
}
