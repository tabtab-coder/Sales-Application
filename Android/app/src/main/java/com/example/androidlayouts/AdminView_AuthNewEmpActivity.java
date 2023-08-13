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
import com.b07.store.ApplicationHelper;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.EmployeeInterf;
import com.b07.users.EmployeeInterfaceInterface;
import com.b07.users.Roles;
import com.b07.users.UserInterface;

public class AdminView_AuthNewEmpActivity extends AppCompatActivity {

  private EditText employeeID;
  private EditText employeePassword;
  private Button login;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth_new_emp);

    employeeID = findViewById(R.id.etEmployeeID);
    employeePassword = findViewById(R.id.etPassword);
    login = findViewById(R.id.bLogin);

    TextView titleView1 = findViewById(R.id.tvAuthenticate);
    final Translator translator = new Translator(AdminView_AuthNewEmpActivity.this);
    titleView1.setText(translator
        .translate(titleView1.getText().toString(), LanguageSingleton.getInstance().getValue()));

    employeeID.setHint(translator
        .translate(employeeID.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    employeePassword.setHint(translator.translate(employeePassword.getHint().toString(),
        LanguageSingleton.getInstance().getValue()));

    login.setText(translator
        .translate(login.getText().toString(), LanguageSingleton.getInstance().getValue()));

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(employeeID.getText().toString())
            || TextUtils.isEmpty(employeePassword.getText().toString())) {
          Toast.makeText(getApplicationContext(),
              translator.translate("Missing field",
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
          return;
        }
        int id = Integer.parseInt(employeeID.getText().toString());
        String password = employeePassword.getText().toString();

        UserInterface user = ApplicationHelper
            .logIn(Roles.EMPLOYEE, id, password, getApplicationContext());
        if (user != null && user.getAuthenticate()) {
          Intent previousIntent = getIntent();
          EmployeeInterfaceInterface employeeInterface
              = (EmployeeInterfaceInterface) previousIntent.getSerializableExtra("INTERFACE");
          employeeInterface.setCurrentEmployee((EmployeeInterf) user);
          Toast.makeText(getApplicationContext(),
              translator.translate("Logged in",
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
          Intent intent = new Intent();
          intent.putExtra("INTERFACE", employeeInterface);
          setResult(RESULT_OK, intent);
          finish();
          return;
        } else {
          Toast.makeText(getApplicationContext(),
              translator.translate("Failed to log in",
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
          return;
        }
      }
    });
  }
}
