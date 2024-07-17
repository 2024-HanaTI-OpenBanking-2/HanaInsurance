package HanaInsurance.HanaInsurance.repository;

import HanaInsurance.HanaInsurance.entity.InsCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsCustomerRepository extends JpaRepository<InsCustomer, String> {
  InsCustomer findByCustomerIdAndCustomerPassword(String customerId, String customerPassword);
  InsCustomer findByCi(String ci);
  InsCustomer findByCustomerId(String customerId);

}
