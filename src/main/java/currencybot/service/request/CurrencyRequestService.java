package currencybot.service.request;

import currencybot.dto.currency.CurrencyRateDto;

import java.util.List;

public interface CurrencyRequestService {
    List<CurrencyRateDto> getCurrencyRates();
}
