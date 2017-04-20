package ru.dan.translator.call;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dan.translator.MainActivity;
import ru.dan.translator.RetrofitConnector;
import ru.dan.translator.Translator;
import ru.dan.translator.response.GetLangsReply;
import ru.dan.translator.response.TranslateReply;

/**
 * Created by  DubininA on 09.04.2017.
 */

public class Calls{
    YTapi ytAPI;
    WeakReference<Context> context;
    //TODO WeakReference

    public Calls(Context context) {
        this.context = new WeakReference<Context>(context);
        ytAPI = new RetrofitConnector().getYT();
    }

    public void getLangs() {
        Call<GetLangsReply> langs = ytAPI.yt_getLangs("ru", MainActivity.API_KEY);

        Callback<GetLangsReply> respLangsCallBack = new Callback<GetLangsReply>() {
            @Override
            public void onResponse(Call<GetLangsReply> call, Response<GetLangsReply> response) {
                if (response != null) {
                    Intent intent = new Intent("YT_GETLANGS");
                    intent.putExtra("GETLANGS", response.body().getLangs());

                    Context c = context.get();
                    if (c != null)
                        LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<GetLangsReply> call, Throwable t) {
                Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
            }
        };

        langs.enqueue(respLangsCallBack);

    }


    public void getTranslate(String API_KEY, String dirs, String origText){
        Call<TranslateReply> translate = ytAPI.yt_translate(API_KEY, dirs, origText);

        Callback<TranslateReply> replyTranslateCallBack = new Callback<TranslateReply>() {
            @Override
            public void onResponse(Call<TranslateReply> call, Response<TranslateReply> response) {
//                Log.d("happy", response.body().getText().toString());
                Intent intent = new Intent("YT_GETTRANSLATE");
                intent.putExtra("GETTRANSLATE", response.body().getFormatText());
                Context c = context.get();
                if (c != null)
                    LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<TranslateReply> call, Throwable t) {
                Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
            }
        };

        translate.enqueue(replyTranslateCallBack);
    }

}

