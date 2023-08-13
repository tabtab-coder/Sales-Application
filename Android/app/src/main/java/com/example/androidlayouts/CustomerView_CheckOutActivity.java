package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.exceptions.IdNotInDatabaseException;
import com.b07.exceptions.InvalidInputException;
import com.b07.exceptions.OutOfStockException;
import com.b07.store.ShoppingCart;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import java.text.NumberFormat;

public class CustomerView_CheckOutActivity extends AppCompatActivity {

  private TextView beforeTax;
  private TextView afterTax;
  private TextView header;
  private Button checkOut;
  private Button exit;
  private ShoppingCart cart;
  private Intent intent;
  private Translator trans;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_check_out);

    trans = new Translator(this);

    header = findViewById(R.id.tvCheckOut);
    beforeTax = findViewById(R.id.tvBeforeTax);
    afterTax = findViewById(R.id.tvAfterTax);
    checkOut = findViewById(R.id.bCheckOut);
    exit = findViewById(R.id.bExitCheckOut);

    intent = getIntent();
    cart = (ShoppingCart) intent.getSerializableExtra("CART");
    cart.setContext(this);

    header.setText(
        trans.translate(header.getText().toString(), LanguageSingleton.getInstance().getValue())
            + (NumberFormat.getCurrencyInstance().format(cart.getTotal())));
    beforeTax.setText(
        trans.translate(beforeTax.getText().toString(), LanguageSingleton.getInstance().getValue())
            + (NumberFormat.getCurrencyInstance().format(cart.getTotal())));
    afterTax.setText(
        trans.translate(afterTax.getText().toString(), LanguageSingleton.getInstance().getValue())
            + (NumberFormat.getCurrencyInstance()
            .format(cart.getTotal().multiply(cart.getTaxRate()))));
    exit.setText(
        trans.translate(exit.getText().toString(), LanguageSingleton.getInstance().getValue()));
    checkOut.setText(
        trans.translate(checkOut.getText().toString(), LanguageSingleton.getInstance().getValue()));

    checkOut.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            try {
              boolean out = cart.checkOut();
              if (out == true) {
                Toast.makeText(CustomerView_CheckOutActivity.this, trans
                        .translate("Successful purchase!", LanguageSingleton.getInstance().getValue()),
                    Toast.LENGTH_SHORT)
                    .show();
              } else {
                Toast.makeText(CustomerView_CheckOutActivity.this,
                    trans.translate("Purchase failed", LanguageSingleton.getInstance().getValue()),
                    Toast.LENGTH_SHORT).show();
              }
            } catch (InvalidInputException | IdNotInDatabaseException e) {
              Toast.makeText(CustomerView_CheckOutActivity.this, trans
                      .translate("Purchase failed: Item does not exist",
                          LanguageSingleton.getInstance().getValue()),
                  Toast.LENGTH_SHORT).show();
            } catch (OutOfStockException e) {
              Toast.makeText(CustomerView_CheckOutActivity.this, trans
                      .translate("Purchase failed: Not enough items in stock",
                          LanguageSingleton.getInstance().getValue()),
                  Toast.LENGTH_SHORT).show();
            }
          }
        });

    exit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent back = new Intent(CustomerView_CheckOutActivity.this, CustomerView.class);
        back.putExtra("CART", cart);
        back.putExtra("FROM_ACTIVITY", "UPDATE");
        startActivity(back);
      }
    });

  }
}
