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
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.InvalidInputException;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.EmployeeInterface;
import com.b07.users.EmployeeInterfaceInterface;
import com.b07.users.Roles;


public class EmployView_MakeNewUserActivity extends AppCompatActivity {

  private TextView loginText;
  private EditText nameUser;
  private EditText ageUser;
  private EditText passwordUser;
  private EditText addressUser;
  private Button createUser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_make_new_user);
    final Intent previousIntent = getIntent();
    final EmployeeInterfaceInterface employeeInterface
        = (EmployeeInterfaceInterface) previousIntent.getSerializableExtra("INTERFACE");
    final Roles role = (Roles) previousIntent.getSerializableExtra("ROLE");

    loginText = findViewById(R.id.tvHeaderUser);
    nameUser = findViewById(R.id.etNameUser);
    ageUser = findViewById(R.id.etAgeUser);
    passwordUser = findViewById(R.id.etPasswordUser);
    addressUser = findViewById(R.id.etAddressUser);
    createUser = findViewById(R.id.bCreateUser);

    TextView titleView1 = findViewById(R.id.tvHeaderUser);
    final Translator translator = new Translator(EmployView_MakeNewUserActivity.this);

    String titleString1 = translator
        .translate("Creating " + role.toString(), LanguageSingleton.getInstance().getValue());
    titleView1
        .setText(translator.translate(titleString1, LanguageSingleton.getInstance().getValue()));
    nameUser.setHint(translator
        .translate(nameUser.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    ageUser.setHint(translator
        .translate(ageUser.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    passwordUser.setHint(translator
        .translate(passwordUser.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    addressUser.setHint(translator
        .translate(addressUser.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    createUser.setText(translator
        .translate(createUser.getText().toString(), LanguageSingleton.getInstance().getValue()));
    createUser.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(nameUser.getText().toString())
            || TextUtils.isEmpty(ageUser.getText().toString())
            || TextUtils.isEmpty(passwordUser.getText().toString())
            || TextUtils.isEmpty(addressUser.getText().toString())) {
          Toast.makeText(EmployView_MakeNewUserActivity.this,
              translator.translate("Missing field", LanguageSingleton.getInstance().getValue()),
              Toast.LENGTH_SHORT)
              .show();
          return;
        }
        DatabaseInsertHelper mydb = new DatabaseInsertHelper(EmployView_MakeNewUserActivity.this);
        String name = nameUser.getText().toString();
        int age = Integer.parseInt(ageUser.getText().toString());
        String address = addressUser.getText().toString();
        String password = passwordUser.getText().toString();
        int id = -1;
        try {
          if (employeeInterface == null) {
            EmployeeInterface register =
                new EmployeeInterface(null, EmployView_MakeNewUserActivity.this);
            id = register.createCustomer(name, age, address, password);
            Toast.makeText(EmployView_MakeNewUserActivity.this,
                translator.translate("Created " + role.toString() + " id: " + id,
                    LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
                .show();
            finish();
            return;
          }
          employeeInterface.setContext(EmployView_MakeNewUserActivity.this);
          switch (role) {
            case ADMIN:
            case EMPLOYEE:
              id = employeeInterface.createEmployee(name, age, address, password);
              break;
            case CUSTOMER:
              id = employeeInterface.createCustomer(name, age, address, password);
              break;
          }
        } catch (InvalidInputException | DatabaseInsertException e) {
          Toast.makeText(EmployView_MakeNewUserActivity.this,
              translator.translate("Failed to create " + role.toString(),
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
          finish();
          return;
        }
        Toast.makeText(EmployView_MakeNewUserActivity.this,
            translator.translate("Created " + role.toString() + " id: " + id,
                LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
            .show();
        finish();
        return;
      }
    });
  }
}
