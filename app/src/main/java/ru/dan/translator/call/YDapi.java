package ru.dan.translator.call;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.dan.translator.response.YDLookup;

/**
 * Created by home on 23.04.2017.
 */

public interface YDapi {

    @POST("/api/v1/dicservice.json/getLangs")
    Call<List<String>> yd_getLangs(@Query("key") String apiKey);


    @POST("/api/v1/dicservice.json/lookup")
    Call<YDLookup> yd_Lookup(@Query("key") String apiKey, @Query("lang") String lang, @Query("text") String text);
}
