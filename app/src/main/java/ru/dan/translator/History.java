package ru.dan.translator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter historyAdapter;
    private RecyclerView.LayoutManager historyLayoutManager;
    private List<TranslateObj> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_HISTORY, null, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            TranslateObj to = new TranslateObj();
            to.setId(cursor.getLong(
                    cursor.getColumnIndex(DBHelper.COLUMN_ID)
            ));
            to.setOrigLang(cursor.getString(
                    cursor.getColumnIndex(DBHelper.COLUMN_ORIGLANG)
            ));
            to.setOrigText(cursor.getString(
                    cursor.getColumnIndex(DBHelper.COLUMN_ORIGTEXT)
            ));
            to.setTranslateLang(cursor.getString(
                    cursor.getColumnIndex(DBHelper.COLUMN_TRANSLATELANG)
            ));
            to.setTranslateText(cursor.getString(
                    cursor.getColumnIndex(DBHelper.COLUMN_TRANSLATETEXT)
            ));
            if(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_FAV)).equals("true")){
                to.setFavorite(true);
            }else{
                to.setFavorite(false);
            }

            historyList.add(to);

        }
        database.close();

        historyRecyclerView = (RecyclerView) findViewById(R.id.history_recycle);

        historyLayoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(historyLayoutManager);

        historyAdapter = new HistoryAdapter(historyList);
        historyRecyclerView.setAdapter(historyAdapter);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_translate: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
