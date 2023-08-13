package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.store.ShoppingCart;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import java.text.NumberFormat;

public class CustomerView_ViewSubtotalActivity extends AppCompatActivity {

  private TextView subtotal;
  private TextView header;
  private Translator trans;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_total);

    trans = new Translator(this);

    header = findViewById(R.id.tvSubtotalHeader);
    subtotal = findViewById(R.id.tvSubtotal);

    header.setText(
        trans.translate(header.getText().toString(), LanguageSingleton.getInstance().getValue()));

    Intent i = getIntent();
    ShoppingCart cart = (ShoppingCart) i.getSerializableExtra("CART");
    cart.setContext(this);
    subtotal.setText((NumberFormat.getCurrencyInstance().format(cart.getTotal())));
  }
}
