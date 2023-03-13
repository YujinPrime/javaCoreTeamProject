package currencybot.service.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import currencybot.dto.currency.CurrencyRateDto;
import currencybot.dto.currency.CurrencyRatePrivatDto;
import currencybot.enums.BankName;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyPrivatRequestService implements CurrencyRequestService {

    private static final String URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";
    @Override
    public List<CurrencyRateDto> getCurrencyRates() {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<CurrencyRatePrivatDto> responseDtos = convertResponseToList(response);
            return responseDtos.stream()
                    .map(dto -> new CurrencyRateDto(dto.getCcy(), dto.getBuy(), dto.getSale(), BankName.PRIVATBANK))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CurrencyRatePrivatDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, CurrencyRatePrivatDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
