package currencybot.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import currencybot.dto.settings.SettingsDto;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class CurrencyBotController extends TelegramLongPollingBot {
    public static List<SettingsDto> settingsDtoList = settingsFromJson();
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
    public static synchronized void settingsToJson() {
        try (Writer fileWriter = new FileWriter(JSON_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(settingsDtoList, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<SettingsDto> settingsFromJson() {
        List<SettingsDto> tempList = new ArrayList<>();
        try (Reader fileReader = new FileReader(JSON_FILE)) {
            Type type = TypeToken.getParameterized(List.class, SettingsDto.class).getType();
            tempList = new Gson().fromJson(fileReader, type);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(tempList == null) {
            tempList = new ArrayList<>();
        }
        return tempList;
    }
}
