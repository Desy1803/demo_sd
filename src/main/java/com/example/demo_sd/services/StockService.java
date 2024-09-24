package com.example.demo_sd.services;

import com.example.demo_sd.dto.BestMatchDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class StockService {

    private static final String API_KEY = "6DAWQ5U39PHB4SG9"; // Sostituisci con la tua chiave API
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final WebClient webClient;

    public StockService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // Aumenta il limite a 10 MB
                .build())
                .build();
    }

    public String getStockData(String symbol, String outputsize, String datatype, String type) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("function", type)
                        .queryParam("symbol", symbol)
                        .queryParam("outputsize", outputsize)
                        .queryParam("datatype", datatype)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class) // Assumiamo che l'output sia in formato JSON o CSV come Stringa
                .block(); // Blocking operation per ottenere la risposta
    }


    public String getSymbolicSearch(BestMatchDto bestMatchDto){
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("function", bestMatchDto.getFunction())
                        .queryParam("keywords", bestMatchDto.getKeywords())
                        .queryParam("apikey", API_KEY)
                        .queryParam("datatype", bestMatchDto.getDatatype())
                        .build())
                .retrieve()
                .bodyToMono(String.class) // Assumiamo che l'output sia in formato JSON o CSV come Stringa
                .block(); // Blocking operation per ottenere la risposta
    }


    public String getStockQuote(String function, String symbol, String datatype) {

        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("symbol", symbol)
                        .queryParam("datatype", datatype)
                        .queryParam("apikey", API_KEY)
                        .queryParam("function", function)
                        .build())
                .retrieve()
                .bodyToMono(String.class) // Assumiamo che l'output sia in formato JSON o CSV come Stringa
                .block(); // Blocking operation per ottenere la risposta
    }
    //TIME_SERIES_MONTHLY_ADJUSTED, TIME_SERIES_DAILY_ADJUSTED (PREMIUM), TIME_SERIES_WEEKLY_ADJUSTED ->  function

    public String getGlobalMarketOpenAndCloseStatus(String function){ //function=MARKET_STATUS
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("function", function)
                        .build())
                .retrieve()
                .bodyToMono(String.class) // Assumiamo che l'output sia in formato JSON o CSV come Stringa
                .block(); // Blocking operation per ottenere la risposta
    }
    public String getFundamentalData(String function, String symbol){ //function=OVERVIEW, symbol = IBM
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("symbol", symbol)
                        .queryParam("function", function)
                        .build())
                .retrieve()
                .bodyToMono(String.class) // Assumiamo che l'output sia in formato JSON o CSV come Stringa
                .block(); // Blocking operation per ottenere la risposta
    }

    public String getDividends(String function, String symbol){ //function=OVERVIEW, symbol = IBM
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("symbol", symbol)
                        .queryParam("function", function)
                        .build())
                .retrieve()
                .bodyToMono(String.class) // Assumiamo che l'output sia in formato JSON o CSV come Stringa
                .block(); // Blocking operation per ottenere la risposta
    }

    public Mono<String> sendRequestCoreStock(String function, String symbol, String datatype, String type, String keywords, String outputsize) {
        return this.webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.queryParam("apikey", API_KEY);
                    if (Objects.nonNull(function)) {
                        uriBuilder.queryParam("function", function);
                    }
                    if (Objects.nonNull(symbol)) {
                        uriBuilder.queryParam("symbol", symbol);
                    }
                    if (Objects.nonNull(datatype)) {
                        uriBuilder.queryParam("datatype", datatype);
                    }
                    if (Objects.nonNull(type)) {
                        uriBuilder.queryParam("type", type);
                    }
                    if (Objects.nonNull(keywords)) {
                        uriBuilder.queryParam("keywords", keywords);
                    }
                    if (Objects.nonNull(outputsize)) {
                        uriBuilder.queryParam("outputsize",outputsize);
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(String.class);
    }
}