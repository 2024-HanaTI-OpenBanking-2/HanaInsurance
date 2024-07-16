package HanaInsurance.HanaInsurance.controller;

import HanaInsurance.HanaInsurance.dto.InsJoinListDTO;
import HanaInsurance.HanaInsurance.entity.InsCustomer;
import HanaInsurance.HanaInsurance.service.InsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class InsController {
  @Autowired
  private InsService insService;

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
    InsCustomer customer = insService.authenticate(customerId, password);
    if (customer != null) {
      session.setAttribute("customerId", customer.getCustomerId());
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
    String customerId = (String) session.getAttribute("customerId");
    if (customerId != null) {
      List<InsJoinListDTO> joinListDTOs = insService.getJoinListByCustomerId(customerId);
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
    String customerId = (String) session.getAttribute("customerId");
    if (customerId != null) {
      insService.joinInsurance(customerId, insuranceId);
      return "redirect:/join-lists";
    } else {
      return "redirect:/login";
    }
  }
}
