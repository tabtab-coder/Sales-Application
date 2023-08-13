package com.example.androidlayouts;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.translate.Language;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;

import static android.widget.AdapterView.OnItemSelectedListener;

public class LanguageSelectionActivity extends AppCompatActivity implements OnItemSelectedListener {

  private Spinner languages;
  private Button back;
  private Language language = Language.ENGLISH;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_language_selection);

    languages = findViewById(R.id.languageSpinner);
    back = findViewById(R.id.bBackLanguages);
    TextView titleView = findViewById(R.id.tvLanguageHeader);

    Translator translator = new Translator(LanguageSelectionActivity.this);
    back.setText(translator
        .translate(back.getText().toString(), LanguageSingleton.getInstance().getValue()));
    titleView.setText(translator
        .translate(titleView.getText().toString(), LanguageSingleton.getInstance().getValue()));

    String[] languageList = getResources().getStringArray(R.array.languages);
    int i = 0;
    while (i < languageList.length) {
      switch (languageList[i]) {
        case "Chinese_SM":
          languageList[i] = capitalizeFirstLetter(translator.translate("Chinese Simplified",
              LanguageSingleton.getInstance().getValue())) + " (" + capitalizeFirstLetter(
              translator.translate("Chinese Simplified",
              Language.getEnum(languageList[i]))) + ")";
          break;
        case "Chinese_TR":
          languageList[i] = capitalizeFirstLetter(translator.translate("Chinese Traditional",
              LanguageSingleton.getInstance().getValue())) + " (" + capitalizeFirstLetter(
              translator.translate("Chinese Traditional",
              Language.getEnum(languageList[i]))) + ")";
          break;
        case "Haitian_Creole":
          languageList[i] = capitalizeFirstLetter(translator.translate("Haitian Creole",
              LanguageSingleton.getInstance().getValue()))  + " (" + capitalizeFirstLetter(
              translator.translate("Haitian Creole",
              Language.getEnum(languageList[i]))) + ")";
          break;
        default:
          languageList[i] = capitalizeFirstLetter(translator.translate(languageList[i],
              LanguageSingleton.getInstance().getValue())) + " (" + capitalizeFirstLetter(
              translator.translate(languageList[i],
              Language.getEnum(languageList[i]))) + ")";
      }
      i++;
    }
    ArrayAdapter<String> readUserType = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, languageList);
    readUserType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    languages.setAdapter(readUserType);
    languages.setOnItemSelectedListener(this);
    languages.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            ((TextView) languages.getSelectedView()).setTextColor(Color.WHITE);
          }
        });
    back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
        return;
      }
    });
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String[] languageList = getResources().getStringArray(R.array.languages);
    language = Language.getEnum(languageList[position]);
    LanguageSingleton.getInstance().setValue(language);
    return;
  }

  private String capitalizeFirstLetter(String original) {
    if (original == null || original.length() == 0) {
      return original;
    }
    return original.substring(0, 1).toUpperCase() + original.substring(1);
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    language = Language.ENGLISH;
  }
}
