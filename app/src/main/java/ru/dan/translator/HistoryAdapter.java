package ru.dan.translator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by  DubininA on 20.04.2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private List<TranslateObj> historyList;

    public HistoryAdapter(List<TranslateObj> historyList) {
        this.historyList = historyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_listviewitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TranslateObj t = historyList.get(position);
        holder.ot.setText(t.getOrigText());
        holder.tt.setText(t.getTranslateText());
        if(t.isFavorite()){
            holder.fav.setImageResource(android.R.drawable.btn_star_big_on);
        }else{
            holder.fav.setImageResource(android.R.drawable.btn_star_big_off);
        }

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ot;
        private TextView tt;
        private ImageView fav;

        public ViewHolder(View itemView) {
            super(itemView);

            ot = (TextView) itemView.findViewById(R.id.ot);
            tt = (TextView) itemView.findViewById(R.id.tt);
            fav = (ImageView) itemView.findViewById(R.id.fav);

        }
    }
}
