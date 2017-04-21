package ru.dan.translator;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.dan.translator.call.Calls;

/**
 * Created by  DubininA on 20.04.2017.
 */

public class Translator extends Fragment{

    private Spinner langFrom, langTo;
    private ImageButton changeDirs;
    private Button doTranslateBtn;
    private EditText origText;
    private EditText translate;
    private ImageView faView;

    TranslateObj translateObj;
    private HashMap<String,String> langs;
    private static String from;
    private static String to;
    private List<String> key,val;
    private Context context;
    ArrayAdapter adapter;
    private long curId;
    private DBHelper dbHelper;
    public static final String APP_PREFERENCES_FROM = "APP_PREFERENCES_FROM", APP_PREFERENCES_TO = "APP_PREFERENCES_TO";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translator, container, false);
        context = getActivity().getApplicationContext();

        dbHelper = new DBHelper(context);

        langFrom = (Spinner) view.findViewById(R.id.langFrom);
        langTo = (Spinner) view.findViewById(R.id.langTo);
        changeDirs = (ImageButton) view.findViewById(R.id.changeDirs);
        origText = (EditText) view.findViewById(R.id.origText);
        doTranslateBtn = (Button) view.findViewById(R.id.doTranslateBtn);
        translate = (EditText) view.findViewById(R.id.translate);
        faView = (ImageView) view.findViewById(R.id.faView);

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
            LocalBroadcastManager.getInstance(context).registerReceiver(langMessageReceiver,
                    new IntentFilter("YT_GETLANGS"));
        };

        doTranslateBtn.setOnClickListener(btnClick_doTranslateBtn);

        changeDirs.setOnClickListener(btnClick_changeDirs);

        faView.setOnClickListener(btnClick_setFav);

        return view;
    }


    BroadcastReceiver translateMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.hasExtra("GETTRANSLATE")){
                    translateObj.setTranslateText(intent.getStringExtra("GETTRANSLATE"));
                    translate.setText(translateObj.getTranslateText());
                    faView.setImageResource(android.R.drawable.btn_star_big_off);
//                    Log.d("happy", "TEXTEDITOR: " + translateObj.getTranslateText());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.COLUMN_ORIGLANG, translateObj.getOrigLang());
                    contentValues.put(DBHelper.COLUMN_ORIGTEXT, translateObj.getOrigText());
                    contentValues.put(DBHelper.COLUMN_TRANSLATELANG, translateObj.getTranslateLang());
                    contentValues.put(DBHelper.COLUMN_TRANSLATETEXT, translateObj.getTranslateText());
                    contentValues.put(DBHelper.COLUMN_FAV, Boolean.toString(translateObj.isFavorite()));

                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    if ( database.query(DBHelper.TABLE_HISTORY,null,
                            DBHelper.COLUMN_ORIGLANG + "=? and " +
                            DBHelper.COLUMN_ORIGTEXT + "=? and " +
                            DBHelper.COLUMN_TRANSLATELANG + "=? and " +
                            DBHelper.COLUMN_TRANSLATETEXT + "=? ",
                            new String[]{translateObj.getOrigLang(),translateObj.getOrigText(),translateObj.getTranslateLang(),translateObj.getTranslateText()},
                            null,null,null).getCount()
                            == 0) {
                        translateObj.setId(database.insert(DBHelper.TABLE_HISTORY, null, contentValues));
                    }
                    database.close();

                } else Log.e("happy", "NO GETTRANSLATE");
            }else Log.e("happy", "NO INTENT");
        }

    };

    BroadcastReceiver langMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                langs = (HashMap<String, String>) intent.getSerializableExtra("GETLANGS");
                val = new ArrayList<>(langs.values());
                key = new ArrayList<>(langs.keySet());

                spinerSet();
            }
        }

    };

    View.OnClickListener btnClick_setFav = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String f;
            if (translateObj.isFavorite()){
                faView.setImageResource(android.R.drawable.btn_star_big_off);
                translateObj.setFavorite(false);
            } else {
                faView.setImageResource(android.R.drawable.btn_star_big_on);
                translateObj.setFavorite(true);
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.COLUMN_FAV, Boolean.toString(translateObj.isFavorite()));
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.update(DBHelper.TABLE_HISTORY, contentValues, DBHelper.COLUMN_ID + "=?", new String[] {Long.toString(translateObj.getId())} );
            database.close();


        }
    };

    View.OnClickListener btnClick_doTranslateBtn = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            translateObj = new TranslateObj();
            translateObj.setOrigLang(from);
            translateObj.setTranslateLang(to);
            translateObj.setOrigText(getOrigText());

            Calls calls = new Calls(context);
            calls.getTranslate(MainActivity.API_KEY, from + "-" + to, getOrigText());
            LocalBroadcastManager.getInstance(context).registerReceiver(translateMessageReceiver,
                    new IntentFilter("YT_GETTRANSLATE"));


        }
    };



        View.OnClickListener btnClick_changeDirs = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int t = langFrom.getSelectedItemPosition();
            langFrom.setSelection(langTo.getSelectedItemPosition());
            langTo.setSelection(t);

            saveCurentLangs();

            String tmp = getOrigText();
            setOrigText(getTranslate());
            setTranslate(tmp);
        }
    };

    AdapterView.OnItemSelectedListener fromItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            from = key.get(position);
            saveCurentLangs();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener toItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            to = key.get(position);
            saveCurentLangs();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void spinerSet(){
        adapter = new ArrayAdapter(context, R.layout.lang_item, val);
        adapter.setDropDownViewResource(R.layout.lang_item);
        langFrom.setAdapter(adapter);
        langTo.setAdapter(adapter);
        langFrom.setOnItemSelectedListener(fromItemSelectedListener);
        langTo.setOnItemSelectedListener(toItemSelectedListener);

        if(MainActivity.mSettings.contains(APP_PREFERENCES_FROM) && MainActivity.mSettings.contains(APP_PREFERENCES_TO)) {
            langFrom.setSelection(MainActivity.mSettings.getInt(APP_PREFERENCES_FROM, 0));
            langTo.setSelection(MainActivity.mSettings.getInt(APP_PREFERENCES_TO, 0));
        }
    }

    @Override
    public void onPause() {
        saveCurentLangs();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("LANGS", langs);
    }

    public void saveCurentLangs(){
        MainActivity.editor.putInt(APP_PREFERENCES_FROM, langFrom.getSelectedItemPosition());
        MainActivity.editor.putInt(APP_PREFERENCES_TO, langTo.getSelectedItemPosition());
        MainActivity.editor.apply();
    }

    public String getOrigText() {
        return origText.getText().toString();
    }
    public void setOrigText(String origText) {
        this.origText.setText(origText);
    }
    public String getTranslate() {
        return this.translate.getText().toString();
    }
    public void setTranslate(String translate) {
        this.translate.setText(translate);
    }

}
