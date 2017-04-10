package ru.dan.translator;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by  DubininA on 10.04.2017.
 */

public class TextEditor extends Fragment {

    private Button doTranslateBtn;
    private EditText editText;
    private EditText translate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor, container, false);

        editText = (EditText) view.findViewById(R.id.editText);
        doTranslateBtn = (Button) view.findViewById(R.id.doTranslateBtn);
        translate = (EditText) view.findViewById(R.id.translate);


        return view;
    }
}
