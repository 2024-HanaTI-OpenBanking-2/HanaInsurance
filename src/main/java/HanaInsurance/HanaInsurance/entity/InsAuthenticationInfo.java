package HanaInsurance.HanaInsurance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ins_authentication_infos")
public class InsAuthenticationInfo {
  @Id
  @Column(name = "client_id", length = 100, nullable = false)
  private String clientId;

  @Column(name = "client_secret", length = 100)
  private String clientSecret;

  @Column(name = "client_name", length = 100)
  private String clientName;

  // Getters and Setters
}