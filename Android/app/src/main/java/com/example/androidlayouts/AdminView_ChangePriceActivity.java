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
import com.b07.exceptions.InvalidInputException;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.Admin;

public class AdminView_ChangePriceActivity extends AppCompatActivity {

  private Button update;
  private Admin admin;
  private int item;
  private int price;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_change_price);
    update = findViewById(R.id.bUpdatePrices);
    Intent intent = getIntent();
    Translator translate = new Translator(AdminView_ChangePriceActivity.this);

    update.setText(translate.translate(update.getText().toString(), LanguageSingleton.
        getInstance().getValue()));

    final EditText itemId = findViewById(R.id.etItemID);
    itemId.setHint(translate.translate(itemId.getHint().toString(), LanguageSingleton.
        getInstance().getValue()));

    final EditText changedPrice = findViewById(R.id.etItemPrice);
    changedPrice.setHint(translate.translate(changedPrice.getHint().toString(),
        LanguageSingleton.getInstance().getValue()));

    TextView headerView = findViewById(R.id.changeItemPrices);
    String header = headerView.getText().toString();
    String headerTrans = translate.translate(header, LanguageSingleton.getInstance()
        .getValue());
    headerView.setText(headerTrans);

    admin = (Admin) intent.getSerializableExtra("USER");
    admin.setContext(AdminView_ChangePriceActivity.this);
    String viewbook = admin.Viewbook();
    update.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (TextUtils.isEmpty(itemId.getText().toString()) == false
            && TextUtils.isEmpty(changedPrice.getText().toString()) == false) {
          item = Integer.parseInt(itemId.getText().toString());
          price = Integer.parseInt(changedPrice.getText().toString());
          Translator translator = new Translator(AdminView_ChangePriceActivity.this);
          String success = translator.translate("Price Changed",
              LanguageSingleton.getInstance().getValue());
          String failure = translator.translate("Price failed to change",
              LanguageSingleton.getInstance().getValue());
          try {
            admin.changePrices(price, item);
            Toast.makeText(AdminView_ChangePriceActivity.this, success, Toast.LENGTH_SHORT)
                .show();
            finish();
            return;
          } catch (IdNotInDatabaseException e) {
            Toast.makeText(AdminView_ChangePriceActivity.this, failure, Toast.LENGTH_SHORT)
                .show();
            finish();
            return;
          } catch (InvalidInputException a) {
            Toast.makeText(AdminView_ChangePriceActivity.this, failure, Toast.LENGTH_SHORT)
                .show();
            finish();
            return;
          }
        }
      }
    });
  }
}


