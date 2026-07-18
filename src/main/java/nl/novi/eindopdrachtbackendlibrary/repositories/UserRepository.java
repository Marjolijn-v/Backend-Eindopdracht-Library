package nl.novi.eindopdrachtbackendlibrary.repositories;

import nl.novi.eindopdrachtbackendlibrary.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
