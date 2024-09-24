package com.example.demo_sd.controllers;

import com.example.demo_sd.enumerations.DataType;
import com.example.demo_sd.enumerations.TimeSeriesType;
import com.example.demo_sd.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/stocks")
public class InformationController {
    @Autowired
    private StockService stockService;

    @GetMapping("/all")
    public Mono<String> getStockData(@RequestParam String symbol,
                                     @RequestParam(defaultValue = "compact") String outputsize,
                                     @RequestParam(defaultValue = "json") DataType datatype,
                                     @RequestParam(defaultValue = "TIME_SERIES_DAILY") TimeSeriesType type) {
        return stockService.sendRequestCoreStock(null, symbol, datatype.getValue(), type.getValue(), null, outputsize);
    }

    @GetMapping("/symbol-search")
    public Mono<String> symbolSearch(
            @RequestParam String function,
            @RequestParam String keywords,
            @RequestParam(required = false) DataType datatype) {
        return stockService.sendRequestCoreStock(function, null, datatype.getValue(), null, keywords, null);
    }

    @GetMapping("/latestInfo")
    public Mono<String> getLatestInfo(@RequestParam String function,
                                             @RequestParam String symbol,
                                             @RequestParam(required = false) DataType datatype) {
        return stockService.sendRequestCoreStock(function, symbol, datatype.getValue(), null, null, null);
    }

    @GetMapping("/getGlobalStatus")
    public Mono<String> getGlobalStatus(@RequestParam String function){
        return stockService.sendRequestCoreStock(function, null, null, null, null, null);
    }

    @GetMapping("/getFundamentalData")
    public Mono<String> getFundamentalData(@RequestParam String function,
                                     @RequestParam String symbol){
        return stockService.sendRequestCoreStock(function, symbol, null, null, null, null);
    }

    @GetMapping("/getDividends")
    public Mono<String> getDividends(@RequestParam String function,
                                     @RequestParam String symbol){
        return stockService.sendRequestCoreStock(function, symbol, null, null, null, null);
    }

    @GetMapping("/getBalanceSheet")
    public Mono<String> getBalanceSheet(
                                     @RequestParam String symbol){
        return stockService.sendRequestCoreStock("BALANCE_SHEET", symbol, null, null, null, null);
    }

    @GetMapping("/getEarnings")
    public Mono<String> getEarnings(
            @RequestParam String symbol){
        return stockService.sendRequestCoreStock("EARNINGS", symbol, null, null, null, null);
    }

    @GetMapping("/getEarningsCalendar")
    public Mono<String> getEarningCalendar(
            @RequestParam String symbol){
        return stockService.sendRequestCoreStock("EARNINGS_CALENDAR", symbol, null, null, null, null);
    }
}
