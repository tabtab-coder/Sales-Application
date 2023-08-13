package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.EmployeeInterf;
import com.b07.users.EmployeeInterface;
import com.b07.users.EmployeeInterfaceInterface;
import com.b07.users.Roles;


public class EmployeeView extends AppCompatActivity {

  private Button authEmp;
  private Button makeUser;
  private Button makeAccount;
  private Button makeEmp;
  private Button restock;
  private TextView titleView1;
  private TextView titleView2;

  private EmployeeInterfaceInterface employeeInterface;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_employee_view);

    Intent previousIntent = getIntent();
    EmployeeInterf user = (EmployeeInterf) previousIntent.getSerializableExtra("USER");
    DatabaseSelectHelper mydbSelect = new DatabaseSelectHelper(this);
    employeeInterface = new EmployeeInterface(user, mydbSelect.getInventoryHelper(), this);
    authEmp = findViewById(R.id.authEmp);
    makeUser = findViewById(R.id.makeUser);
    makeAccount = findViewById(R.id.makeAccount);
    makeEmp = findViewById(R.id.makeEmp);
    restock = findViewById(R.id.restock);

    titleView1 = findViewById(R.id.tvHeader);
    titleView2 = findViewById(R.id.tvBellow);
    Translator translator = new Translator(EmployeeView.this);

    titleView1.setText(translator.translate("Hello", LanguageSingleton.getInstance()
        .getValue()) + translator
        .translate(user.getName(), LanguageSingleton.getInstance().getValue()));
    titleView2
        .setText(translator.translate(titleView2.getText().toString(),
            LanguageSingleton.getInstance().getValue()));

    authEmp.setText(translator
        .translate(authEmp.getText().toString(), LanguageSingleton.getInstance().getValue()));
    makeUser.setText(translator
        .translate(makeUser.getText().toString(), LanguageSingleton.getInstance().getValue()));
    makeAccount.setText(translator
        .translate(makeAccount.getText().toString(), LanguageSingleton.getInstance().getValue()));
    makeEmp.setText(translator
        .translate(makeEmp.getText().toString(), LanguageSingleton.getInstance().getValue()));
    restock.setText(translator
        .translate(restock.getText().toString(), LanguageSingleton.getInstance().getValue()));

    authEmp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int1 = new Intent(EmployeeView.this, EmployView_AuthNewEmployActivity.class);
        int1.putExtra("INTERFACE", employeeInterface);
        startActivityForResult(int1, 1);

      }
    });

    makeUser.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int1 = new Intent(EmployeeView.this, EmployView_MakeNewUserActivity.class);
        int1.putExtra("INTERFACE", employeeInterface);
        int1.putExtra("ROLE", Roles.CUSTOMER);
        startActivity(int1);
      }
    });

    makeAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int1 = new Intent(EmployeeView.this, EmployView_MakeNewAccActivity.class);
        int1.putExtra("INTERFACE", employeeInterface);
        startActivity(int1);
      }
    });

    makeEmp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int1 = new Intent(EmployeeView.this, EmployView_MakeNewUserActivity.class);
        int1.putExtra("INTERFACE", employeeInterface);
        int1.putExtra("ROLE", Roles.EMPLOYEE);
        startActivity(int1);
      }
    });

    restock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int1 = new Intent(EmployeeView.this, EmployView_RestockInventoryActivity.class);
        int1.putExtra("INTERFACE", employeeInterface);
        startActivityForResult(int1, 1);
      }
    });

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        employeeInterface = (EmployeeInterfaceInterface) data.getSerializableExtra("INTERFACE");
      }
    }
  }
}
