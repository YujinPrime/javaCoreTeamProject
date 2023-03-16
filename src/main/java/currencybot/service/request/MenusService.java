package currencybot.service.request;

import currencybot.dto.settings.UserSettingDto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import static java.lang.Math.toIntExact;

public class MenusService {

    private static final RateResponseService rateResponseService = new RateResponseService();
    private static final KeyboardService keyboardService = new KeyboardService();
    private static final String WELCOME_MESSAGE = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";
    private static final String SETTINGS_MESSAGE = "Налаштування";
    private static final String BANKNAME_MESSAGE = "Банк";
    private static final String MAIN_MENU_MESSAGE = "Оберіть одну з наданих опцій";
    private static final String NOTIFICATION_TIME_MESSAGE = "Виберіть час оповіщень";
    private static final String CURRENCY_MESSAGE = "Валюти";

    public SendMessage getStartMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(WELCOME_MESSAGE)
                .replyMarkup(keyboardService.getMainKeyboard())
                .build();
    }

    public SendMessage getMainMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(MAIN_MENU_MESSAGE)
                .replyMarkup(keyboardService.getMainKeyboard())
                .build();
    }

    public SendMessage getRateMenu(long chatId, UserSettingDto userSettingsDto) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(rateResponseService.getRateResponse(userSettingsDto))
                .replyMarkup(keyboardService.getMainKeyboard())
                .build();
    }

    public SendMessage getAutoRateMenu(long chatId, UserSettingDto userSettingsDto) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(rateResponseService.getRateResponse(userSettingsDto))
                .replyMarkup(keyboardService.getHomeKeyboard())
                .build();
    }

    public SendMessage getSettingsMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(SETTINGS_MESSAGE)
                .replyMarkup(keyboardService.getSettingsKeyboard(chatId))
                .build();
    }


    public EditMessageText getCurrencyMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text(CURRENCY_MESSAGE)
                .replyMarkup(keyboardService.getCurrencyKeyboard(chatId))
                .build();
    }
    public EditMessageText getBankMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text(BANKNAME_MESSAGE)
                .replyMarkup(keyboardService.getBankNameKeyboard(chatId))
                .build();
    }

    public SendMessage getNotificationMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(NOTIFICATION_TIME_MESSAGE)
                .replyMarkup(keyboardService.getNotificationKeyboard())
                .build();
    }


}
