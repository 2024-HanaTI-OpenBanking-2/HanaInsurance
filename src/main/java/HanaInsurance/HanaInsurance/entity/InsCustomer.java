package HanaInsurance.HanaInsurance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ins_customers")
public class InsCustomer {
  @Id
  @Column(name = "customer_id", length = 255, nullable = false)
  private String customerId;

  @Column(name = "name", length = 255, nullable = false)
  private String name;

  @Column(name = "ssn", length = 255, nullable = false)
  private String ssn;

  @Column(name = "gender1", length = 255)
  private String gender1;

  @Column(name = "customer_password", length = 255)
  private String customerPassword;

  @Column(name = "contact_info", length = 255)
  private String contactInfo;

  @Column(name = "email", length = 255)
  private String email;

  @Column(name = "ci", length = 200)
  private String ci;

  // Getters and Setters
}