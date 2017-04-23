package ru.dan.translator.call;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dan.translator.MainActivity;
import ru.dan.translator.RetrofitConnector;
import ru.dan.translator.response.Def;
import ru.dan.translator.response.GetLangsReply;
import ru.dan.translator.response.Tr;
import ru.dan.translator.response.TranslateReply;
import ru.dan.translator.response.YDLookup;

/**
 * Created by  DubininA on 09.04.2017.
 */

public class Calls{
    WeakReference<Context> context;
    //TODO WeakReference

    public Calls(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    public String getLangYD(){
        YDapi ydAPI = new RetrofitConnector().getYD();

        Call

        return "";
    }

    public void getLookup(String lang, String text){
        YDapi ydAPI = new RetrofitConnector().getYD();

        Call<YDLookup> ydl = ydAPI.yd_Lookup(MainActivity.DICT_API_KEY, lang, text);

        Callback<YDLookup> respYDLCallBack = new Callback<YDLookup>() {
            @Override
            public void onResponse(Call<YDLookup> call, Response<YDLookup> response) {
                if(response != null){
                    List<Def> defList = response.body().getDef();
                    for(Def d : defList){
                        Log.d("happy", d.getText());
                        List<Tr> trList = d.getTr();
                        for(Tr tr :trList){
                            Log.d("happy", tr.getText());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<YDLookup> call, Throwable t) {
                Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
            }
        };
        ydl.enqueue(respYDLCallBack);
    }


    public void getLangs() {
        YTapi ytAPI = new RetrofitConnector().getYT();

        Call<GetLangsReply> langs = ytAPI.yt_getLangs("ru", MainActivity.TRANS_API_KEY);

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


    public void getTranslate(String API_KEY, String from, final String to, String origText){
        YTapi ytAPI = new RetrofitConnector().getYT();

        Call<TranslateReply> translate = ytAPI.yt_translate(API_KEY, from + "-" + to, origText);

        Callback<TranslateReply> replyTranslateCallBack = new Callback<TranslateReply>() {
            @Override
            public void onResponse(Call<TranslateReply> call, Response<TranslateReply> response) {

                getLookup(to+"-"+to, response.body().getFormatText());
//                Log.d("happy", response.body().getText().toString());


//                Intent intent = new Intent("YT_GETTRANSLATE");
//                intent.putExtra("GETTRANSLATE", response.body().getFormatText());
//                Context c = context.get();
//                if (c != null)
//                    LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<TranslateReply> call, Throwable t) {
                Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
            }
        };

        translate.enqueue(replyTranslateCallBack);
    }

}

