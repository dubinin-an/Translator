package ru.dan.translator;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dan.translator.call.YDapi;
import ru.dan.translator.call.YTapi;

/**
 * Created by  DubininA on 09.04.2017.
 */

public class RetrofitConnector {

    private static final String YT_URL = "https://translate.yandex.net";
    private static final String YD_URL = "https://dictionary.yandex.net";

    public RetrofitConnector() {
    }

    public YDapi getYD(){
        Retrofit retrofitYT = new Retrofit.Builder()
                .baseUrl(YD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitYT.create(YDapi.class);
    }

    public YTapi getYT(){
        Retrofit retrofitYT = new Retrofit.Builder()
                .baseUrl(YT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitYT.create(YTapi.class);
    }

}
