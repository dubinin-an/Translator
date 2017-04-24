package ru.dan.translator.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    LocalBroadcastManager localBroadcastManager;
    boolean isYDLang;
    BroadcastReceiver ydLangMessageReceiver;
    YDapi ydAPI;

    public static final String YD_GETLANG = "YD_GETLANG";

    String rText = "";
    private String lang;
    private String text;


    //TODO WeakReference

    public Calls(Context context) {
        this.context = new WeakReference<Context>(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        ydAPI = new RetrofitConnector().getYD();


        ydLangMessageReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(final Context cont, Intent intent) {
                if(intent != null){
                    boolean l = intent.getBooleanExtra("YDGETLANG", false);
                    if(l){
                        rText = "";
                        Log.d("happy","Язык словаря совпал с языком перевода");
                        Callback<YDLookup> respYDLCallBack = new Callback<YDLookup>() {
                            @Override
                            public void onResponse(Call<YDLookup> call, Response<YDLookup> response) {
                                if (response != null) {
//                                    Log.d("happy","Успешный ответ от словаря: "+call.request().toString());
                                    Log.d("happy","Успешный ответ от словаря.");
                                    List<Def> defList = response.body().getDef();
                                    for (Def d : defList) {
//                                        rText = rText.concat(d.getText().concat("\n"));
//                            Log.d("happy", d.getText());
                                        List<Tr> trList = d.getTr();
                                        for (Tr tr : trList) {
                                            rText = rText.concat(tr.getText().concat("\n"));
                                        }
                                    }

//                                    Log.d("happy", "getLookup: " + rText);

                                    Intent intent = new Intent("YD_GETSIN");
                                    intent.putExtra("GETSIN", rText);

                                    localBroadcastManager.sendBroadcast(intent);


                                }
                            }

                            @Override
                            public void onFailure(Call<YDLookup> call, Throwable t) {
                                Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
                            }
                        };

                        ydAPI.yd_Lookup(MainActivity.DICT_API_KEY, lang, text).enqueue(respYDLCallBack);


                    }
                } else Log.d("happy", "YD Intent NOOL");

            }
        };
//        localBroadcastManager.unregisterReceiver(ydLangMessageReceiver);

    }

    public void unregBroadcastReceivers(){
        Log.d("happy","Отрегистрировали слушатель возврата языков синонимов");
        localBroadcastManager.unregisterReceiver(ydLangMessageReceiver);
    }

    public void isLangYD(final String lang){
        isYDLang = false;
        Callback<List<String>> respLangListCallBack = new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Log.d("happy","Dictionary Languagelist success");
                if(response != null){
                    if(response.body().contains(lang)) {
                        isYDLang = true;
                        Log.d("happy", "lang  "  + lang);
                    }
                }

                Intent intent = new Intent(YD_GETLANG);
                intent.putExtra("YDGETLANG", isYDLang);

                Log.d("happy","Обратный вызов со списком языков синонимов");
                localBroadcastManager.sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        };

        ydAPI.yd_getLangs(MainActivity.DICT_API_KEY).enqueue(respLangListCallBack);
    }

    public void getLookup(final String lang, final String text){
        this.lang = lang;
        this.text = text;
        Log.d("happy","Пришли в getLookup");
//        ydLangMessageReceiver = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(final Context cont, Intent intent) {
//                if(intent != null){
//                    boolean l = intent.getBooleanExtra("YDGETLANG", false);
//                    if(l){
//                        rText = "";
//                        Log.d("happy","Язык словаря совпал с языком перевода");
//                        Callback<YDLookup> respYDLCallBack = new Callback<YDLookup>() {
//                            @Override
//                            public void onResponse(Call<YDLookup> call, Response<YDLookup> response) {
//                                if (response != null) {
////                                    Log.d("happy","Успешный ответ от словаря: "+call.request().toString());
//                                    Log.d("happy","Успешный ответ от словаря.");
//                                    List<Def> defList = response.body().getDef();
//                                    for (Def d : defList) {
////                                        rText = rText.concat(d.getText().concat("\n"));
////                            Log.d("happy", d.getText());
//                                        List<Tr> trList = d.getTr();
//                                        for (Tr tr : trList) {
//                                            rText = rText.concat(tr.getText().concat("\n"));
//                                        }
//                                    }
//
////                                    Log.d("happy", "getLookup: " + rText);
//
//                                    Intent intent = new Intent("YD_GETSIN");
//                                    intent.putExtra("GETSIN", rText);
//
//                                    localBroadcastManager.sendBroadcast(intent);
//
//
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<YDLookup> call, Throwable t) {
//                                Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
//                            }
//                        };
//
//                        ydAPI.yd_Lookup(MainActivity.DICT_API_KEY, lang, text).enqueue(respYDLCallBack);
//
//
//                    }
//                } else Log.d("happy", "YD Intent NOOL");
//
//            }
//        };


//        localBroadcastManager.unregisterReceiver(ydLangMessageReceiver);
//        Log.d("happy","Зарегистрировали слушатель возврата языков синонимов");
//        localBroadcastManager.registerReceiver(ydLangMessageReceiver,new IntentFilter(YD_GETLANG));
        isLangYD(lang);
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

                    localBroadcastManager.sendBroadcast(intent);
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
                Log.d("happy","We have an success translate!:"+response.body().getFormatText());

                getLookup(to+"-"+to, response.body().getFormatText());

//                Log.d("happy", response.body().getText().toString());


                Intent intent = new Intent("YT_GETTRANSLATE");
                intent.putExtra("GETTRANSLATE", response.body().getFormatText());

                localBroadcastManager.sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<TranslateReply> call, Throwable t) {
                Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
            }
        };

        translate.enqueue(replyTranslateCallBack);
    }

    public void regBroadcastReceivers() {
        Log.d("happy","Зарегистрировали слушатель возврата языков синонимов");
        localBroadcastManager.registerReceiver(ydLangMessageReceiver,new IntentFilter(YD_GETLANG));
    }

}

