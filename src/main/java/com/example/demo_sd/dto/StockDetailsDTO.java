package com.example.demo_sd.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockDetailsDTO {

    @JsonProperty("Symbol")
    private String symbol;

    @JsonProperty("AssetType")
    private String assetType;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("CIK")
    private String cik;

    @JsonProperty("Exchange")
    private String exchange;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Sector")
    private String sector;

    @JsonProperty("Industry")
    private String industry;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("OfficialSite")
    private String officialSite;

    @JsonProperty("FiscalYearEnd")
    private String fiscalYearEnd;

    @JsonProperty("LatestQuarter")
    private String latestQuarter;

    @JsonProperty("MarketCapitalization")
    private String marketCapitalization;

    @JsonProperty("EBITDA")
    private String ebitda;

    @JsonProperty("PERatio")
    private String peRatio;

    @JsonProperty("PEGRatio")
    private String pegRatio;

    @JsonProperty("BookValue")
    private String bookValue;

    @JsonProperty("DividendPerShare")
    private String dividendPerShare;

    @JsonProperty("DividendYield")
    private String dividendYield;

    @JsonProperty("EPS")
    private String eps;

    @JsonProperty("RevenuePerShareTTM")
    private String revenuePerShareTTM;

    @JsonProperty("ProfitMargin")
    private String profitMargin;

    @JsonProperty("OperatingMarginTTM")
    private String operatingMarginTTM;

    @JsonProperty("ReturnOnAssetsTTM")
    private String returnOnAssetsTTM;

    @JsonProperty("ReturnOnEquityTTM")
    private String returnOnEquityTTM;

    @JsonProperty("RevenueTTM")
    private String revenueTTM;

    @JsonProperty("GrossProfitTTM")
    private String grossProfitTTM;

    @JsonProperty("DilutedEPSTTM")
    private String dilutedEPSTTM;

    @JsonProperty("QuarterlyEarningsGrowthYOY")
    private String quarterlyEarningsGrowthYOY;

    @JsonProperty("QuarterlyRevenueGrowthYOY")
    private String quarterlyRevenueGrowthYOY;

    @JsonProperty("AnalystTargetPrice")
    private String analystTargetPrice;

    @JsonProperty("AnalystRatingStrongBuy")
    private int analystRatingStrongBuy;

    @JsonProperty("AnalystRatingBuy")
    private int analystRatingBuy;

    @JsonProperty("AnalystRatingHold")
    private int analystRatingHold;

    @JsonProperty("AnalystRatingSell")
    private int analystRatingSell;

    @JsonProperty("AnalystRatingStrongSell")
    private int analystRatingStrongSell;

    @JsonProperty("TrailingPE")
    private String trailingPE;

    @JsonProperty("ForwardPE")
    private String forwardPE;

    @JsonProperty("PriceToSalesRatioTTM")
    private String priceToSalesRatioTTM;

    @JsonProperty("PriceToBookRatio")
    private String priceToBookRatio;

    @JsonProperty("EVToRevenue")
    private String evToRevenue;

    @JsonProperty("EVToEBITDA")
    private String evToEbitda;

    @JsonProperty("Beta")
    private String beta;

    @JsonProperty("52WeekHigh")
    private String week52High;

    @JsonProperty("52WeekLow")
    private String week52Low;

    @JsonProperty("50DayMovingAverage")
    private String movingAverage50Day;

    @JsonProperty("200DayMovingAverage")
    private String movingAverage200Day;

    @JsonProperty("SharesOutstanding")
    private String sharesOutstanding;

    @JsonProperty("DividendDate")
    private String dividendDate;

    @JsonProperty("ExDividendDate")
    private String exDividendDate;
}
