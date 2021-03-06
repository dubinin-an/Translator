package ru.dan.translator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String TRANS_API_KEY = "trnsl.1.1.20170406T164438Z.7321da0631eb77e3.cbb65bd1f1448642316f595b9678855ad4507505";
    public static final String DICT_API_KEY = "dict.1.1.20170421T184614Z.863afbae8ccefc34.f7131ec90976272bbc1e9c85599378e9bf7e1369";
    private static final int RESULT_FROM_HISTORY = 666;
    public static final String TRANSLATION_FROM_HISTORY = "TRANSLATION_FROM_HISTORY";

    private List<String> LangsList;
    public static final String APP_PREFERENCES = "mysettings";
    public static SharedPreferences mSettings;
    public static SharedPreferences.Editor editor;
    public TranslateObj historyTranslateObj;


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
                startActivityForResult(h, RESULT_FROM_HISTORY);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_FROM_HISTORY){
            if (resultCode == RESULT_OK){
                if (data.hasExtra(TRANSLATION_FROM_HISTORY)) {
                    TranslateObj t = (TranslateObj) data.getSerializableExtra(TRANSLATION_FROM_HISTORY);
                    Log.d("happy","TO:"+t.getOrigText());
                    historyTranslateObj = t;
//                    Translator f_translator = (Translator) findViewById(R.id.f_translator);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
