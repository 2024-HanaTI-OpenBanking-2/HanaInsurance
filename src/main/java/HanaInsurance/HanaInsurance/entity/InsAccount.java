package HanaInsurance.HanaInsurance.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;

@Entity
@Data
@Table(name = "ins_accounts")
public class InsAccount {
  @Id
  @Column(name = "account_no", length = 255, nullable = false)
  private String accountNo;

  @Column(name = "customer_id", length = 255, nullable = false)
  private String customerId;

  @Column(name = "account_name", length = 255)
  private String accountName;

  @Column(name = "customer_password", length = 255)
  private String customerPassword;

  @Column(name = "balance", length = 255)
  private String balance;

  @Column(name = "create_date", length = 255)
  private String createDate;

  @Column(name = "account_status")
  private Date accountStatus;

  @Column(name = "bank_code", length = 255)
  private String bankCode;

  // Getters and Setters
}