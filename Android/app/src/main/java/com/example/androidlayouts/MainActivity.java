package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import java.io.File;

public class MainActivity extends AppCompatActivity {

  private Button startBtn;
  private Button language;
  private Intent intent = null;
  private String title;
  private TextView titleView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    startBtn = findViewById(R.id.bOpt1Main);
    language = findViewById(R.id.bLanguage);
    titleView = findViewById(R.id.etWelcome);
    title = titleView.getText().toString();
    Translator translator = new Translator(MainActivity.this);
    startBtn.setText(translator
        .translate(startBtn.getText().toString(), LanguageSingleton.getInstance().getValue()));
    language.setText(translator
        .translate(language.getText().toString(), LanguageSingleton.getInstance().getValue()));
    title = translator.translate(title, LanguageSingleton.getInstance().getValue());
    titleView.setText(title);

    startBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        File database = getApplicationContext().getDatabasePath("inventorymgmt.db");
        if (database == null || !database.exists()) {
          intent = new Intent(MainActivity.this, InitializationDatabaseActivity.class);
          startActivity(intent);
        } else {
          intent = new Intent(MainActivity.this, LoginPromptActivity.class);
          startActivity(intent);
        }
      }
    });

    language.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        intent = new Intent(MainActivity.this, LanguageSelectionActivity.class);
        startActivityForResult(intent, 1);
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      recreate();
    }
  }
}