package ru.dan.translator.call;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dan.translator.MainActivity;
import ru.dan.translator.RetrofitConnector;
import ru.dan.translator.response.GetLangsReply;

import static android.R.id.list;

/**
 * Created by  DubininA on 09.04.2017.
 */

public class Calls{
    YTapi ytAPI;
    WeakReference<MainActivity> activity;
    //TODO WeakReference

    public Calls(MainActivity mainActivity) {
        activity = new WeakReference<MainActivity>(mainActivity);
        ytAPI = new RetrofitConnector().getYT();
    }

    public List<String> getLangs() {
        final List<String> list = new ArrayList<>();
        Call<GetLangsReply> langs = ytAPI.yt_getLangs("ru", MainActivity.API_KEY);

        Callback<GetLangsReply> respCallBack = new Callback<GetLangsReply>() {
            @Override
            public void onResponse(Call<GetLangsReply> call, Response<GetLangsReply> response) {
                if (response != null) {
//                    list.addAll(response.body().getLangsValues());
                    Log.d("happy", "CALLS: " + response.body().getLangsValues());
                    MainActivity a = activity.get();
                    if (a != null) {
                        a.setLangsList(response.body().getLangsValues());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetLangsReply> call, Throwable t) {
                Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
            }
        };

//        langs.enqueue(new GetLangsResponce(activity));
        langs.enqueue(respCallBack);

        return list;
    }







//    private class GetLangsResponce implements Callback<GetLangsReply> {
//        private OnGetLangsList listner;
//
//        public GetLangsResponce(OnGetLangsList listner){
//            this.listner = listner;
//        }
//        @Override
//        public void onResponse(Call<GetLangsReply> call, Response<GetLangsReply> response) {
//            if (response != null) {
//                List<String> list = response.body().getLangsValues();
//                Log.d("happy", "CALLS: " + response.body().getLangsValues());
//                listner.setLangsList(list);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GetLangsReply> call, Throwable t) {
//            Log.e("happy", "Error: " + call.request().toString() + "   *********   " + t.getMessage());
//        }
//
//    }
//    public interface OnGetLangsList {
//        void setLangsList(List<String> list);
//    }
}

