package currencybot.dto.settings;

import currencybot.enums.BankName;
import currencybot.enums.Currency;

import java.util.ArrayList;
import java.util.List;

public class UserSettingDto {
    private long chatId;
    private int decimalCount;
    private List<Currency> currency;
    private BankName bank;
    private String notificationTime;

    public UserSettingDto(long chatId) {
        this.chatId = chatId;
        this.decimalCount = 2;
        currency = new ArrayList<>(){
            {
                add(Currency.USD);
            }
        };
        bank = BankName.PRIVATBANK;
        notificationTime = "OFF";
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getDecimalCount() {
        return decimalCount;
    }

    public void setDecimalCount(int decimalCount) {
        this.decimalCount = decimalCount;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }

    public BankName getBank() {
        return bank;
    }

    public void setBank(BankName bank) {
        this.bank = bank;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        if(notificationTime.equalsIgnoreCase("Вимкнути сповіщення")) {
            this.notificationTime = "OFF";
        } else {
            this.notificationTime = notificationTime.replace(":00", "");
        }
    }
}