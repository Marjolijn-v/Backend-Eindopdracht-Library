package nl.novi.eindopdrachtbackendlibrary.repositories;

import nl.novi.eindopdrachtbackendlibrary.entities.LoanActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanActivityRepository extends JpaRepository<LoanActivityEntity, Long> {
}
