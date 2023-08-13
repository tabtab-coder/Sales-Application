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

public class AdminView_PrintInactiveAccActivity extends AppCompatActivity {

  private Button printInactive;
  private Admin admin;
  private int user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_print_inactive);
    printInactive = findViewById(R.id.bPrintInactive);
    Intent intent = getIntent();
    Translator translate = new Translator(AdminView_PrintInactiveAccActivity.this);

    printInactive.setText(translate.translate(printInactive.getText().toString(),
        LanguageSingleton.getInstance().getValue()));

    final EditText userId = findViewById(R.id.etInactiveUserId);
    userId.setHint(translate.translate(userId.getHint().toString(),
        LanguageSingleton.getInstance().getValue()));

    admin = (Admin) intent.getSerializableExtra("USER");
    admin.setContext(AdminView_PrintInactiveAccActivity.this);

    TextView headerView = findViewById(R.id.tvPrintingInActive);
    String header = headerView.getText().toString();
    String headerTrans = translate.translate(header, LanguageSingleton.getInstance()
        .getValue());
    headerView.setText(headerTrans);

    TextView promptView = findViewById(R.id.tvInactivePrompt);
    String prompt = promptView.getText().toString();
    String promptTrans = translate.translate(prompt, LanguageSingleton.getInstance()
        .getValue());
    promptView.setText(promptTrans);
    printInactive.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(userId.getText().toString()) == false) {
          user = Integer.parseInt(userId.getText().toString());
          Translator translator = new Translator(AdminView_PrintInactiveAccActivity.this);
          String incorrectId = translator.translate("Id is not a customer",
              LanguageSingleton.getInstance().getValue());
          String failure = translator.translate("Failed to print",
              LanguageSingleton.getInstance().getValue());

          try {
            String inactive = admin.getHistoricAccounts(user);
            TextView textView = findViewById(R.id.tvPrintsInactive);
            textView.setText(inactive);
          } catch (IdNotInDatabaseException e) {
            Toast.makeText(AdminView_PrintInactiveAccActivity.this, incorrectId, Toast.
                LENGTH_SHORT)
                .show();
          } catch (NullPointerException a) {
            Toast.makeText(AdminView_PrintInactiveAccActivity.this, failure, Toast.
                LENGTH_SHORT)
                .show();
          }
        }
      }
    });
  }
}
