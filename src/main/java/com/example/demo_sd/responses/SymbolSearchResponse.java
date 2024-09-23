package com.example.demo_sd.responses;

import java.util.List;


public class SymbolSearchResponse {
    private List<BestMatch> bestMatches;


    public static class BestMatch {
        private String symbol;
        private String name;
        private String type;
        private String region;
        private String marketOpen;
        private String marketClose;
        private String timezone;
        private String currency;
        private String matchScore;

        BestMatch(String symbol, String name, String type, String region, String marketOpen, String marketClose, String timezone, String currency, String matchScore) {
            this.symbol = symbol;
            this.name = name;
            this.type = type;
            this.region = region;
            this.marketOpen = marketOpen;
            this.marketClose = marketClose;
            this.timezone = timezone;
            this.currency = currency;
            this.matchScore = matchScore;
        }

        public static BestMatchBuilder builder() {
            return new BestMatchBuilder();
        }

        public String getSymbol() {
            return this.symbol;
        }

        public String getName() {
            return this.name;
        }

        public String getType() {
            return this.type;
        }

        public String getRegion() {
            return this.region;
        }

        public String getMarketOpen() {
            return this.marketOpen;
        }

        public String getMarketClose() {
            return this.marketClose;
        }

        public String getTimezone() {
            return this.timezone;
        }

        public String getCurrency() {
            return this.currency;
        }

        public String getMatchScore() {
            return this.matchScore;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public void setMarketOpen(String marketOpen) {
            this.marketOpen = marketOpen;
        }

        public void setMarketClose(String marketClose) {
            this.marketClose = marketClose;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setMatchScore(String matchScore) {
            this.matchScore = matchScore;
        }

        public static class BestMatchBuilder {
            private String symbol;
            private String name;
            private String type;
            private String region;
            private String marketOpen;
            private String marketClose;
            private String timezone;
            private String currency;
            private String matchScore;

            BestMatchBuilder() {
            }

            public BestMatchBuilder symbol(String symbol) {
                this.symbol = symbol;
                return this;
            }

            public BestMatchBuilder name(String name) {
                this.name = name;
                return this;
            }

            public BestMatchBuilder type(String type) {
                this.type = type;
                return this;
            }

            public BestMatchBuilder region(String region) {
                this.region = region;
                return this;
            }

            public BestMatchBuilder marketOpen(String marketOpen) {
                this.marketOpen = marketOpen;
                return this;
            }

            public BestMatchBuilder marketClose(String marketClose) {
                this.marketClose = marketClose;
                return this;
            }

            public BestMatchBuilder timezone(String timezone) {
                this.timezone = timezone;
                return this;
            }

            public BestMatchBuilder currency(String currency) {
                this.currency = currency;
                return this;
            }

            public BestMatchBuilder matchScore(String matchScore) {
                this.matchScore = matchScore;
                return this;
            }

            public BestMatch build() {
                return new BestMatch(this.symbol, this.name, this.type, this.region, this.marketOpen, this.marketClose, this.timezone, this.currency, this.matchScore);
            }

            public String toString() {
                return "SymbolSearchResponse.BestMatch.BestMatchBuilder(symbol=" + this.symbol + ", name=" + this.name + ", type=" + this.type + ", region=" + this.region + ", marketOpen=" + this.marketOpen + ", marketClose=" + this.marketClose + ", timezone=" + this.timezone + ", currency=" + this.currency + ", matchScore=" + this.matchScore + ")";
            }
        }
    }
}