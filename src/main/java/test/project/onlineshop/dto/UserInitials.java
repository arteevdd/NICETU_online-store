package test.project.onlineshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInitials {

    String firstName;

    String secondName;

    public UserInitials(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }
}
