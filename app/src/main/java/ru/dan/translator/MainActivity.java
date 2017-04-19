package ru.dan.translator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dan.translator.call.Calls;
import ru.dan.translator.call.YTapi;
import ru.dan.translator.response.GetLangsReply;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "trnsl.1.1.20170406T164438Z.7321da0631eb77e3.cbb65bd1f1448642316f595b9678855ad4507505";
    private List<String> LangsList;
    public static final String APP_PREFERENCES = "mysettings";
    public static SharedPreferences mSettings;
    public static SharedPreferences.Editor editor;


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
