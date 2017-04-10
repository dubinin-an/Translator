package ru.dan.translator;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dan.translator.call.YTapi;

/**
 * Created by  DubininA on 09.04.2017.
 */

public class RetrofitConnector {

    private  YTapi yt;
    private static final String YT_URL = "https://translate.yandex.net";

    public RetrofitConnector() {
        Retrofit retrofitYT = new Retrofit.Builder()
                .baseUrl(YT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        yt = retrofitYT.create(YTapi.class);

    }

    public YTapi getYT(){
        return yt;
    }

}
