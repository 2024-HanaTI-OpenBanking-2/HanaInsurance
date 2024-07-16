package HanaInsurance.HanaInsurance.service;

import HanaInsurance.HanaInsurance.dto.InsJoinListDTO;
import HanaInsurance.HanaInsurance.entity.InsJoinList;
import HanaInsurance.HanaInsurance.repository.InsJoinListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JoinListService {
  @Autowired
  private InsJoinListRepository insJoinListRepository;

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
      return dto;
    }).collect(Collectors.toList());
  }
}
