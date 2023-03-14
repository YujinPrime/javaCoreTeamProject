package currencybot.service.request;

import currencybot.dto.settings.SettingsDto;
import currencybot.enums.Currency;

public class CommandResolverService {

    private void changeUserCurrencySetting(SettingsDto userSettingsDto, Currency currency) {
        if (userSettingsDto.getCurrency().contains(currency) && userSettingsDto.getCurrency().size() > 1) {
            userSettingsDto.getCurrency().remove(currency);
        } else {
            userSettingsDto.getCurrency().add(currency);
        }
    }
}
