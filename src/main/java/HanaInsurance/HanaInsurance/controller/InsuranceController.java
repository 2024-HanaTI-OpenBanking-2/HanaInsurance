package HanaInsurance.HanaInsurance.controller;

import HanaInsurance.HanaInsurance.dto.AccountCiResponseDTO;
import HanaInsurance.HanaInsurance.dto.AccountInfoInsDTO;
import HanaInsurance.HanaInsurance.dto.CombinedAccountInfo;
import HanaInsurance.HanaInsurance.dto.InsuranceResponseDTO;
import HanaInsurance.HanaInsurance.entity.InsAccount;
import HanaInsurance.HanaInsurance.entity.InsCustomer;
import HanaInsurance.HanaInsurance.repository.InsAccountRepository;
import HanaInsurance.HanaInsurance.repository.InsCustomerRepository;
import HanaInsurance.HanaInsurance.service.InsuranceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

  @Autowired
  private InsuranceService insuranceService;

  @Autowired
  private InsCustomerRepository insCustomerRepository;

  @Autowired
  private InsAccountRepository insAccountRepository;


  @GetMapping("/accountinfo")
  public ResponseEntity<CombinedAccountInfo> getAccountInfo(HttpServletRequest request,
      Model model) {
    // 사용자 ID 추출 로직
    String customerId = getUserId(request, model);

    // customerId로 CI 불러오기
    InsCustomer customer = insCustomerRepository.findByCustomerId(customerId);

    if (customer == null) {
      return ResponseEntity.notFound().build();
    }
    String ci = customer.getCi();

    // ci 요청에 담아서 날리기
    AccountInfoInsDTO accountInfoInsDTO = new AccountInfoInsDTO(ci); // DTO 생성
    CombinedAccountInfo combinedAccountInfo = insuranceService.getCombinedAccountInfo(
        accountInfoInsDTO); // 서비스 호출
    return ResponseEntity.ok(combinedAccountInfo); // 결합된 계좌 정보 응답
  }

  public String getUserId(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false); // 기존 세션 가져오기, 없으면 null 반환
    if (session != null) {
      InsCustomer customer = (InsCustomer) session.getAttribute("customer");
      if (customer != null) {
        model.addAttribute("customerId", customer.getCustomerId());
        return customer.getCustomerId();
      } else {
        return null; // 세션에 사용자 정보가 없으면 로그인 페이지로 리다이렉트
      }
    } else {
      return null; // 세션 자체가 없으면 로그인 페이지로 리다이렉트
    }
  }


  @PostMapping("/list")
  public List<InsuranceResponseDTO> getInsuranceList(
      @RequestBody AccountCiResponseDTO accountCiResponseDTO) {
    return insuranceService.getInsuranceList(accountCiResponseDTO);
  }

  @PostMapping("/saveOrUpdateAccount")
  public ResponseEntity<?> saveOrUpdateAccount(@RequestBody InsAccount account, HttpSession session) {
    try {
      System.out.println("in the saveOrUpdateAccount");
      InsCustomer customer = (InsCustomer) session.getAttribute("customer");
      if (customer == null) {
        throw new IllegalStateException("No customer is logged in");
      }

      String customerId = customer.getCustomerId();
      account.setCustomerId(customerId);
      System.out.println("Customer ID from session: " + customerId);

      Optional<InsAccount> existingAccountOpt = insAccountRepository.findByAccountNoAndCustomerId(account.getAccountNo(), customerId);
      if (existingAccountOpt.isPresent()) {
        InsAccount existingAccount = existingAccountOpt.get();
        existingAccount.setAccountName(account.getAccountName());
        existingAccount.setBankCode(account.getBankCode());
        existingAccount.setBalance(account.getBalance());
        existingAccount.setAccountNo(account.getAccountNo());
        return ResponseEntity.ok(insAccountRepository.save(existingAccount));
      } else {
        return ResponseEntity.ok(insAccountRepository.save(account));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
  }

  @GetMapping("/currentAccount")
  public ResponseEntity<?> getCurrentAccount(HttpSession session) {
    InsCustomer customer = (InsCustomer) session.getAttribute("customer");
    if (customer == null) {
      return ResponseEntity.status(401).body("No customer is logged in");
    }

    String customerId = customer.getCustomerId();
    List<InsAccount> accounts = insAccountRepository.findByCustomerId(customerId);
    return ResponseEntity.ok(accounts);
  }

}
