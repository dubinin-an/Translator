package ru.dan.translator;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

import ru.dan.translator.call.Calls;

/**
 * Created by  DubininA on 10.04.2017.
 */

public class TextEditor extends Fragment {

    private Button doTranslateBtn;
    private static EditText editText;
    private static EditText translate;
    private Context context;

    public static String getEditText() {
        return editText.getText().toString();
    }

    public static void setEditText(String editText) {
        TextEditor.editText.setText(editText);
    }

    public static String getTranslate() {
        return translate.getText().toString();
    }

    public static void setTranslate(String translate) {
        TextEditor.translate.setText(translate);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor, container, false);
        context = getActivity().getApplicationContext();


        editText = (EditText) view.findViewById(R.id.editText);
        doTranslateBtn = (Button) view.findViewById(R.id.doTranslateBtn);
        translate = (EditText) view.findViewById(R.id.translate);

        doTranslateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calls calls = new Calls(context);
                calls.getTranslate();
                LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                        new IntentFilter("YT_GETTRANSLATE"));
            }
        });


        return view;
    }

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.hasExtra("GETTRANSLATE")){
                    translate.setText(intent.getStringExtra("GETTRANSLATE"));
                    Log.d("happy", "TEXTEDITOR: " + intent.getStringExtra("GETTRANSLATE"));
                } else Log.e("happy", "NO GETTRANSLATE");
            }else Log.e("happy", "NO INTENT");
        }

    };

    public static String getText(){
        return editText.getText().toString();
    }
}
