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
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.EmployeeInterfaceInterface;

public class MakeNewAccActivity extends AppCompatActivity {

  private EditText customerID;
  private Button create;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_make_new_acc);

    TextView titleView1 = findViewById(R.id.tvupgradeCustomer);
    TextView titleView2 = findViewById(R.id.tvupgradeCustomer2);
    create = findViewById(R.id.bCreate);
    customerID = findViewById(R.id.etCustomerId);
    final Translator translator = new Translator(MakeNewAccActivity.this);
    titleView1.setText(translator
        .translate(titleView1.getText().toString(), LanguageSingleton.getInstance().getValue()));
    titleView2.setText(translator
        .translate(titleView2.getText().toString(), LanguageSingleton.getInstance().getValue()));
    customerID.setHint(translator
        .translate(customerID.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    create.setText(translator
        .translate(create.getText().toString(), LanguageSingleton.getInstance().getValue()));
    create.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(customerID.getText().toString())) {
          Toast.makeText(getApplicationContext(),
              translator.translate("Missing field",
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
          return;
        }
        int id = Integer.parseInt(customerID.getText().toString());
        Intent previousIntent = getIntent();
        EmployeeInterfaceInterface employeeInterface
            = (EmployeeInterfaceInterface) previousIntent.getSerializableExtra("INTERFACE");
        employeeInterface.setContext(MakeNewAccActivity.this);
        employeeInterface.upgradeAccount(id);
        finish();
        return;
      }
    });
  }
}
