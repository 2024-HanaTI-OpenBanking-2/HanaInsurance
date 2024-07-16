package HanaInsurance.HanaInsurance.repository;

import HanaInsurance.HanaInsurance.entity.InsJoinList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InsJoinListRepository extends JpaRepository<InsJoinList, String> {
  List<InsJoinList> findByCustomerId(String customerId);
}
