package currencybot.service.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import currencybot.dto.currency.CurrencyRateDto;
import currencybot.dto.currency.CurrencyRateMonoDto;
import currencybot.enums.BankName;
import currencybot.enums.Currency;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static currencybot.enums.Currency.*;

public class CurrencyMonoRequestService implements CurrencyRequestService {
    private static final String URL = "https://api.monobank.ua/bank/currency";
    private static final Map<Integer, Currency> codeCurr = Map.of(
            980, UAH,
            840, USD,
            978, EUR
    );

    @Override
    public List<CurrencyRateDto> getCurrencyRates() {
        try {
            Connection.Response response = Jsoup.connect(URL).ignoreContentType(true).execute();
            if (response.statusCode() == 200) {
                List<CurrencyRateMonoDto> currencyRateResponses = convertResponseToList(response.body());
                return currencyRateResponses.stream()
                        .filter(item -> codeCurr.containsKey(item.getCurrencyCodeA())
                                && codeCurr.containsKey(item.getCurrencyCodeB())
                                && item.getCurrencyCodeB().equals(980)
                        )
                        .map(item -> new CurrencyRateDto(
                                codeCurr.get(item.getCurrencyCodeA()),
                                item.getRateBuy(),
                                item.getRateSell(),
                                BankName.MONOBANK
                        ))
                        .collect(Collectors.toList());
            }
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CurrencyRateMonoDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, CurrencyRateMonoDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
