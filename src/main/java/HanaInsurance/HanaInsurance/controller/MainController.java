package HanaInsurance.HanaInsurance.controller;

import HanaInsurance.HanaInsurance.dto.InsJoinListDTO;
import HanaInsurance.HanaInsurance.entity.InsAccount;
import HanaInsurance.HanaInsurance.entity.InsCustomer;
import HanaInsurance.HanaInsurance.repository.InsAccountRepository;
import HanaInsurance.HanaInsurance.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
  @Autowired
  private MainService mainService;

  @Autowired
  private InsAccountRepository insAccountRepository;

  @GetMapping("/login")
  public String loginForm() {
    return "login";
  }

  @GetMapping("/join")
  public String joinForm() {
    return "join";
  }

  @GetMapping("/product-list")
  public String productlistForm() {
    return "product-list";
  }

  @PostMapping("/login")
  public String login(@RequestParam String customerId, @RequestParam String password, HttpSession session) {
    InsCustomer customer = mainService.authenticate(customerId, password);
    if (customer != null) {
      session.setAttribute("customer", customer); // 전체 사용자 정보를 세션에 저장
      return "redirect:/join-lists";
    } else {
      return "login";
    }
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }

  @GetMapping("/join-lists")
  public String getJoinList(Model model, HttpSession session) {
    InsCustomer customer = (InsCustomer) session.getAttribute("customer");
    if (customer != null) {
      List<InsJoinListDTO> joinListDTOs = mainService.getJoinListByCustomerId(customer.getCustomerId());
      model.addAttribute("joinLists", joinListDTOs);
      return "join-lists";
    } else {
      return "redirect:/login";
    }
  }

  @GetMapping("/product-detail")
  public String productDetail(@RequestParam String insuranceId, Model model) {
    model.addAttribute("insuranceId", insuranceId);
    return "product-detail";
  }

  @PostMapping("/terms-confirm")
  public String termsConfirm(@RequestParam String insuranceId, HttpSession session, Model model) {
    session.setAttribute("insuranceId", insuranceId);
    model.addAttribute("insuranceId", insuranceId);
    return "insurance-confirm";
  }

  @GetMapping("/insurance-confirm")
  public String insuranceConfirm(HttpSession session, Model model) {
    String insuranceId = (String) session.getAttribute("insuranceId");
    if (insuranceId != null) {
      model.addAttribute("insuranceId", insuranceId);
      return "insurance-confirm";
    } else {
      return "redirect:/login";
    }
  }

  @PostMapping("/join-insurance")
  public String joinInsurance(@RequestParam String insuranceId, HttpSession session) {
    InsCustomer customer = (InsCustomer) session.getAttribute("customer");
    if (customer != null) {
      mainService.joinInsurance(customer.getCustomerId(), insuranceId);
      return "redirect:/join-lists";
    } else {
      return "redirect:/login";
    }
  }

  @GetMapping("/change-account")
  public String changeAccount() {
    return "change_account"; // Thymeleaf는 .html 확장자를 자동으로 찾습니다.
  }


  @GetMapping("/current/{customerId}")
  public ResponseEntity<?> getCurrentAccount(@PathVariable String customerId) {
    List<InsAccount> accounts = insAccountRepository.findByCustomerId(customerId);
    if (accounts.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts found for customer id: " + customerId);
    }
    return ResponseEntity.ok(accounts);
  }
}
