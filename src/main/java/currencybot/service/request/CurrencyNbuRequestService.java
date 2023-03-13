package currencybot.service.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import currencybot.dto.currency.CurrencyRateDto;
import currencybot.dto.currency.CurrencyRateNbuDto;
import currencybot.enums.BankName;
import currencybot.enums.Currency;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyNbuRequestService implements CurrencyRequestService {
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    public List<CurrencyRateDto> getCurrencyRates() {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<CurrencyRateNbuDto> responseDtos = convertResponseToList(response);
            return responseDtos.stream()
                    .map(dto -> new CurrencyRateDto(dto.getCc(), dto.getRate(), dto.getRate(), BankName.NBU))
                    .filter(dto -> dto.getCurrency() == Currency.USD || dto.getCurrency() == Currency.EUR)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CurrencyRateNbuDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, CurrencyRateNbuDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }

}
