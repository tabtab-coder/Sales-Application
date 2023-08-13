package com.example.androidlayouts;

import static android.widget.AdapterView.OnItemSelectedListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.store.ApplicationHelper;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.Roles;
import com.b07.users.UserInterface;

public class LoginPromptActivity extends AppCompatActivity implements OnItemSelectedListener {

  private Button login;
  private EditText nameLogin;
  private EditText passwordLogin;
  private Spinner userType;
  private int id;
  private String password;
  private Roles role = null;
  private Button register;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);
    nameLogin = findViewById(R.id.etNameLogin);
    passwordLogin = findViewById(R.id.etPasswordLogin);
    userType = findViewById(R.id.sUserTypeMenu);
    login = findViewById(R.id.btnLogin);
    register = findViewById(R.id.btnRegister);

    TextView titleView1 = findViewById(R.id.tvWelcome);
    TextView titleView2 = findViewById(R.id.tvLoginPrompt);
    final Translator translator = new Translator(LoginPromptActivity.this);
    titleView1.setText(translator
        .translate(titleView1.getText().toString(), LanguageSingleton.getInstance().getValue()));
    titleView2.setText(translator
        .translate(titleView2.getText().toString(), LanguageSingleton.getInstance().getValue()));
    nameLogin.setHint(translator
        .translate(nameLogin.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    passwordLogin.setHint(translator
        .translate(passwordLogin.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    login.setText(translator
        .translate(login.getText().toString(), LanguageSingleton.getInstance().getValue()));
    register.setText(translator
        .translate(register.getText().toString(), LanguageSingleton.getInstance().getValue()));

    String[] userList = getResources().getStringArray(R.array.userTypes);
    int i = 0;
    while (i < userList.length) {
      userList[i] =
          capitalizeFirstLetter(translator.translate(userList[i],
              LanguageSingleton.getInstance().getValue()).toLowerCase());
      i++;
    }
    ArrayAdapter<String> readUserType = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, userList);
    readUserType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    userType.setAdapter(readUserType);
    userType.setOnItemSelectedListener(this);
    userType.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            ((TextView) userType.getSelectedView()).setTextColor(Color.WHITE);
          }
        });

    login.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(nameLogin.getText().toString())
            || TextUtils.isEmpty(passwordLogin.getText().toString())) {
          Toast.makeText(LoginPromptActivity.this,
              translator.translate("Missing field", LanguageSingleton.getInstance().getValue()),
              Toast.LENGTH_SHORT)
              .show();
          return;
        }
        Intent intent = null;
        id = Integer.parseInt(nameLogin.getText().toString());
        password = passwordLogin.getText().toString();

        UserInterface user = ApplicationHelper.logIn(role, id, password, LoginPromptActivity.this);
        if (user != null) {
          if (user.getAuthenticate()) {
            switch (role) {
              case ADMIN:
                intent = new Intent(LoginPromptActivity.this, AdminView.class);
                break;
              case EMPLOYEE:
                intent = new Intent(LoginPromptActivity.this, EmployeeView.class);
                break;
              case CUSTOMER:
                intent = new Intent(LoginPromptActivity.this, CustomerView.class);
                intent.putExtra("FROM_ACTIVITY", "LOGIN_PAGE");
                break;
            }
          }
        }
        if (intent != null) {
          Toast.makeText(LoginPromptActivity.this,
              translator.translate("Logged in", LanguageSingleton.getInstance().getValue()),
              Toast.LENGTH_SHORT)
              .show();
          intent.putExtra("USER", user);
          startActivity(intent);
        } else {
          Toast.makeText(LoginPromptActivity.this,
              translator.translate("Failed to log in", LanguageSingleton.getInstance().getValue()),
              Toast.LENGTH_SHORT)
              .show();
        }
        return;
      }
    });

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginPromptActivity.this, EmployView_MakeNewUserActivity.class);
        intent.putExtra("ROLE", Roles.CUSTOMER);
        startActivity(intent);
      }
    });
  }

  private String capitalizeFirstLetter(String original) {
    if (original == null || original.length() == 0) {
      return original;
    }
    return original.substring(0, 1).toUpperCase() + original.substring(1);
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String[] userList = getResources().getStringArray(R.array.userTypes);
    role = Roles.valueOf(userList[position]);
    return;
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    role = null;
  }
}
