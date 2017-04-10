package ru.dan.translator.call;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.dan.translator.response.GetLangsReply;

/**
 * Created by  DubininA on 07.04.2017.
 */

public interface YTapi {

    @POST("/api/v1.5/tr.json/getLangs")
    Call<GetLangsReply> yt_getLangs(@Query("ui") String UserLocale, @Query("key") String apiKey);



}
