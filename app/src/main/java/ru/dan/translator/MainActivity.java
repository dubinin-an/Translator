package ru.dan.translator;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Calls calls = new Calls(this);
//        calls.getLangs();


    }

//    @Override
    public void setLangsList(List<String> list) {
        this.LangsList = list;
        Log.d("happy", "MA" + list);
    }
}
