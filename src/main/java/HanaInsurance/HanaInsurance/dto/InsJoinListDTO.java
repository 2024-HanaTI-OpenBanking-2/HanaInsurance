package HanaInsurance.HanaInsurance.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class InsJoinListDTO {
  private String contractId;
  private String customerId;
  private String insuranceId;
  private String specialContractStatus;
  private Timestamp subscriptionDate;
  private Timestamp endDate;
  private Integer totalFee;
  private Integer currentFee;
  private String insuranceStatus;
  private String insuranceName;

}
