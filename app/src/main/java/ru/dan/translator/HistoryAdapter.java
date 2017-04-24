package ru.dan.translator;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static ru.dan.translator.R.id.faView;

/**
 * Created by  DubininA on 20.04.2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    public List<TranslateObj> historyList;
    private Activity context;

    public HistoryAdapter(List<TranslateObj> historyList, Activity context) {
        this.historyList = historyList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_listviewitem, parent, false);
        return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TranslateObj t = historyList.get(position);
        holder.ot.setText(t.getOrigText());
        holder.tt.setText(t.getTranslateText());
        holder.from.setText(t.getOrigLang());
        holder.to.setText(t.getTranslateLang());
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

    private void invertFav(int adapterPosition) {
        TranslateObj to = historyList.get(adapterPosition);
            if (to.isFavorite()){
                to.setFavorite(false);
            } else {
                to.setFavorite(true);
            }
            DBHelper dbHelper = new DBHelper(context);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.COLUMN_FAV, Boolean.toString(to.isFavorite()));
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.update(DBHelper.TABLE_HISTORY, contentValues, DBHelper.COLUMN_ID + "=?", new String[] {Long.toString(to.getId())} );
            database.close();
            notifyItemChanged(adapterPosition);

    }

    private void sendItemToTranslator(TranslateObj t) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.TRANSLATION_FROM_HISTORY,t);

        context.setResult(RESULT_OK, intent);
        context.finish();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final HistoryAdapter adapter;
        private TextView from;
        private TextView to;
        private TextView ot;
        private TextView tt;
        private ImageView fav;

        public ViewHolder(View itemView, final HistoryAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            from = (TextView) itemView.findViewById(R.id.from);
            to = (TextView) itemView.findViewById(R.id.to);
            ot = (TextView) itemView.findViewById(R.id.ot);
            tt = (TextView) itemView.findViewById(R.id.tt);
            fav = (ImageView) itemView.findViewById(R.id.fav);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        adapter.invertFav(getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        TranslateObj t = adapter.historyList.get(getAdapterPosition());
                        adapter.sendItemToTranslator(t);
                    }
                }
            });



        }
    }



}
