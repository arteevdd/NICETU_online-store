package test.project.onlineshop.service.user;

import test.project.onlineshop.dto.AuthRequest;
import test.project.onlineshop.dto.RegistrationRequest;

import java.util.Map;

public interface UserService {

    void registration(RegistrationRequest user);

    Map<String, String> login(AuthRequest authRequest);
}
