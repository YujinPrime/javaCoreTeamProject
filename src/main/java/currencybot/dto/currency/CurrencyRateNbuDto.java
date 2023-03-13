package currencybot.dto.currency;

import currencybot.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyRateNbuDto {
    private Currency cc;
    private BigDecimal rate;

}
