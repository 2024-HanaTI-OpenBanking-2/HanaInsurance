package HanaInsurance.HanaInsurance.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;


@Entity
@Data
@Table(name = "ins_join_lists")
public class InsJoinList {
  @Id
  @Column(name = "contract_id", length = 255, nullable = false)
  private String contractId;

  @Column(name = "customer_id", length = 255, nullable = false)
  private String customerId;

  @Column(name = "insurance_id", length = 255, nullable = false)
  private String insuranceId;

  @Column(name = "special_contract_status", length = 255)
  private String specialContractStatus;

  @Column(name = "subscription_date")
  private Timestamp subscriptionDate;

  @Column(name = "end_date")
  private Timestamp endDate;

  @Column(name = "total_fee")
  private Integer totalFee;

  @Column(name = "current_fee")
  private Integer currentFee;

  @Column(name = "insurance_status", length = 255)
  private String insuranceStatus;

  // Getters and Setters
}