package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.model.Account;

@Repository
public interface AccountRepository extends JpaRepository <Account, Long>{
}
