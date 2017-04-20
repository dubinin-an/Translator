package ru.dan.translator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "trnsl.1.1.20170406T164438Z.7321da0631eb77e3.cbb65bd1f1448642316f595b9678855ad4507505";
    private List<String> LangsList;
    public static final String APP_PREFERENCES = "mysettings";
    public static SharedPreferences mSettings;
    public static SharedPreferences.Editor editor;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_history: {
                Intent h = new Intent(this, History.class);
                startActivity(h);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        editor = mSettings.edit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
