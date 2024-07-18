package HanaInsurance.HanaInsurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ins_products")
public class InsProduct {
  @Id
  @Column(name = "insurance_id", length = 100, nullable = false)
  private String insuranceId;

  @Column(name = "insurance_name", length = 1000, nullable = false)
  private String insuranceName;

  @Column(name = "total_fee", nullable = false)
  private Integer totalFee;

  @Column(name = "insurance_type", length = 100, nullable = false)
  private String insuranceType;

  // Getters and Setters
}