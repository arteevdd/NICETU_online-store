package test.project.onlineshop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByUserId(Integer userId);

    Optional<User> findUserByEmail(String email);

    Iterable<User> findAll();

//    @Modifying
//    @Transactional
//    @Query("UPDATE User u " +
//            "SET u.firstName = :firstName, u.secondName = :secondName " +
//            "WHERE u.userId = :userId")
//    void updateUserInitials(@Param("userId") Integer userId,
//                            @Param("firstName") String firstName,
//                            @Param("secondName") String secondName);

    @Modifying
    @Transactional
    @Query("UPDATE User u " +
            "SET u.email = :email " +
            "WHERE u.userId = :userId")
    void updateEmail(@Param("userId") Integer userId,
                     @Param("email") String email);

    @Transactional
    void deleteUserByEmail(String email);

    @Transactional
    void deleteUserByUserId(Integer userId);

}
