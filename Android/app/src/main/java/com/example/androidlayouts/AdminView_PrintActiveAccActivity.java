package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.exceptions.IdNotInDatabaseException;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.Admin;

public class AdminView_PrintActiveAccActivity extends AppCompatActivity {

  private Button printActive;
  private String incorrectId;
  private String printFail;
  private Admin admin;
  private int user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_print_active);
    printActive = findViewById(R.id.bPrintActive);
    final EditText userId = findViewById(R.id.etUserId);
    Translator translate = new Translator(AdminView_PrintActiveAccActivity.this);
    userId.setHint(translate.translate(userId.getHint().toString(), LanguageSingleton.
        getInstance().getValue()));

    printActive.setText(translate.translate(printActive.getText().toString(),
        LanguageSingleton.getInstance().getValue()));

    TextView headerView = findViewById(R.id.tvPrintingActive);
    String header = headerView.getText().toString();
    String headerTrans = translate.translate(header, LanguageSingleton.getInstance()
        .getValue());
    headerView.setText(headerTrans);

    TextView promptView = findViewById(R.id.tvActivePrompt);
    String prompt = promptView.getText().toString();
    String promptTrans = translate.translate(prompt, LanguageSingleton.getInstance()
        .getValue());
    promptView.setText(promptTrans);
    printActive.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = getIntent();
        Translator translator = new Translator(AdminView_PrintActiveAccActivity.this);
        admin = (Admin) intent.getSerializableExtra("USER");
        admin.setContext(AdminView_PrintActiveAccActivity.this);
        if (TextUtils.isEmpty(userId.getText().toString()) == false) {
          user = Integer.parseInt(userId.getText().toString());
          incorrectId = translator.translate("Id is incorrect",
              LanguageSingleton.getInstance().getValue());
          printFail = translator.translate("Failed to print",
              LanguageSingleton.getInstance().getValue());
          try {
            String active = admin.getActiveAccounts(user);
            TextView textView = findViewById(R.id.tvPrintsActive);
            textView.setText(active);
          } catch (IdNotInDatabaseException e) {
            Toast.makeText(AdminView_PrintActiveAccActivity.this, incorrectId, Toast.LENGTH_SHORT)
                .show();
          } catch (NullPointerException a) {
            Toast.makeText(AdminView_PrintActiveAccActivity.this, printFail, Toast.LENGTH_SHORT)
                .show();
          }
        }
      }
    });

  }
}
