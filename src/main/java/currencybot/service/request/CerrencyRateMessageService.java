package currencybot.service.request;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CerrencyRateMessageService {

    private final List<CurrencyRateRequestService> requestServices = List.of(
            new MonoCurrencyRateRequestService(),
            new PrivatCurrencyRateRequestService(),
            new NbuCurrencyRateRequestService()
    );

    private List<CurrencyRateDto> getCurrencyRatesBySettings(UserSettingDto userSettingsDto, boolean isNewRequestRequired) {
        List<CurrencyRateDto> currencyRates;
        if (isNewRequestRequired) {
            currencyRates = getActualRates();
            currencyRateDtos.addAll(currencyRates.stream()
                    .filter(currencyRateDto -> userSettingsDto.getBank() == currencyRateDto.getBankName())
                    .collect(Collectors.toList()));
        } else {
            currencyRates = currencyRateDtos;
        }
        return currencyRates.stream()
                .filter(currencyRateDto -> userSettingsDto.getBank() == currencyRateDto.getBankName())
                .filter(currencyRateDto -> userSettingsDto.getCurrency().contains(currencyRateDto.getCurrency()))
                .collect(Collectors.toList());
    }

    public List<CurrencyRateDto> getActualRates() {
        return requestServices.stream()
                .map(CurrencyRateRequestService::getCurrencyRates)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
