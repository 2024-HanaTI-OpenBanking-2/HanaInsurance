package HanaInsurance.HanaInsurance.service;

import HanaInsurance.HanaInsurance.dto.AccountCiResponseDTO;
import HanaInsurance.HanaInsurance.dto.AccountInfoInsDTO;
import HanaInsurance.HanaInsurance.dto.CombinedAccountInfo;
import HanaInsurance.HanaInsurance.dto.InsuranceResponseDTO;
import HanaInsurance.HanaInsurance.entity.InsCustomer;
import HanaInsurance.HanaInsurance.entity.InsJoinList;
import HanaInsurance.HanaInsurance.entity.InsProduct;
import HanaInsurance.HanaInsurance.repository.InsCustomerRepository;
import HanaInsurance.HanaInsurance.repository.InsJoinListRepository;
import HanaInsurance.HanaInsurance.repository.InsProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

@Service
public class InsuranceService {

  private final RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private InsCustomerRepository insCustomerRepository;

  @Autowired
  private InsJoinListRepository insJoinListRepository;

  @Autowired
  private InsProductRepository insProductRepository;


  @Value("${openbank.server.url}")
  private String openbankServerUrl;

  public List<InsuranceResponseDTO> getInsuranceList(AccountCiResponseDTO accountCiResponseDTO) {
    InsCustomer customer = insCustomerRepository.findByCi(accountCiResponseDTO.getCi());

    if (customer == null) {
      throw new IllegalArgumentException("Invalid CI");
    }

    List<InsJoinList> joinLists = insJoinListRepository.findByCustomerId(customer.getCustomerId());

    return joinLists.stream().map(joinList -> {
      InsProduct product = insProductRepository.findById(joinList.getInsuranceId()).orElse(null);
      if (product == null) {
        throw new IllegalArgumentException("Invalid insuranceId: " + joinList.getInsuranceId());
      }
      return new InsuranceResponseDTO(
          joinList.getInsuranceId(),
          product.getInsuranceName(),
          product.getInsuranceType(),
          joinList.getTotalFee().toString(),
          joinList.getInsuranceStatus(),
          joinList.getSubscriptionDate(),
          joinList.getEndDate()
      );
    }).collect(Collectors.toList());
  }

  public CombinedAccountInfo getCombinedAccountInfo(AccountInfoInsDTO accountInfoInsDTO) {
    String url = openbankServerUrl + "/api/insurance/accountinfo/list";
    System.out.println("getAccountInfoList :" + url);
    System.out.println(accountInfoInsDTO.getCi());
    CombinedAccountInfo response = restTemplate.postForObject(url, accountInfoInsDTO, CombinedAccountInfo.class);
    System.out.println(response);
    return response;
  }
}
