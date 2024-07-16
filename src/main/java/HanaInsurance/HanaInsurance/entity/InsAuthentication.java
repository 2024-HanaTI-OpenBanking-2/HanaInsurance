package HanaInsurance.HanaInsurance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;

@Entity
@Data
@Table(name = "ins_authentications")
public class InsAuthentication {

  @Id
  @Column(name = "access_token_id", length = 255, nullable = false)
  private String accessTokenId;

  @Column(name = "expires_in")
  private Integer expiresIn;

  @Column(name = "refresh_token", length = 255)
  private String refreshToken;

  @Column(name = "auth_scope", length = 255)
  private String authScope;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "client_use_code", length = 255)
  private String clientUseCode;

  // Getters and Setters
}
