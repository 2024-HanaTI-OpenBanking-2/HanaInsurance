package HanaInsurance.HanaInsurance.controller;

import HanaInsurance.HanaInsurance.dto.InsJoinListDTO;
import HanaInsurance.HanaInsurance.service.JoinListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/joinlists")
public class InsJoinListController {
  @Autowired
  private JoinListService joinListService;

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<List<InsJoinListDTO>> getJoinListByCustomerId(@PathVariable String customerId) {
    List<InsJoinListDTO> joinListDTOs = joinListService.getJoinListByCustomerId(customerId);
    if (joinListDTOs != null && !joinListDTOs.isEmpty()) {
      return ResponseEntity.ok(joinListDTOs);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
