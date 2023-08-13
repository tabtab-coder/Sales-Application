package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.exceptions.IdNotInDatabaseException;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.CustomerInterf;

public class CustomerView_ViewPrevPurchasesActivity extends AppCompatActivity {

  private TextView prevPurchases;
  private TextView header;
  private Intent intent;
  private Button exit;
  private Translator trans;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_prev_purchasehistory);

    trans = new Translator(CustomerView_ViewPrevPurchasesActivity.this);

    prevPurchases = findViewById(R.id.tvPrintPurchases);
    header = findViewById(R.id.tvPurchaseHistory);
    exit = findViewById(R.id.bExitPurchaseHistory);

    exit.setText(
        trans.translate(exit.getText().toString(), LanguageSingleton.getInstance().getValue()));
    header.setText(
        trans.translate(header.getText().toString(), LanguageSingleton.getInstance().getValue()));

    intent = getIntent();
    CustomerInterf customer = (CustomerInterf) intent.getSerializableExtra("USER");
    customer.setContext(CustomerView_ViewPrevPurchasesActivity.this);

    String toPrint = "";
    try {
      String purchases = customer.viewPurchases();
      toPrint = trans.translate(purchases, LanguageSingleton.getInstance().getValue());
    } catch (IdNotInDatabaseException e) {
      String errorMessage = trans
          .translate("Cannot find id for previous purchases",
              LanguageSingleton.getInstance().getValue());
      Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
    prevPurchases.setText(toPrint);
    prevPurchases.setMovementMethod(new ScrollingMovementMethod());
    exit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
  }
}
