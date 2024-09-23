package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.BestMatchDto;
import com.example.demo_sd.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class InformationController {
    @Autowired
    private StockService stockService;

    @GetMapping("/all")
    public String getStockData(@RequestParam String symbol,
                                    @RequestParam(defaultValue = "compact") String outputsize,
                                    @RequestParam(defaultValue = "json") String datatype,
                                    @RequestParam(defaultValue = "TIME_SERIES_DAILY") String type) {
        return stockService.getStockData(symbol, outputsize, datatype, type);
    }

    @GetMapping("/symbol-search")
    public String symbolSearch(
            @RequestParam String function,
            @RequestParam String keywords,
            @RequestParam(required = false) String datatype) {


        BestMatchDto request = new BestMatchDto();
        request.setFunction(function);
        request.setKeywords(keywords);
        request.setDatatype(datatype);

        return stockService.getSymbolicSearch(request);
    }

    @GetMapping("/latestInfo")
    public String getLatestInfo(@RequestParam String symbol,
                               @RequestParam(defaultValue = "compact") String outputsize,
                               @RequestParam(defaultValue = "json") String datatype,
                               @RequestParam(defaultValue = "TIME_SERIES_DAILY") String type) {
        return stockService.getStockData(symbol, outputsize, datatype, type);
    }
}
