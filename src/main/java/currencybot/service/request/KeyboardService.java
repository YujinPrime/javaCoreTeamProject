package currencybot.service.request;

import com.vdurmont.emoji.EmojiParser;
import currencybot.controller.CurrencyBotController;
import currencybot.dto.settings.SettingsDto;
import currencybot.enums.Currency;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class KeyboardService {

    private static final String GET_INFO_BUTTON = "Отримати інфо";
    private static final String GET_INFO_CALLBACK = "getInfo";
    private static final String SETTINGS_BUTTON = "Налаштування";
    private static final String SETTINGS_CALLBACK = "settings";
    private static final String DECIMAL_COUNT_BUTTON = "Кількість знаків після коми (";
    private static final String DECIMAL_COUNT_CALLBACK = "set_decimalCount";
    private static final String BANK_BUTTON = "Банк (";
    private static final String BANK_CALLBACK = "set_bankName";
    private static final String CURRENCY_BUTTON = "Валюти ";
    private static final String CURRENCY_CALLBACK = "set_currency";
    private static final String NOTIFICATION_TIME_BUTTON = "Час оповіщень (";
    private static final String NOTIFICATION_TIME_CALLBACK = "notificationTime";
    private static final String HOME_CALLBACK = "home";
    private static final String HOME_EMOJI_BUTTON = ":house:";

    private static final String RIGHT_ROUND_BRACKET = ")";
    private static final String LEFT_ROUND_BRACKET = "(";
    private static final String LEFT_SQUARE_BRACKET = "[";
    private static final String RIGHT_SQUARE_BRACKET = "]";

    private static final String USD_OPTION = "USD ";
    private static final String USD_CALLBACK = "set_cur_usd";
    private static final String EUR_OPTION = "EUR ";
    private static final String EUR_CALLBACK = "set_cur_eur";

    private static final String CHECK_MARK_EMOJI = ":white_check_mark:";
    private static final String EMPTY_STRING = "";

    private static final String BACK_EMOJI_BUTTON = ":arrow_left:";
    private static final String BACK_CALLBACK = "set_back";

    public InlineKeyboardMarkup getMainKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton(GET_INFO_BUTTON, GET_INFO_CALLBACK));
        rowsInline.add(createButton(SETTINGS_BUTTON, SETTINGS_CALLBACK));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getSettingsKeyboard(long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton(DECIMAL_COUNT_BUTTON + getUserDecimalSetting(chatId) + RIGHT_ROUND_BRACKET, DECIMAL_COUNT_CALLBACK));
        rowsInline.add(createButton(BANK_BUTTON + getUserBankSetting(chatId).name + RIGHT_ROUND_BRACKET, BANK_CALLBACK));
        rowsInline.add(createButton(CURRENCY_BUTTON + getUserCurrencySetting(chatId)
                .toString()
                .replace(LEFT_SQUARE_BRACKET, LEFT_ROUND_BRACKET)
                .replace(RIGHT_SQUARE_BRACKET, RIGHT_ROUND_BRACKET), CURRENCY_CALLBACK));
        rowsInline.add(createButton(NOTIFICATION_TIME_BUTTON + getUserNotificationSetting(chatId) + RIGHT_ROUND_BRACKET, NOTIFICATION_TIME_CALLBACK));
        rowsInline.add(createButton(EmojiParser.parseToUnicode(HOME_EMOJI_BUTTON), HOME_CALLBACK));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getCurrencyKeyboard(long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton(USD_OPTION + checkMarkForCurrency(chatId, Currency.USD), USD_CALLBACK));
        rowsInline.add(createButton(EUR_OPTION + checkMarkForCurrency(chatId, Currency.EUR), EUR_CALLBACK));
        rowsInline.add(createBackHomeButtons());
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private String checkMarkForCurrency(long chatId, Currency currency) {
        List<Currency> userCurrencySetting = getUserCurrencySetting(chatId);
        return userCurrencySetting.contains(currency)
                ? EmojiParser.parseToUnicode(CHECK_MARK_EMOJI)
                : EMPTY_STRING;
    }

    private List<Currency> getUserCurrencySetting(long chatId) {
        return CurrencyBotController.settingsDtoList.stream()
                .filter(userSettings -> userSettings.getChatId() == chatId)
                .map(SettingsDto::getCurrency)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
    private List<InlineKeyboardButton> createButton(String command, String callBack) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(command)
                .callbackData(callBack)
                .build();
        rowInline.add(button);
        return rowInline;
    }

    private List<InlineKeyboardButton> createBackHomeButtons() {
        String markedOptionArrow = EmojiParser.parseToUnicode(BACK_EMOJI_BUTTON);
        String markedOptionHouse = EmojiParser.parseToUnicode(HOME_EMOJI_BUTTON);
        List<InlineKeyboardButton> backButton = createButton(markedOptionArrow, BACK_CALLBACK);
        List<InlineKeyboardButton> homeButton = createButton(markedOptionHouse, HOME_CALLBACK);
        backButton.addAll(homeButton);
        return backButton;
    }
}
