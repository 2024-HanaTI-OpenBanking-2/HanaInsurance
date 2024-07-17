package HanaInsurance.HanaInsurance.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombinedAccountInfo {

    private List<AccountInfoResponseDTO> bankAccounts;
    private List<SecuritiesAccountInfoDto> stockAccounts;
}
