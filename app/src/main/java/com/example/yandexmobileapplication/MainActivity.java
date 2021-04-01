package com.example.yandexmobileapplication;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yandexmobileapplication.ForTinyDB.ResponseStockDTOWrapper;
import com.example.yandexmobileapplication.Response.BestMatchingList;
import com.example.yandexmobileapplication.Response.Result;
import com.example.yandexmobileapplication.Response.StockPrice;
import com.example.yandexmobileapplication.extensions.Extensions;

import TinyDB.TinyDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

public class MainActivity<object> extends AppCompatActivity {
    private final String BASE_URL = "https://finnhub.io/api/";
    private final Retrofit retroClient = RetroClient.getRetrofitInstance();
    private final ApiService apiService = retroClient.create(ApiService.class);
    private final int stockPerPage = 10;
    private final Context context =this;

    private StockAdapter adapterSearch = null;
    private StockAdapter adapterFavourites = null;
    private StockAdapter adapterStocks = null;

    private int requestNumber = 0;

    private EditText search;
    private ImageButton clear;
    private Button stocks;
    private Button favourite;
    private RecyclerView stocksRecycler;

    private LinkedList<StockDTO> stockDTO = new LinkedList<>();
    private LinkedList<StockDTO> stockDTOsSearching = new LinkedList<>();
    private LinkedList<StockDTO> favouriteDTOs = new LinkedList<>();

    public LinkedList<ApiDataLists> responseStockDTO = new LinkedList<>();
    private LinkedList<ApiDataLists> responseStockDTOsSearching = new LinkedList<>();

    private ApplicationStatus favouriteStatus = ApplicationStatus.IS_NOT_IN_FAVOURITE;
    private ApplicationStatus searchingStatus = ApplicationStatus.IS_NOT_SEARCHING;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"ResourceType", "CutPasteId"})
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stocksRecycler = findViewById(R.id.rv_stocks);

        loadStartStockListLocal();

        search = findViewById(R.id.input_search);
        clear = findViewById(R.id.btn_clear);
        stocks = findViewById(R.id.btn_stocks);
        favourite = findViewById(R.id.btn_favourite);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    clear.setVisibility(View.VISIBLE);
                }
                else {
                    clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    String searchString = search.getText().toString().toLowerCase(Locale.getDefault());
                    if(!searchString.equals("")) {
                        if(favouriteStatus.equals(ApplicationStatus.IS_NOT_IN_FAVOURITE)) {
                            searchingStatus = ApplicationStatus.IS_SEARCHING;

                            stockDTOsSearching.clear();
                            responseStockDTOsSearching.clear();

                            stocksRecycler.swapAdapter(adapterStocks,false);

                            getBestMatchingTickerFromApi(searchString);

                        }
                        else {
                            stockDTOsSearching.clear();
                            responseStockDTOsSearching.clear();

                            for(int i = 0; i < responseStockDTO.size(); i++) {
                                ApiDataLists defaultDTOValue = responseStockDTO.get(i);
                                if(defaultDTOValue.companyCode.toLowerCase(Locale.getDefault()).equals(searchString) ||
                                        defaultDTOValue.companyName.toLowerCase(Locale.getDefault()).equals(searchString) &&
                                favouriteStatus.equals(ApplicationStatus.IS_IN_FAVOURITE)) {
                                    responseStockDTOsSearching.add(defaultDTOValue);
                                }
                                getPriceForStocksFromApi(responseStockDTOsSearching,adapterSearch,stockDTOsSearching);
                            }
                        }
                    }
                    else {
                        searchingStatus = ApplicationStatus.IS_NOT_SEARCHING;
                        requestNumber = 0;

                        if(favouriteStatus.equals(ApplicationStatus.IS_NOT_IN_FAVOURITE)) {
                            stocksRecycler.swapAdapter(adapterStocks,true);
                        }
                        else if (favouriteStatus.equals(ApplicationStatus.IS_IN_FAVOURITE)) {
                            stocksRecycler.swapAdapter(adapterFavourites,true);
                        }
                    }
                }
                return true;
            }
        });

        stocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStocks();
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavourites();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchingStatus = ApplicationStatus.IS_NOT_SEARCHING;
                requestNumber = 0;
                search.setText("");

                if(favouriteStatus.equals(ApplicationStatus.IS_NOT_IN_FAVOURITE)) {
                    stocksRecycler.swapAdapter(adapterStocks,true);
                }
                else {
                    stocksRecycler.swapAdapter(adapterFavourites,true);
                }
            }
        });

        adapterSearch = new StockAdapter(stockDTOsSearching);
        adapterSearch.stockAdapterListener = new StockAdapterListener() {
            @Override
            public void onMarkedAsFavourite(StockDTO stock) {
                addedToFavourites(stock);
            }

            @Override
            public void onRemovedFromFavourite(StockDTO stock) {
                removedFromFavourites(stock);
            }
        };

        adapterStocks = new StockAdapter(stockDTO);
        adapterStocks.stockAdapterListener = new StockAdapterListener() {
            @Override
            public void onMarkedAsFavourite(StockDTO stock) {
                addedToFavourites(stock);
            }

            @Override
            public void onRemovedFromFavourite(StockDTO stock) {
                removedFromFavourites(stock);
            }
        };

        adapterFavourites = new StockAdapter(favouriteDTOs);
        adapterFavourites.stockAdapterListener = new StockAdapterListener() {
            @Override
            public void onMarkedAsFavourite(StockDTO stock) {
                addedToFavourites(stock);
            }

            @Override
            public void onRemovedFromFavourite(StockDTO stock) {
                removedFromFavourites(stock);
            }
        };

        getStockList();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        stocksRecycler.setHasFixedSize(true);
        stocksRecycler.setLayoutManager(layoutManager);
        stocksRecycler.setAdapter(adapterStocks);
        showStocks();
    }

    private void addedToFavourites(StockDTO stock) {
        favouriteDTOs.add(stock);
        for(int i = 0; i < responseStockDTO.size(); i++) {
            if(responseStockDTO.get(i).companyCode.equals(stock.companyCode)) {
                responseStockDTO.get(i).favouriteStatus = ApplicationStatus.IS_IN_FAVOURITE;
            }
        }

        for (int i = 0; i < stockDTO.size(); i++) {
            if(stockDTO.get(i).companyCode.equals(stock.companyCode)) {
                stockDTO.get(i).favouriteStatus = ApplicationStatus.IS_IN_FAVOURITE;
            }
        }
        saveStartSockListLocal();
    }

    private void removedFromFavourites(StockDTO stock) {
        for(int i = 0; i < responseStockDTO.size(); i++) {
            if(responseStockDTO.get(i).companyCode.equals(stock.companyCode)){
                responseStockDTO.get(i).favouriteStatus = ApplicationStatus.IS_NOT_IN_FAVOURITE;
            }
        }

        for(int i = 0; i < favouriteDTOs.size(); i++) {
            if(favouriteDTOs.get(i).companyCode.equals(stock.companyCode)) {
                favouriteDTOs.get(i).favouriteStatus = ApplicationStatus.IS_NOT_IN_FAVOURITE;
            }
        }

        for(int i = 0; i < stockDTO.size(); i++) {
            if (stockDTO.get(i).companyCode.equals(stock.companyCode)) {
                stockDTO.get(i).favouriteStatus = ApplicationStatus.IS_NOT_IN_FAVOURITE;
            }
        }

        for(int i = 0; i < stockDTOsSearching.size(); i++) {
            if(stockDTOsSearching.get(i).companyCode.equals(stock.companyCode)) {
                stockDTOsSearching.get(i).favouriteStatus = ApplicationStatus.IS_NOT_IN_FAVOURITE;
            }
        }
        saveStartSockListLocal();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void setButtonActive(Button button) {
        button.setTextColor(this.getColor(R.color.black));
        button.setEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void setButtonInactive(Button button) {
        button.setTextColor(this.getColor(R.color.grey_light));
        button.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void showStocks() {
        favouriteStatus = ApplicationStatus.IS_NOT_IN_FAVOURITE;

        setButtonActive(stocks);
        setButtonInactive(favourite);
        if(searchingStatus.equals(ApplicationStatus.IS_NOT_SEARCHING)) {
            stocksRecycler.swapAdapter(adapterStocks, true);
        }
        else {
            stocksRecycler.swapAdapter(adapterFavourites, true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void showFavourites() {
        favouriteStatus = ApplicationStatus.IS_IN_FAVOURITE;

        setButtonActive(favourite);
        setButtonInactive(stocks);

        stocksRecycler.swapAdapter(adapterFavourites, true);
    }

    void getStockList() {
        getPriceForStocksFromApi(responseStockDTO, adapterStocks,stockDTO);
    }



    private void getBestMatchingTickerFromApi(String searchString) {
        Call <BestMatchingList> call = apiService.getBestMatching("https://finnhub.io/api/v1/search?q=" + searchString + "&token=c1c4o0v48v6sp0s59kj0");
        call.enqueue(new Callback<BestMatchingList>() {

            @Override
            public void onResponse
                    (@NotNull Call<BestMatchingList> call,
                     @NotNull Response<BestMatchingList> response) {
                if(!search.getText().toString().equals("")) {
                    if (response.errorBody() != null) {
                        Toast.makeText(context,"Failed connect to server,\n please try again later", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (int i = 0; i < responseStockDTO.size(); i++) {
                            ApiDataLists currentList = responseStockDTO.get(i);
                            if(currentList.companyCode.toLowerCase(Locale.getDefault()).equals(searchString) ||
                            currentList.companyName.toLowerCase(Locale.getDefault()).equals(searchString)) {
                                responseStockDTOsSearching.add(currentList);
                            }
                        }
                        if(response.body() != null) {
                            Extensions isContains = new Extensions();
                            LinkedList<Result> responseList = response.body().responsedStockDTO;
                            int currentLoadedStocks = response.body().count;

                            for (int i = 0; i < currentLoadedStocks; i++) {
                                ApiDataLists currentList = new ApiDataLists(responseList.get(i).companyTicker,
                                        responseList.get(i).companyName, ApplicationStatus.IS_NOT_IN_FAVOURITE);

                                if (isContains.isContainsApiDataList(currentList, responseStockDTOsSearching)) {
                                    responseStockDTOsSearching.add(currentList);
                                }
                            }
                        }
                    }
                }
                getPriceForStocksFromApi(responseStockDTOsSearching,adapterSearch,stockDTOsSearching);

            }

            @Override
            public void onFailure(@NotNull Call<BestMatchingList> call, @NotNull Throwable t) {
                Toast.makeText(context,"Failed to connect to server", Toast.LENGTH_SHORT ).show();
            }
        });
    }
    void getPriceForStocksFromApi(
            LinkedList<ApiDataLists> defaultList, // responseStockDTO
            StockAdapter adapter,
            LinkedList<StockDTO> stockDTOList // StockDTO
            ) {
        LinkedList<ApiDataLists> sendRequestList = new LinkedList<>();
        final int currentLoadedStocks = stockDTOList.size();

        if(currentLoadedStocks == 0) {
            stocksRecycler.swapAdapter(adapter,false);
        }

        for (int i = 0; i < currentLoadedStocks + stockPerPage; i++) {
            if(defaultList.size() == stockDTOList.size()) {
                break;
            }

            if(i < defaultList.size()) {
                sendRequestList.add(defaultList.get(i));
                requestNumber++;
                getPrice(sendRequestList, adapter, stockDTOList);
            }
        }

    }
    private void getPrice(LinkedList<ApiDataLists> sendRequestList, StockAdapter adapter,LinkedList<StockDTO> stockDTOList) {
        final int listSize = sendRequestList.size();
        while (stockDTO.size() != listSize) {
            int count = 0;
            Call<StockPrice> call = apiService.getPrice("https://finnhub.io/api/v1/quote?symbol=" + sendRequestList.get(count).companyCode + "&token=c1c4o0v48v6sp0s59kj0");
            ApiDataLists currentTicker = sendRequestList.get(count);
            call.enqueue(new Callback<StockPrice>() {
                             @Override
                             public void onResponse(
                                     @NotNull Call<StockPrice> call,
                                     @NotNull Response<StockPrice> response) {
                                 if(requestNumber > 0) {
                                     requestNumber--;
                                     if(response.errorBody() != null) {
                                         if (response.code() == 429) {
                                             requestNumber = 0;
                                             Toast.makeText(context,"Failed to connect to server\n Please, try again later",Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                     if(response.body() != null) {
                                         String priceChange = getPriceChange(response);

                                         StockDTO loadedStock = new StockDTO(
                                                 currentTicker.companyCode,
                                                 currentTicker.companyName,
                                                 String.format(Locale.US, "$%.2f",response.body().c),
                                                 priceChange,
                                                 currentTicker.favouriteStatus
                                         );
                                         Extensions extensions = new Extensions();
                                         if(!extensions.isContainsStockDTO(loadedStock, stockDTOList)) {
                                             stockDTO.add(loadedStock);
                                         }
                                         if(!extensions.isContainsApiDataList(currentTicker, responseStockDTO)) {
                                             responseStockDTO.add(currentTicker);
                                         }
                                         adapter.notifyDataSetChanged();
                                     }
                                 }
                                 if(requestNumber != 0) {
                                     requestNumber = 0;
                                 }
                             }
                @Override
                public void onFailure(@NotNull Call call, @NotNull Throwable t) {
                    Toast.makeText(context,"Failed to connect to server\n Please, try again later", Toast.LENGTH_SHORT).show();
                }
            });
            count++;
        }
    }
    private String getPriceChange(Response<StockPrice> response) {
        assert response.body() != null;
        if (Double.parseDouble(String.format(Locale.US, "+%.2f", response.body().c - response.body().pc)) > 0) {
            return String.format(Locale.US, "+%.2f", response.body().c - response.body().pc) + "(" +
                    String.format(Locale.US, "+%.2f", 100 * (response.body().c - response.body().pc) / response.body().c) + "%)";
        } else if (Double.parseDouble(String.format(Locale.US, "+%.2f", response.body().c - response.body().pc)) < 0) {
            return String.format(Locale.US, "+%.2f", response.body().c - response.body().pc) + "(" +
                    String.format(Locale.US, "+%.2f", 100 * (response.body().c - response.body().pc) / response.body().c) + "%)";
        } else {
            return String.format(Locale.US, "+%.2f", response.body().c - response.body().pc) + "(" +
                    String.format(Locale.US, "+%.2f", 100 * (response.body().c - response.body().pc) / response.body().c) + "%)";
        }
    }

    private void loadStartStockListLocal() {
        TinyDB tinyDB = new TinyDB(context);
        ResponseStockDTOWrapper loaded = tinyDB.getObject(ApiDataLists.key, ResponseStockDTOWrapper.class);

        if (loaded == null) {
            responseStockDTO = defaultList;
        }
        else {
            responseStockDTO = loaded.getResponseStockDTO();
        }


    }
    private void saveStartSockListLocal() {
        responseStockDTO = defaultList;
        TinyDB tinyDB = new TinyDB(context);
        tinyDB.putObject(ApiDataLists.key, new ResponseStockDTOWrapper(responseStockDTO));
    }

     LinkedList<ApiDataLists> defaultList = new LinkedList<>(Arrays.asList(
            (new ApiDataLists("Apple Inc.", "AAPL", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Microsoft Corporation", "MSFT", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Amazon.com Inc.", "AMZN", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Facebook Inc. Class A", "FB", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("JPMorgan Chase & Co.", "JPM", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Berkshire Hathaway Inc. Class B", "BRK.B", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Johnson & Johnson", "AAPL", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Alphabet Inc. Class C", "GOOG", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Alphabet Inc. Class A", "GOOGL", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Exxon Mobil Corporation", "XOM", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Bank of America Corporation", "BAC", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Wells Fargo & Company", "WFC", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Intel Corporation", "INTC", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("AT&T Inc.", "T", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Visa Inc. Class A", "V", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Cisco Systems Inc.", "CSCO", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Chevron Corporation", "CVX", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("UnitedHealth Group Incorporated", "UNH", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Pfizer Inc.", "PFE", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Home Depot Inc.", "HD", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Procter & Gamble Company", "PG", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Verizon Communications Inc.", "VZ", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Citigroup Inc.", "C", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("AbbVie Inc.", "ABBV", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Boeing Company", "BA", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Coca-Cola Company", "KO", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Comcast Corporation Class A", "CMCSA", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Mastercard Incorporated Class A", "MA", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Philip Morris International Inc.", "PM", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("DowDuPont Inc.", "DWDP", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("PepsiCo Inc.", "PEP", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Oracle Corporation", "ORCL", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Walt Disney Company", "DIS", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Merck & Co. Inc.", "MRK", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("NVIDIA Corporation", "NVDA", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("3M Company", "MMM", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Amgen Inc.", "AMGN", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("International Business Machines Corporation", "IBM", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Netflix Inc.", "NFLX", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Walmart Inc.", "WMT", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Altria Group Inc", "MO", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("McDonald’s Corporation", "MCD", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("General Electric Company", "GE", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Honeywell International Inc.", "HON", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Medtronic plc", "MDT", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Abbott Laboratories", "ABT", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Texas Instruments Incorporated", "TXN", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Bristol-Myers Squibb Company", "BMY", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Adobe Systems Incorporated", "ADBE", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Union Pacific Corporation", "UNP", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Gilead Sciences Inc.", "GILD", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Booking Holdings Inc.", "BKNG", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Broadcom Limited", "AVGO", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Accenture Plc Class A", "ACN", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("United Technologies Corporation", "UTX", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Goldman Sachs Group Inc.", "GS", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Schlumberger NV", "SLB", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Caterpillar Inc.", "CAT", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("PayPal Holdings Inc", "PYPL", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("QUALCOMM Incorporated", "QCOM", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("salesforce.com inc.", "CRM", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("NIKE Inc. Class B", "NKE", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Thermo Fisher Scientific Inc.", "TMO", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("U.S. Bancorp", "USB", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Starbucks Corporation", "SBUX", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Lockheed Martin Corporation", "LMT", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Costco Wholesale Corporation", "COST", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Morgan Stanley", "MS", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("PNC Financial Services Group Inc.", "PNC", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Eli Lilly and Company", "LLY", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("United Parcel Service Inc. Class B", "UPS", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Time Warner Inc.", "TWX", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("NextEra Energy Inc.", "NEE", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Celgene Corporation", "CELG", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Lowe’s Companies Inc.", "LOW", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("BlackRock Inc.", "BLK", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("CVS Health Corporation", "CVS", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("American Express Company", "AXP", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Micron Technology Inc.", "MU", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Charter Communications Inc. Class A", "CHTR", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Charles Schwab Corporation", "SCHW", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Mondelez International Inc. Class A", "MDLZ", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Chubb Limited", "CB", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("ConocoPhillips", "COP", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Applied Materials Inc.", "AMAT", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Danaher Corporation", "DHR", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("American Tower Corporation", "AMT", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Colgate-Palmolive Company", "CL", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("General Dynamics Corporation", "GD", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("FedEx Corporation", "FDX", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Raytheon Company", "RTN", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Walgreens Boots Alliance Inc", "WBA", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Northrop Grumman Corporation", "NOC", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Biogen Inc.", "BIIB", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Becton Dickinson and Company", "BDX", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Anthem Inc.", "ANTM", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Aetna Inc.", "AET", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("EOG Resources Inc.", "EOG", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Bank of New York Mellon Corporation", "BK", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Activision Blizzard Inc.", "ATVI", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("CME Group Inc. Class A", "CME", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Allergan plc", "AGN", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Monsanto Company", "MON", ApplicationStatus.IS_NOT_IN_FAVOURITE)),
            (new ApiDataLists("Stryker Corporation", "SYK", ApplicationStatus.IS_NOT_IN_FAVOURITE))));
}
