package com.example.demo_sd.services;

import com.example.demo_sd.dto.BestMatchDto;
import com.example.demo_sd.dto.GlobalQuoteDto;
import com.example.demo_sd.responses.GlobalQuoteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

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
    //TYPE : TIME_SERIES_WEEKLY, TIME_SERIES_DAILY, TIME_SERIES_MONTHLY

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


    public GlobalQuoteResponse getStockQuote(String function, String symbol, String datatype) {
       /* String url = "https://api.example.com/quote?function=" + function +
                "&symbol=" + symbol +
                "&datatype=" + datatype +
                "&apikey=YOUR_API_KEY"; // Replace with your API key

        ResponseEntity<com.example.demo.dto.GlobalQuoteResponse> responseEntity = restTemplate.getForEntity(url, ApiResponseDTO.class);
        ApiResponseDTO apiResponse = responseEntity.getBody();

        return apiResponse != null ? apiResponse.getGlobalQuote() : null;*/
        return null;
    }
}