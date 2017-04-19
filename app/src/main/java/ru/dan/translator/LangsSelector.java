package ru.dan.translator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.dan.translator.call.Calls;

/**
 * Created by  DubininA on 10.04.2017.
 */

public class LangsSelector extends Fragment {

    private Spinner langFrom, langTo;
    private ImageButton changeDirs;
    private HashMap<String,String> langs;
    private String dirs;
    private static String from;
    private static String to;
    private List<String>  key,val;
    private Context context;
    ArrayAdapter adapter;

    public static final String APP_PREFERENCES_FROM = "APP_PREFERENCES_FROM", APP_PREFERENCES_TO = "APP_PREFERENCES_TO";

    public static String getDirs() {
//        Log.d("happy", "LangSelector: from: " + from + " - to: " + to);

        return from + "-" + to;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt("FROM", langFrom.getSelectedItemPosition());
//        outState.putInt("TO", langTo.getSelectedItemPosition());
        outState.putSerializable("LANGS", langs);


    }

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int t = langFrom.getSelectedItemPosition();
            langFrom.setSelection(langTo.getSelectedItemPosition());
            langTo.setSelection(t);

            saveCurentLangs();

            String tmp = TextEditor.getEditText();
            TextEditor.setEditText(TextEditor.getTranslate());
            TextEditor.setTranslate(tmp);

        }
    };

    OnItemSelectedListener fromItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            from = key.get(position);
            saveCurentLangs();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    OnItemSelectedListener toItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            to = key.get(position);
            saveCurentLangs();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
//                    Log.d("happy","LangsSelector");
                langs = (HashMap<String, String>) intent.getSerializableExtra("GETLANGS");
                val = new ArrayList<>(langs.values());
                key = new ArrayList<>(langs.keySet());
//                    Log.d("happy", val.toString());
                // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
                spinerSet();
            }
        }

    };

    private void spinerSet(){

        adapter = new ArrayAdapter(context, R.layout.lang_item, val);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(R.layout.lang_item);
        // Применяем адаптер к элементу spinner
        langFrom.setAdapter(adapter);
        langTo.setAdapter(adapter);
        langFrom.setOnItemSelectedListener(fromItemSelectedListener);
        langTo.setOnItemSelectedListener(toItemSelectedListener);

        if(MainActivity.mSettings.contains(APP_PREFERENCES_FROM) && MainActivity.mSettings.contains(APP_PREFERENCES_TO)) {
            langTo.setSelection(MainActivity.mSettings.getInt(APP_PREFERENCES_FROM, 0));
            langFrom.setSelection(MainActivity.mSettings.getInt(APP_PREFERENCES_TO, 0));
        }
    }

    @Override
    public void onPause() {
        saveCurentLangs();
        super.onPause();
    }

    public void saveCurentLangs(){
        MainActivity.editor.putInt(APP_PREFERENCES_FROM, langFrom.getSelectedItemPosition());
        MainActivity.editor.putInt(APP_PREFERENCES_TO, langTo.getSelectedItemPosition());
        MainActivity.editor.apply();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.langs_selector, container, false);

        context = getActivity().getApplicationContext();

        langFrom = (Spinner) view.findViewById(R.id.langFrom);
        langTo = (Spinner) view.findViewById(R.id.langTo);
        changeDirs = (ImageButton) view.findViewById(R.id.changeDirs);


        if (savedInstanceState != null){
            if (savedInstanceState.containsKey("LANGS")){
                langs = (HashMap<String,String>) savedInstanceState.getSerializable("LANGS");
                val = new ArrayList<>(langs.values());
                key = new ArrayList<>(langs.keySet());

                spinerSet();


            }else Log.d("happy","Err2");
        }else {
            Calls calls = new Calls(context);
            calls.getLangs();
            LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                    new IntentFilter("YT_GETLANGS"));
        };




        changeDirs.setOnClickListener(btnClick);

        return view;

        };

    };
