package currencybot.service.request;

import currencybot.dto.settings.UserSettingDto;
import currencybot.enums.Currency;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class CommandResolverService {
    private static final MenuCreationService menuCreationService = new MenuCreationService();
    private static final String DECIMAL_CALLBACK_MARKER = "dec_";
    private static final String DECIMAL_TWO_CALLBACK = "set_dec_decimalCountIsTwo";
    private static final String DECIMAL_THREE_CALLBACK = "set_dec_decimalCountIsThree";
    private static final String DECIMAL_FOUR_CALLBACK = "set_dec_decimalCountIsFour";
    private static final String DECIMAL_COUNT_CALLBACK = "set_decimalCount";
    public EditMessageText resolveSettingsMenuCommands(String callBack, long chatId, long messageId) {
        EditMessageText newMessage = null;
        UserSettingDto userSettingsDto = getUserSettingsDto(chatId);
        if (callBack.contains(DECIMAL_CALLBACK_MARKER)) {
            switch (callBack) {
                case DECIMAL_TWO_CALLBACK: {
                    userSettingsDto.setDecimalCount(2);
                    newMessage = menuCreationService.getDecimalCountMenu(chatId, messageId);
                } break;
                case DECIMAL_THREE_CALLBACK: {
                    userSettingsDto.setDecimalCount(3);
                    newMessage = menuCreationService.getDecimalCountMenu(chatId, messageId);
                } break;
                case DECIMAL_FOUR_CALLBACK: {
                    userSettingsDto.setDecimalCount(4);
                    newMessage = menuCreationService.getDecimalCountMenu(chatId, messageId);
                }break;
            }
            CurrencyRateBotController.settingsToJson();
        } else if (callBack.contains(BANK_CALLBACK_MARKER)) {
            switch (callBack) {
                case NBU_CALLBACK: {
                    userSettingsDto.setBank(BankName.NBU);
                    newMessage = menuCreationService.getBankMenu(chatId, messageId);
                } break;
                case PRIVAT_CALLBACK: {
                    userSettingsDto.setBank(BankName.PRIVATBANK);
                    newMessage = menuCreationService.getBankMenu(chatId, messageId);
                } break;
                case MONOBANK_CALLBACK: {
                    userSettingsDto.setBank(BankName.MONOBANK);
                    newMessage = menuCreationService.getBankMenu(chatId, messageId);
                }break;
            }
            CurrencyRateBotController.settingsToJson();
        }
        return newMessage;
    }
    private void changeUserCurrencySetting(UserSettingDto userSettingsDto, Currency currency) {
        if (userSettingsDto.getCurrency().contains(currency) && userSettingsDto.getCurrency().size() > 1) {
            userSettingsDto.getCurrency().remove(currency);
        } else {
            userSettingsDto.getCurrency().add(currency);
        }
    }
}
