package com.example.demo_sd.responses;

import com.example.demo_sd.dto.GlobalQuoteDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.StringReader;

public class GlobalQuoteResponse {
    private GlobalQuoteDto globalQuote;

    // No-argument constructor
    public GlobalQuoteResponse() {
    }

    // Getter
    public GlobalQuoteDto getGlobalQuote() {
        return globalQuote;
    }

    // Setter
    public void setGlobalQuote(GlobalQuoteDto globalQuote) {
        this.globalQuote = globalQuote;
    }

    // Method to map JSON response to this class
    public static GlobalQuoteResponse fromJson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, GlobalQuoteResponse.class);
    }

    // Method to map CSV response to this class
    public static GlobalQuoteResponse fromCsv(String csv) throws Exception {
        StringReader reader = new StringReader(csv);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);

        GlobalQuoteResponse response = new GlobalQuoteResponse();
        GlobalQuoteDto quote = new GlobalQuoteDto();

        for (CSVRecord record : records) {
            quote.setSymbol(record.get("symbol"));
            quote.setOpen(record.get("open"));
            quote.setHigh(record.get("high"));
            quote.setLow(record.get("low"));
            quote.setPrice(record.get("price"));
            quote.setVolume(record.get("volume"));
            quote.setLatestTradingDay(record.get("latestTradingDay"));
            quote.setPreviousClose(record.get("previousClose"));
            quote.setChange(record.get("change"));
            quote.setChangePercent(record.get("changePercent"));
        }

        response.setGlobalQuote(quote);
        return response;
    }
}
