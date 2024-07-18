package HanaInsurance.HanaInsurance.repository;

import HanaInsurance.HanaInsurance.entity.InsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsProductRepository extends JpaRepository<InsProduct, String> {
  InsProduct findByInsuranceId(String insuranceId);
}
