package com.synpulse8.samyeung812.backendchallenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synpulse8.samyeung812.backendchallenge.exceptions.CannotGetExchangeRateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    public Double getRate(String base, String target, String date) throws CannotGetExchangeRateException {
        if(base.equals(target)) return 1.0;

        final String baseURL = "https://api.exchangerate.host";
        String url = String.format("%s/%s?base=%s&symbols=%s", baseURL, date, base, target);

        RestTemplate restTemplate = new RestTemplate();
        String jsonResult = restTemplate.getForObject(url, String.class);
        double rate = 0.0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Map> map = new HashMap<>();
            map = mapper.readValue(jsonResult, map.getClass());
            rate = Double.parseDouble(map.get("rates").get(target).toString());
        }
        catch (IOException e) {
            throw new CannotGetExchangeRateException();
        }

        return rate;
    }
}
