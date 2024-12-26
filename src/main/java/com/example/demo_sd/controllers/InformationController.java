package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.Company;
import com.example.demo_sd.dto.PaginatedResponse;
import com.example.demo_sd.enumerations.DataType;
import com.example.demo_sd.enumerations.TimeSeriesType;
import com.example.demo_sd.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin()
public class InformationController {
    @Autowired
    private StockService stockService;

    private List<Company> companies = new ArrayList<>();

    public InformationController() {
        companies.add(new Company("Apple Inc.", "AAPL", "Technology"));
        companies.add(new Company("Microsoft Corp.", "MSFT", "Technology"));
        companies.add(new Company("Amazon.com Inc.", "AMZN", "Consumer Discretionary"));
        companies.add(new Company("Tesla Inc.", "TSLA", "Consumer Discretionary"));
        companies.add(new Company("Alphabet Inc.", "GOOGL", "Technology"));
        companies.add(new Company("Facebook, Inc.", "FB", "Communication Services"));
        companies.add(new Company("Alibaba Group", "BABA", "Consumer Discretionary"));
        companies.add(new Company("NVIDIA Corporation", "NVDA", "Technology"));
        companies.add(new Company("Netflix, Inc.", "NFLX", "Communication Services"));
        companies.add(new Company("Adobe Inc.", "ADBE", "Technology"));
        companies.add(new Company("Salesforce.com Inc.", "CRM", "Technology"));
        companies.add(new Company("PayPal Holdings, Inc.", "PYPL", "Finance"));
        companies.add(new Company("The Walt Disney Company", "DIS", "Communication Services"));
        companies.add(new Company("Intel Corporation", "INTC", "Technology"));
        companies.add(new Company("IBM", "IBM", "Technology"));
        companies.add(new Company("Qualcomm Incorporated", "QCOM", "Technology"));
        companies.add(new Company("Coca-Cola Company", "KO", "Consumer Staples"));
        companies.add(new Company("PepsiCo, Inc.", "PEP", "Consumer Staples"));
        companies.add(new Company("Procter & Gamble Co.", "PG", "Consumer Staples"));
        companies.add(new Company("Unilever PLC", "UL", "Consumer Staples"));
        companies.add(new Company("Johnson & Johnson", "JNJ", "Healthcare"));
        companies.add(new Company("Pfizer Inc.", "PFE", "Healthcare"));
        companies.add(new Company("Merck & Co., Inc.", "MRK", "Healthcare"));
        companies.add(new Company("Novartis AG", "NVS", "Healthcare"));
        companies.add(new Company("Bristol-Myers Squibb Company", "BMY", "Healthcare"));
        companies.add(new Company("Chevron Corporation", "CVX", "Energy"));
        companies.add(new Company("Exxon Mobil Corporation", "XOM", "Energy"));
        companies.add(new Company("Royal Dutch Shell PLC", "RDS.A", "Energy"));
        companies.add(new Company("BP PLC", "BP", "Energy"));
        companies.add(new Company("Walmart Inc.", "WMT", "Consumer Staples"));
        companies.add(new Company("Costco Wholesale Corporation", "COST", "Consumer Staples"));
        companies.add(new Company("Home Depot, Inc.", "HD", "Consumer Discretionary"));
        companies.add(new Company("Lowe's Companies, Inc.", "LOW", "Consumer Discretionary"));
        companies.add(new Company("Target Corporation", "TGT", "Consumer Discretionary"));
        companies.add(new Company("Nike, Inc.", "NKE", "Consumer Discretionary"));
        companies.add(new Company("Starbucks Corporation", "SBUX", "Consumer Discretionary"));
        companies.add(new Company("McDonald's Corporation", "MCD", "Consumer Discretionary"));
        companies.add(new Company("Visa Inc.", "V", "Finance"));
        companies.add(new Company("Mastercard Incorporated", "MA", "Finance"));
        companies.add(new Company("American Express Company", "AXP", "Finance"));
        companies.add(new Company("JPMorgan Chase & Co.", "JPM", "Finance"));
        companies.add(new Company("Bank of America Corporation", "BAC", "Finance"));
        companies.add(new Company("Citigroup Inc.", "C", "Finance"));
        companies.add(new Company("Wells Fargo & Company", "WFC", "Finance"));
        companies.add(new Company("Goldman Sachs Group, Inc.", "GS", "Finance"));
        companies.add(new Company("Morgan Stanley", "MS", "Finance"));
        companies.add(new Company("Berkshire Hathaway Inc.", "BRK.A", "Finance"));
        companies.add(new Company("BlackRock, Inc.", "BLK", "Finance"));
        companies.add(new Company("T-Mobile US, Inc.", "TMUS", "Communication Services"));
        companies.add(new Company("Verizon Communications Inc.", "VZ", "Communication Services"));
        companies.add(new Company("AT&T Inc.", "T", "Communication Services"));
        companies.add(new Company("Sprint Corporation", "S", "Communication Services"));
        companies.add(new Company("NextEra Energy, Inc.", "NEE", "Utilities"));
        companies.add(new Company("Duke Energy Corporation", "DUK", "Utilities"));
        companies.add(new Company("Southern Company", "SO", "Utilities"));
        companies.add(new Company("Exelon Corporation", "EXC", "Utilities"));
        companies.add(new Company("Consolidated Edison, Inc.", "ED", "Utilities"));
        companies.add(new Company("3M Company", "MMM", "Industrials"));
        companies.add(new Company("General Electric Company", "GE", "Industrials"));
        companies.add(new Company("Honeywell International Inc.", "HON", "Industrials"));
        companies.add(new Company("Caterpillar Inc.", "CAT", "Industrials"));
        companies.add(new Company("Boeing Company", "BA", "Industrials"));
        companies.add(new Company("Ford Motor Company", "F", "Consumer Discretionary"));
        companies.add(new Company("General Motors Company", "GM", "Consumer Discretionary"));
        companies.add(new Company("Volkswagen AG", "VWAGY", "Consumer Discretionary"));
        companies.add(new Company("Honda Motor Co., Ltd.", "HMC", "Consumer Discretionary"));
        companies.add(new Company("Toyota Motor Corporation", "TM", "Consumer Discretionary"));
        companies.add(new Company("Siemens AG", "SIEGY", "Industrials"));
        companies.add(new Company("Schneider Electric SE", "SBGSF", "Industrials"));
        companies.add(new Company("United Parcel Service, Inc.", "UPS", "Industrials"));
        companies.add(new Company("FedEx Corporation", "FDX", "Industrials"));
        companies.add(new Company("Netflix, Inc.", "NFLX", "Communication Services"));
        companies.add(new Company("Spotify Technology S.A.", "SPOT", "Communication Services"));
        companies.add(new Company("eBay Inc.", "EBAY", "Consumer Discretionary"));
        companies.add(new Company("Booking Holdings Inc.", "BKNG", "Consumer Discretionary"));
        companies.add(new Company("Expedia Group, Inc.", "EXPE", "Consumer Discretionary"));
        companies.add(new Company("Salesforce.com, Inc.", "CRM", "Technology"));
        companies.add(new Company("Zillow Group, Inc.", "Z", "Consumer Discretionary"));
        companies.add(new Company("Square, Inc.", "SQ", "Finance"));
        companies.add(new Company("Snap Inc.", "SNAP", "Communication Services"));
        companies.add(new Company("Pinterest, Inc.", "PINS", "Communication Services"));
        companies.add(new Company("Twitter, Inc.", "TWTR", "Communication Services"));
        companies.add(new Company("Lyft, Inc.", "LYFT", "Consumer Discretionary"));
        companies.add(new Company("Uber Technologies, Inc.", "UBER", "Consumer Discretionary"));
        companies.add(new Company("Palantir Technologies Inc.", "PLTR", "Technology"));
        companies.add(new Company("CrowdStrike Holdings, Inc.", "CRWD", "Technology"));
        companies.add(new Company("Zoom Video Communications, Inc.", "ZM", "Technology"));
        companies.add(new Company("DocuSign, Inc.", "DOCU", "Technology"));
        companies.add(new Company("ServiceNow, Inc.", "NOW", "Technology"));
        companies.add(new Company("RingCentral, Inc.", "RNG", "Technology"));
        companies.add(new Company("Slack Technologies, Inc.", "WORK", "Technology"));
        companies.add(new Company("Cloudflare, Inc.", "NET", "Technology"));
        companies.add(new Company("Snowflake Inc.", "SNOW", "Technology"));
        companies.add(new Company("Twilio Inc.", "TWLO", "Technology"));
        companies.add(new Company("Nokia Corporation", "NOK", "Technology"));
        companies.add(new Company("Ericsson", "ERIC", "Technology"));
        companies.add(new Company("Toshiba Corporation", "TOSYY", "Technology"));
        companies.add(new Company("Advanced Micro Devices, Inc.", "AMD", "Technology"));
        companies.add(new Company("Micron Technology, Inc.", "MU", "Technology"));
        companies.add(new Company("Broadcom Inc.", "AVGO", "Technology"));
        companies.add(new Company("Texas Instruments Incorporated", "TXN", "Technology"));
        companies.add(new Company("Analog Devices, Inc.", "ADI", "Technology"));
        companies.add(new Company("InfuSystem Holdings, Inc.", "INFU", "Healthcare"));
        companies.add(new Company("Stryker Corporation", "SYK", "Healthcare"));
        companies.add(new Company("Thermo Fisher Scientific Inc.", "TMO", "Healthcare"));
        companies.add(new Company("Medtronic plc", "MDT", "Healthcare"));
        companies.add(new Company("AbbVie Inc.", "ABBV", "Healthcare"));
        companies.add(new Company("AstraZeneca PLC", "AZN", "Healthcare"));
        companies.add(new Company("Gilead Sciences, Inc.", "GILD", "Healthcare"));
        companies.add(new Company("Amgen Inc.", "AMGN", "Healthcare"));
        companies.add(new Company("Boeing Co.", "BA", "Industrials"));
        companies.add(new Company("Lockheed Martin Corporation", "LMT", "Industrials"));
        companies.add(new Company("Northrop Grumman Corporation", "NOC", "Industrials"));
        companies.add(new Company("Raytheon Technologies Corporation", "RTX", "Industrials"));
        companies.add(new Company("Siemens AG", "SIEGY", "Industrials"));
        companies.add(new Company("General Dynamics Corporation", "GD", "Industrials"));
        companies.add(new Company("Rockwell Automation, Inc.", "ROK", "Industrials"));
        companies.add(new Company("Honeywell International Inc.", "HON", "Industrials"));
    }

    @GetMapping("/companies")
    public PaginatedResponse<Company> getAllCompanies(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size) {

        System.out.println("Getting companies");
        if (page==-1)
            return new PaginatedResponse<>(companies, 0, companies.size(), 1);

        List<Company> paginatedCompanies = paginate(companies, page, size);


        int totalCount = companies.size();
        int totalPages = (int) Math.ceil((double) totalCount / size);


        return new PaginatedResponse<>(paginatedCompanies, totalCount, page, totalPages);
    }
    @GetMapping("/companies/search")
    public List<Company> searchCompanies(
            @RequestParam String query) {
        return companies.stream()
                .filter(company -> company.getName().toLowerCase().contains(query.toLowerCase()) ||
                        company.getSymbol().toLowerCase().contains(query.toLowerCase()) ||
                        company.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

    }


    private List<Company> paginate(List<Company> list, int page, int size) {
        list.sort(Comparator.comparing(Company::getName));

        int start = (page - 1) * size;
        int end = Math.min(start + size, list.size());

        if (start >= list.size()) {
            return new ArrayList<>();
        }

        return list.subList(start, end);
    }

    @GetMapping("/all")
    public Mono<String> getStockData(@RequestParam String symbol,
                                     @RequestParam(defaultValue = "compact", required = false) String outputsize,
                                     @RequestParam(defaultValue = "DATA_TYPE_JSON", required = false) DataType datatype,
                                     @RequestParam(defaultValue = "TIME_SERIES_DAILY", required = false) TimeSeriesType type) {
        return stockService.sendRequestCoreStock(null, symbol, datatype.getValue(), type.getValue(), null, outputsize);
    }

    @GetMapping("/symbol-search")
    public Mono<String> symbolSearch(
            @RequestParam String function,
            @RequestParam String keywords,
            @RequestParam(required = false) DataType datatype) {
        return stockService.sendRequestCoreStock(function, null, datatype.getValue(), null, keywords, null);
    }

    @GetMapping("/latest-info")

    public Mono<String> getLatestInfo(@RequestParam String symbol ) {
        return stockService.sendRequestCoreStock("OVERVIEW", symbol, "json", null, null, null);
    }

    @GetMapping("/getGlobalStatus")
    public Mono<String> getGlobalStatus(@RequestParam String function){
        return stockService.sendRequestCoreStock(function, null, null, null, null, null);
    }

    @GetMapping("/getFundamentalData")
    public Mono<String> getFundamentalData(@RequestParam String function,
                                     @RequestParam String symbol){

        System.out.println("Getting fundamental data");
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
        System.out.println("Getting balance sheet");
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
