package com.example.androidlayouts;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.translate.Language;
import com.b07.translate.LanguageSingleton;

import static android.widget.AdapterView.OnItemSelectedListener;

public class ChooseLanguage extends AppCompatActivity implements OnItemSelectedListener {


    private Spinner chooseLanguage;
    private Button done;
    private Language language = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.FILLINWITHCORRECTINTERFACE);

        chooseLanguage = (Spinner) findViewById(R.id.FILLINWITHCORRECTID);
        done = (Button) findViewById(R.id.FILLINWITHCORRECTID);


        ArrayAdapter<String> readUserType = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.chooseLanguage));
        readUserType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseLanguage.setAdapter(readUserType);
        chooseLanguage.setOnItemSelectedListener(this);
        chooseLanguage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) chooseLanguage.getSelectedView()).setTextColor(Color.WHITE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });*/

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        language = Language.getEnum(parent.getItemAtPosition(position).toString());
        LanguageSingleton.getInstance().setValue(language);
        return;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        language = Language.ENGLISH;
    }
}
