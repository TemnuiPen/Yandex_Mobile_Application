package com.example.yandexmobileapplication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockHolder> {

    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_NORMAL = 2;

    private final LinkedList<StockDTO> stockDTOs;

    public StockAdapterListener stockAdapterListener = null;

    private boolean isShowingLoading = false;


    public StockAdapter (LinkedList<StockDTO> stockDTOs) {
        this.stockDTOs = stockDTOs;
    }

    @NonNull
    @Override
    public StockAdapter.StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_NORMAL) {
            View stockView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,
                    parent,false);
            return new StockHolder(stockView);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading,
                    parent,false);
            return new LoadingHolder(loadingView);
        }
        return new StockHolder(parent);
    }

    @Override
    public int getItemViewType(int position) {
        int listSize = stockDTOs.size();
        if(position < listSize) {
            return VIEW_TYPE_NORMAL;
        }
        return VIEW_TYPE_LOADING;
    }


    void hideLoading() {
        isShowingLoading = false;
        int itemCount = getItemCount();
        notifyItemRemoved(itemCount);
    }
    void showLoading() {
        isShowingLoading = true;
        int itemCount = getItemCount();
        notifyItemInserted(itemCount + 1);
    }


    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        onBind(holder,position, stockDTOs.get(position));
    }

    @SuppressLint("ResourceAsColor")
    public void onBind( StockHolder holder, int position, StockDTO stockDTO) {

        holder.companyName.setText(stockDTO.companyName);
        holder.companyCode.setText(stockDTO.companyCode);

        holder.companyStock.setText((CharSequence) stockDTO.stockPrice);
        holder.stockChange.setText((CharSequence) stockDTO.priceChange);


        if (((CharSequence) stockDTO.stockPrice).charAt(0) == '-') {
            holder.stockChange.getResources().getColor(R.color.red);
        }
        else if( ((CharSequence) stockDTO.stockPrice).charAt(0) == '+') {
            holder.stockChange.getResources().getColor(R.color.green);
        }

        if (stockDTO.favouriteStatus.equals(ApplicationStatus.IS_IN_FAVOURITE)) {
            holder.favourite.setBackgroundResource(R.drawable.ic_baseline_star_24_yellow);
        }
        else if ((stockDTO.favouriteStatus).equals(ApplicationStatus.IS_NOT_IN_FAVOURITE)) {
            holder.favourite.setBackgroundResource(R.drawable.ic_baseline_star_24_grey);
        }
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stockDTO.favouriteStatus.equals(ApplicationStatus.IS_IN_FAVOURITE)) {
                    stockDTO.favouriteStatus = ApplicationStatus.IS_NOT_IN_FAVOURITE;
                    holder.favourite.setBackgroundResource(R.drawable.ic_baseline_star_24_grey);
                    stockAdapterListener.onRemovedFromFavourite(stockDTO);

                }
                else if ((stockDTO.favouriteStatus).equals(ApplicationStatus.IS_NOT_IN_FAVOURITE)) {
                    stockDTO.favouriteStatus = ApplicationStatus.IS_IN_FAVOURITE;
                    holder.favourite.setBackgroundResource(R.drawable.ic_baseline_star_24_yellow);
                    stockAdapterListener.onMarkedAsFavourite(stockDTO);
                }
            }
        });

        if ((position % 2) == 0) {
            holder.layout.setBackgroundColor(R.color.white);
        }
        else if (position % 2 != 0) {
            holder.layout.setBackgroundColor(R.color.grey_light);
        }

    }

    @Override
    public int getItemCount() {
        return stockDTOs.size();
    }


     class StockHolder extends RecyclerView.ViewHolder {

        public StockHolder(@NonNull View itemView) {
            super(itemView);

        }

        public TextView companyCode = itemView.findViewById(R.id.company_code);
        TextView companyName = itemView.findViewById(R.id.company_name);
        TextView companyStock = itemView.findViewById(R.id.price);
        TextView stockChange = itemView.findViewById(R.id.price_change);
        RelativeLayout layout = itemView.findViewById(R.id.rv_item);
        ImageButton favourite = itemView.findViewById(R.id.button_star);

    }
    class LoadingHolder extends StockHolder  {
        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
            itemView = loading;
        }
        View loading = itemView.findViewById(R.id.progressBar);
    }
}
