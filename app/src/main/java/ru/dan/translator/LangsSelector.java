package ru.dan.translator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * Created by  DubininA on 10.04.2017.
 */

public class LangsSelector extends Fragment {

    private Spinner langFrom, langTo;
    private ImageButton changeDirs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.langs_selector,container,false);

        langFrom = (Spinner) view.findViewById(R.id.langFrom);
        langTo = (Spinner) view.findViewById(R.id.langTo);
        changeDirs = (ImageButton) view.findViewById(R.id.changeDirs);

        return view;
    }
}
