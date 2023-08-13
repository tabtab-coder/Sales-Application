package com.example.androidlayouts;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.exceptions.InvalidInputException;
import com.b07.store.ApplicationHelper;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.Roles;

public class InitializationDatabaseActivity extends AppCompatActivity {

  private EditText nameAdmin;
  private EditText ageAdmin;
  private EditText passwordAdmin;
  private TextView addressAdmin;

  private EditText nameEmployee;
  private EditText ageEmployee;
  private EditText passwordEmployee;
  private TextView addressEmployee;

  private Button initialize;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_initialization_page);

    nameAdmin = findViewById(R.id.etNameAdmin);
    passwordAdmin = findViewById(R.id.etPasswordAdmin);
    ageAdmin = findViewById(R.id.etAgeAdmin);
    addressAdmin = (EditText) findViewById(R.id.etAddressAdmin);

    nameEmployee = findViewById(R.id.etNameEmployee);
    passwordEmployee = findViewById(R.id.etPasswordEmployee);
    ageEmployee = findViewById(R.id.etAgeEmployee);
    addressEmployee = (EditText) findViewById(R.id.etAddressEmployee);
    initialize = findViewById(R.id.initialize);

    TextView titleView1 = findViewById(R.id.tvHeader);
    TextView titleView2 = findViewById(R.id.tvExplanationEmployee);
    TextView titleView3 = findViewById(R.id.tvExplanationAdmin);
    final Translator translator = new Translator(InitializationDatabaseActivity.this);
    titleView3.setText(translator
        .translate(titleView3.getText().toString(), LanguageSingleton.getInstance().getValue()));
    titleView2.setText(translator
        .translate(titleView2.getText().toString(), LanguageSingleton.getInstance().getValue()));
    titleView1.setText(translator
        .translate(titleView1.getText().toString(), LanguageSingleton.getInstance().getValue()));
    nameAdmin.setHint(translator
        .translate(nameAdmin.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    passwordAdmin.setHint(translator
        .translate(passwordAdmin.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    ageAdmin.setHint(translator
        .translate(ageAdmin.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    addressAdmin.setHint(translator
        .translate(addressAdmin.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    initialize.setText(translator
        .translate(initialize.getText().toString(), LanguageSingleton.getInstance().getValue()));
    nameEmployee.setHint(translator
        .translate(nameEmployee.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    passwordEmployee.setHint(translator.translate(passwordEmployee.getHint().toString(),
        LanguageSingleton.getInstance().getValue()));
    ageEmployee.setHint(translator
        .translate(ageEmployee.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    addressEmployee.setHint(translator.translate(addressEmployee.getHint().toString(),
        LanguageSingleton.getInstance().getValue()));

    initialize.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (TextUtils.isEmpty(nameAdmin.getText().toString())
            || TextUtils.isEmpty(passwordAdmin.getText().toString())
            || TextUtils.isEmpty(ageAdmin.getText().toString())
            || TextUtils.isEmpty(addressAdmin.getText().toString())
            || TextUtils.isEmpty(nameEmployee.getText().toString())
            || TextUtils.isEmpty(passwordEmployee.getText().toString())
            || TextUtils.isEmpty(ageEmployee.getText().toString())
            || TextUtils.isEmpty(addressEmployee.getText().toString())) {
          Toast.makeText(InitializationDatabaseActivity.this,
              translator.translate("Missing field",
                  LanguageSingleton.getInstance().getValue()),
              Toast.LENGTH_SHORT).show();
          return;
        }
        DatabaseInsertHelper mydb = new DatabaseInsertHelper(InitializationDatabaseActivity.this);
        String eN = nameEmployee.getText().toString();
        String aN = nameAdmin.getText().toString();
        String eP = passwordEmployee.getText().toString();
        String aP = passwordAdmin.getText().toString();
        String aA = addressAdmin.getText().toString();
        String aE = addressEmployee.getText().toString();
        int agE = Integer.parseInt(ageEmployee.getText().toString());
        int agA = Integer.parseInt(ageAdmin.getText().toString());
        int a;
        int b;
        try {
          a = mydb.insertNewUserHelper(aN, agA, aA, aP);
          b = mydb.insertNewUserHelper(eN, agE, aE, eP);
          int x = mydb.insertRoleHelper(Roles.ADMIN.toString());
          int y = mydb.insertRoleHelper(Roles.EMPLOYEE.toString());
          mydb.insertUserRoleHelper(a, x);
          mydb.insertUserRoleHelper(b, y);
          ApplicationHelper.addItems(InitializationDatabaseActivity.this);
        } catch (InvalidInputException e) {
          Toast.makeText(InitializationDatabaseActivity.this,
              translator.translate("Failed to initialize",
                  LanguageSingleton.getInstance().getValue()),
              Toast.LENGTH_SHORT).show();
          return;
        }
        Toast.makeText(InitializationDatabaseActivity.this,
            translator.translate("Admin Created, id:",
                LanguageSingleton.getInstance().getValue()) + " " + a,
            Toast.LENGTH_SHORT).show();
        Toast.makeText(InitializationDatabaseActivity.this,
            translator.translate("Employee Created, id:",
                LanguageSingleton.getInstance().getValue()) + " " + b,
            Toast.LENGTH_SHORT).show();
        Toast.makeText(InitializationDatabaseActivity.this,
            translator.translate("Database initialized",
                LanguageSingleton.getInstance().getValue()),
            Toast.LENGTH_SHORT).show();
        finish();
        return;
      }
    });
  }
}
