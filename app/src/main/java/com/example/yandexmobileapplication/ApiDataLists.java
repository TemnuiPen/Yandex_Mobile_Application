package com.example.yandexmobileapplication;

import android.widget.TextView;

import java.util.LinkedList;

public class ApiDataLists {
    public TextView companyStock;

    public String getCompanyCode() {
        return companyCode;
    }

    TextView stockChange;
    String companyName;
    String companyCode;
    ApplicationStatus favouriteStatus;

    public static final String key = "CACHED_STOCKS";

    public ApiDataLists(String companyName, String companyCode, ApplicationStatus favouriteStatus) {
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.favouriteStatus = favouriteStatus;
    }

    public ApiDataLists() {
        // empty slot
    }

    public LinkedList<ApiDataLists> defaultList = new LinkedList<ApiDataLists>();

    public void SetDefaultValues() {
        defaultList.addFirst(new ApiDataLists("SanyaHuiSosi", "SHS", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Apple Inc.", "AAPL", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Microsoft Corporation", "MSFT", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Amazon.com Inc.", "AMZN", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Facebook Inc. Class A", "FB", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("JPMorgan Chase & Co.", "JPM", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Berkshire Hathaway Inc. Class B", "BRK.B", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Johnson & Johnson", "AAPL", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Alphabet Inc. Class C", "GOOG", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Alphabet Inc. Class A", "GOOGL", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Exxon Mobil Corporation", "XOM", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Bank of America Corporation", "BAC", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Wells Fargo & Company", "WFC", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Intel Corporation", "INTC", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("AT&T Inc.", "T", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Visa Inc. Class A", "V", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Cisco Systems Inc.", "CSCO", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Chevron Corporation", "CVX", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("UnitedHealth Group Incorporated", "UNH", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Pfizer Inc.", "PFE", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Home Depot Inc.", "HD", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Procter & Gamble Company", "PG", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Verizon Communications Inc.", "VZ", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Citigroup Inc.", "C", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("AbbVie Inc.", "ABBV", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Boeing Company", "BA", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Coca-Cola Company", "KO", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Comcast Corporation Class A", "CMCSA", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Mastercard Incorporated Class A", "MA", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Philip Morris International Inc.", "PM", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("DowDuPont Inc.", "DWDP", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("PepsiCo Inc.", "PEP", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Oracle Corporation", "ORCL", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Walt Disney Company", "DIS", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Merck & Co. Inc.", "MRK", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("NVIDIA Corporation", "NVDA", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("3M Company", "MMM", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Amgen Inc.", "AMGN", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("International Business Machines Corporation", "IBM", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Netflix Inc.", "NFLX", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Walmart Inc.", "WMT", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Altria Group Inc", "MO", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("McDonald’s Corporation", "MCD", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("General Electric Company", "GE", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Honeywell International Inc.", "HON", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Medtronic plc", "MDT", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Abbott Laboratories", "ABT", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Texas Instruments Incorporated", "TXN", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Bristol-Myers Squibb Company", "BMY", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Adobe Systems Incorporated", "ADBE", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Union Pacific Corporation", "UNP", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Gilead Sciences Inc.", "GILD", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Booking Holdings Inc.", "BKNG", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Broadcom Limited", "AVGO", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Accenture Plc Class A", "ACN", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("United Technologies Corporation", "UTX", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Goldman Sachs Group Inc.", "GS", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Schlumberger NV", "SLB", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Caterpillar Inc.", "CAT", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("PayPal Holdings Inc", "PYPL", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("QUALCOMM Incorporated", "QCOM", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("salesforce.com inc.", "CRM", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("NIKE Inc. Class B", "NKE", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Thermo Fisher Scientific Inc.", "TMO", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("U.S. Bancorp", "USB", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Starbucks Corporation", "SBUX", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Lockheed Martin Corporation", "LMT", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Costco Wholesale Corporation", "COST", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Morgan Stanley", "MS", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("PNC Financial Services Group Inc.", "PNC", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Eli Lilly and Company", "LLY", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("United Parcel Service Inc. Class B", "UPS", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Time Warner Inc.", "TWX", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("NextEra Energy Inc.", "NEE", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Celgene Corporation", "CELG", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Lowe’s Companies Inc.", "LOW", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("BlackRock Inc.", "BLK", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("CVS Health Corporation", "CVS", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("American Express Company", "AXP", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Micron Technology Inc.", "MU", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Charter Communications Inc. Class A", "CHTR", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Charles Schwab Corporation", "SCHW", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Mondelez International Inc. Class A", "MDLZ", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Chubb Limited", "CB", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("ConocoPhillips", "COP", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Applied Materials Inc.", "AMAT", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Danaher Corporation", "DHR", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("American Tower Corporation", "AMT", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Colgate-Palmolive Company", "CL", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("General Dynamics Corporation", "GD", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("FedEx Corporation", "FDX", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Raytheon Company", "RTN", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Walgreens Boots Alliance Inc", "WBA", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Northrop Grumman Corporation", "NOC", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Biogen Inc.", "BIIB", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Becton Dickinson and Company", "BDX", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Anthem Inc.", "ANTM", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Aetna Inc.", "AET", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("EOG Resources Inc.", "EOG", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Bank of New York Mellon Corporation", "BK", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Activision Blizzard Inc.", "ATVI", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("CME Group Inc. Class A", "CME", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Allergan plc", "AGN", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Monsanto Company", "MON", ApplicationStatus.IS_NOT_IN_FAVOURITE));
        defaultList.addFirst(new ApiDataLists("Stryker Corporation", "SYK", ApplicationStatus.IS_NOT_IN_FAVOURITE));
    }
}
