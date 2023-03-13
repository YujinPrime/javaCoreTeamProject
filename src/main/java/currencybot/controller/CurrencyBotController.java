package currencybot.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ResourceBundle;


public class CurrencyBotController extends TelegramLongPollingBot {
    private static final String USERNAME = "username";
    private static final String TOKEN = "token";
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("telegramBot");

    @Override
    public String getBotUsername() {
        return resourceBundle.getString(USERNAME);
    }

    @Override
    public String getBotToken() {
        return resourceBundle.getString(TOKEN);
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
