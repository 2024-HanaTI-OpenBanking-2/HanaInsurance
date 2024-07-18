package HanaInsurance.HanaInsurance.service;

import HanaInsurance.HanaInsurance.dto.InsJoinListDTO;
import HanaInsurance.HanaInsurance.entity.InsCustomer;
import HanaInsurance.HanaInsurance.entity.InsJoinList;
import HanaInsurance.HanaInsurance.entity.InsProduct;
import HanaInsurance.HanaInsurance.repository.InsCustomerRepository;
import HanaInsurance.HanaInsurance.repository.InsJoinListRepository;
import HanaInsurance.HanaInsurance.repository.InsProductRepository;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {
  @Autowired
  private InsCustomerRepository insCustomerRepository;

  @Autowired
  private InsJoinListRepository insJoinListRepository;

  @Autowired
  private InsProductRepository insProductRepository;

  public InsCustomer authenticate(String customerId, String password) {
    return insCustomerRepository.findByCustomerIdAndCustomerPassword(customerId, password);
  }

  public List<InsJoinListDTO> getJoinListByCustomerId(String customerId) {
    List<InsJoinList> joinLists = insJoinListRepository.findByCustomerId(customerId);
    return joinLists.stream().map(joinList -> {
      InsJoinListDTO dto = new InsJoinListDTO();
      dto.setContractId(joinList.getContractId());
      dto.setCustomerId(joinList.getCustomerId());
      dto.setInsuranceId(joinList.getInsuranceId());
      dto.setSpecialContractStatus(joinList.getSpecialContractStatus());
      dto.setSubscriptionDate(joinList.getSubscriptionDate());
      dto.setEndDate(joinList.getEndDate());
      dto.setTotalFee(joinList.getTotalFee());
      dto.setCurrentFee(joinList.getCurrentFee());
      dto.setInsuranceStatus(joinList.getInsuranceStatus());
      dto.setInsuranceName(joinList.getInsuranceName());
      return dto;
    }).collect(Collectors.toList());
  }

  public void joinInsurance(String customerId, String insuranceId) {
    InsProduct product = insProductRepository.findByInsuranceId(insuranceId);
    if (product != null) {
      InsJoinList joinList = new InsJoinList();
      joinList.setContractId(generateContractId());
      joinList.setCustomerId(customerId);
      joinList.setInsuranceId(insuranceId);
      joinList.setInsuranceName(product.getInsuranceName());
      joinList.setTotalFee(product.getTotalFee());
      joinList.setSpecialContractStatus("정상");
      joinList.setSubscriptionDate(new Timestamp(System.currentTimeMillis()));
      joinList.setEndDate(
          new Timestamp(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000)); // 1년 후
      joinList.setCurrentFee(0); // 예시 금액으로 계산
      joinList.setInsuranceStatus("활성");
      insJoinListRepository.save(joinList);
    }
  }
  private String generateContractId() {
    // 계약 ID를 생성하는 로직을 구현합니다.
    return "C" + System.currentTimeMillis();
  }
}

