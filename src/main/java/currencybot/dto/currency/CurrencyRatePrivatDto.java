package currencybot.dto.currency;

import currencybot.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyRatePrivatDto {
    private Currency ccy;
    private Currency base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
