package nl.novi.eindopdrachtbackendlibrary.repositories;

import nl.novi.eindopdrachtbackendlibrary.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
