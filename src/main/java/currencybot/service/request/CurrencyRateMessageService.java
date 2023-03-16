package currencybot.service.request;

import currencyratebot.dto.currency.CurrencyRateDto;
import currencyratebot.dto.settings.UserSettingDto;
import currencyratebot.service.request.CurrencyRateRequestService;
import currencyratebot.service.request.MonoCurrencyRateRequestService;
import currencyratebot.service.request.NbuCurrencyRateRequestService;
import currencyratebot.service.request.PrivatCurrencyRateRequestService;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static currencyratebot.service.request.DailyCurrencyRateRequestService.currencyRateDtos;

public class CurrencyRateMessageService {
    private static final String RESPONSE_BANK = "{0}";
    private static final String RESPONSE_RATE = "\n\nКурс купівлі {0}: %.{1}f UAH\nКурс продажу {0}: %.{1}f UAH";
    private static final String ERROR_MESSAGE = "В даний момент інформація не доступна.\nСпробуйте пізніше.";
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
