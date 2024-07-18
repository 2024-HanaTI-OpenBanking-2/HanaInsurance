package HanaInsurance.HanaInsurance.repository;

import HanaInsurance.HanaInsurance.entity.InsAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface InsAccountRepository extends JpaRepository<InsAccount, String> {
  Optional<InsAccount> findByAccountNoAndCustomerId(String accountNo, String customerId);
  List<InsAccount> findByCustomerId(String customerId); //수정 불가

}
