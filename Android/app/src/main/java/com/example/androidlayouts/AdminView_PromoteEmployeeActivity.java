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
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.IdNotInDatabaseException;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.Admin;
import com.b07.users.EmployeeInterf;
import com.b07.users.Roles;
import com.b07.users.UserInterface;

public class AdminView_PromoteEmployeeActivity extends AppCompatActivity {

  private Button promote;
  private int promoteId;
  private Admin admin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_promote_employee);

    Intent intent = getIntent();
    Translator translate = new Translator(AdminView_PromoteEmployeeActivity.this);
    promote = findViewById(R.id.bPromote);
    promote.setText(translate.translate(promote.getText()
        .toString(), LanguageSingleton.getInstance().getValue()));
    final EditText employId = findViewById(R.id.etPromoteeId);
    employId.setHint(translate.translate(employId.getHint().toString(),
        LanguageSingleton.getInstance().getValue()));

    TextView headerView = findViewById(R.id.tvPromoteHeader);
    String header = headerView.getText().toString();
    String headerTrans = translate.translate(header,
        LanguageSingleton.getInstance().getValue());
    headerView.setText(headerTrans);

    admin = (Admin) intent.getSerializableExtra("USER");
    admin.setContext(AdminView_PromoteEmployeeActivity.this);
    promote.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(employId.getText().toString()) == false) {
          promoteId = Integer.parseInt(employId.getText().toString());
          Translator translator = new Translator(AdminView_PromoteEmployeeActivity.this);
          DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(
              AdminView_PromoteEmployeeActivity.this);
          UserInterface promotee = dbSelect.getUserDetailsHelper(promoteId);
          String success = translator.translate("Employee promoted",
              LanguageSingleton.getInstance().getValue());
          String failure = translator.translate("Promotion failed",
              LanguageSingleton.getInstance().getValue());
          try {
            int employeeRoleId = dbSelect.getRoleId(Roles.EMPLOYEE);
            if (promotee != null && promotee.getRoleId() == employeeRoleId) {
              admin.promoteEmployee((EmployeeInterf) promotee);
              Toast.makeText(AdminView_PromoteEmployeeActivity.this, success,
                  Toast.LENGTH_SHORT)
                  .show();
              finish();
              return;
            } else {
              Toast.makeText(AdminView_PromoteEmployeeActivity.this, failure,
                  Toast.LENGTH_SHORT)
                  .show();
              finish();
              return;
            }
          } catch (IdNotInDatabaseException e) {
            Toast.makeText(AdminView_PromoteEmployeeActivity.this, failure, Toast.LENGTH_SHORT)
                .show();
            finish();
            return;
          }
        }
      }
    });
  }
}

