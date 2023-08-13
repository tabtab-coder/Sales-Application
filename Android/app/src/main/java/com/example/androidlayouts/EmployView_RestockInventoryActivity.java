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
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.IdNotInDatabaseException;
import com.b07.exceptions.InvalidInputException;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.EmployeeInterfaceInterface;

public class EmployView_RestockInventoryActivity extends AppCompatActivity {

  private EditText itemID;
  private EditText quantity;
  private Button restock;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restock_inventory);
    itemID = findViewById(R.id.tvItemID);
    quantity = findViewById(R.id.tvQuantity);
    restock = findViewById(R.id.bRestock);

    TextView titleView1 = findViewById(R.id.tvRestock);
    TextView titleView2 = findViewById(R.id.tvRestock2);
    final Translator translator = new Translator(EmployView_RestockInventoryActivity.this);
    String titleString1 = titleView1.getText().toString();
    String titleString2 = titleView2.getText().toString();
    titleView1.setText(translator.translate(titleString1,
        LanguageSingleton.getInstance().getValue()));
    titleView2.setText(translator.translate(titleString2,
        LanguageSingleton.getInstance().getValue()));

    itemID.setHint(translator.translate(itemID.getHint().toString(),
        LanguageSingleton.getInstance().getValue()));
    quantity.setHint(translator.translate(quantity.getHint().toString(),
        LanguageSingleton.getInstance().getValue()));
    restock.setText(translator.translate(restock.getText().toString(),
        LanguageSingleton.getInstance().getValue()));

    Intent previousIntent = getIntent();
    final EmployeeInterfaceInterface employeeInterface
        = (EmployeeInterfaceInterface) previousIntent.getSerializableExtra("INTERFACE");
    restock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(itemID.getText().toString())
            || TextUtils.isEmpty(quantity.getText().toString())) {
          Toast.makeText(getApplicationContext(),
              translator.translate("Missing field",
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
          return;
        }
        int id = Integer.parseInt(itemID.getText().toString());
        int itemQuantity = Integer.parseInt(quantity.getText().toString());
        DatabaseInsertHelper mydbInsert = new DatabaseInsertHelper(getApplicationContext());
        DatabaseSelectHelper mydbSelect = new DatabaseSelectHelper(getApplicationContext());
        try {
          employeeInterface.setContext(getApplicationContext());
          if (employeeInterface.restockInventory(mydbSelect.getItemHelper(id), itemQuantity)) {
            Intent intent = new Intent();
            intent.putExtra("INTERFACE", employeeInterface);
            setResult(RESULT_OK, intent);
            finish();
            return;
          }
        } catch (IdNotInDatabaseException | InvalidInputException e) {
          Toast.makeText(getApplicationContext(),
              translator.translate("Invalid input",
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
  }
}
