package nl.novi.eindopdrachtbackendlibrary.repositories;

import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.LoanActivityEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanActivityRepository extends JpaRepository<LoanActivityEntity, Long> {
    List<LoanActivityEntity> findByUser(UserEntity user);
    List<LoanActivityEntity> findByBook(BookEntity book);
    List<LoanActivityEntity> findByReturnDateNull();

}
