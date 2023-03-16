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
    private static final String BANK_CALLBACK_MARKER = "bank_";
    private static final String NBU_CALLBACK = "set_bank_nbu";
    private static final String PRIVAT_CALLBACK = "set_bank_privatBank";
    private static final String MONOBANK_CALLBACK = "set_bank_monobank";
    private static final String CURRENCY_CALLBACK_MARKER = "cur_";
    private static final String USD_CALLBACK = "set_cur_usd";
    private static final String EUR_CALLBACK = "set_cur_eur";
    private static final String BANK_NAME_CALLBACK = "set_bankName";
    private static final String CURRENCY_CALLBACK = "set_currency";
    private static final String BACK_CALLBACK = "set_back";

    public EditMessageText resolveSettingsMenuCommands(String callBack, long chatId, long messageId) {
        EditMessageText newMessage = null;
        UserSettingDto userSettingsDto = getUserSettingsDto(chatId);
        if (callBack.contains(DECIMAL_CALLBACK_MARKER)) {
            switch (callBack) {
                case DECIMAL_TWO_CALLBACK: {
                    userSettingsDto.setDecimalCount(2);
                    newMessage = menuCreationService.getDecimalCountMenu(chatId, messageId);
                }
                break;
                case DECIMAL_THREE_CALLBACK: {
                    userSettingsDto.setDecimalCount(3);
                    newMessage = menuCreationService.getDecimalCountMenu(chatId, messageId);
                }
                break;
                case DECIMAL_FOUR_CALLBACK: {
                    userSettingsDto.setDecimalCount(4);
                    newMessage = menuCreationService.getDecimalCountMenu(chatId, messageId);
                }
                break;
            }
            CurrencyRateBotController.settingsToJson();
        } else if (callBack.contains(BANK_CALLBACK_MARKER)) {
            switch (callBack) {
                case NBU_CALLBACK: {
                    userSettingsDto.setBank(BankName.NBU);
                    newMessage = menuCreationService.getBankMenu(chatId, messageId);
                }
                break;
                case PRIVAT_CALLBACK: {
                    userSettingsDto.setBank(BankName.PRIVATBANK);
                    newMessage = menuCreationService.getBankMenu(chatId, messageId);
                }
                break;
                case MONOBANK_CALLBACK: {
                    userSettingsDto.setBank(BankName.MONOBANK);
                    newMessage = menuCreationService.getBankMenu(chatId, messageId);
                }
                break;
            }
            CurrencyRateBotController.settingsToJson();
        } else if (callBack.contains(CURRENCY_CALLBACK_MARKER)) {
            switch (callBack) {
                case USD_CALLBACK: {
                    changeUserCurrencySetting(userSettingsDto, Currency.USD);
                    newMessage = menuCreationService.getCurrencyMenu(chatId, messageId);
                }
                break;
                case EUR_CALLBACK: {
                    changeUserCurrencySetting(userSettingsDto, Currency.EUR);
                    newMessage = menuCreationService.getCurrencyMenu(chatId, messageId);
                }
                break;
            }
            CurrencyRateBotController.settingsToJson();
        } else {
            switch (callBack) {
                case DECIMAL_COUNT_CALLBACK: {
                    newMessage = menuCreationService.getDecimalCountMenu(chatId, messageId);
                }
                break;
                case BANK_NAME_CALLBACK: {
                    newMessage = menuCreationService.getBankMenu(chatId, messageId);
                }
                break;
                case CURRENCY_CALLBACK: {
                    newMessage = menuCreationService.getCurrencyMenu(chatId, messageId);
                }
                break;
                case BACK_CALLBACK: {
                    newMessage = menuCreationService.getUpdatedSettingsMenu(chatId, messageId);
                }
                break;
            }
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
