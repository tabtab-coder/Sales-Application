package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import java.util.HashMap;
import java.util.Map.Entry;

public class CustomerView_ChooseCartRestoreActivity extends AppCompatActivity {

  private ShoppingCart cart;
  private int cartSelect;
  private EditText cartNumber;
  private TextView showCarts;
  private TextView header;
  private Button confirmed;
  private HashMap<Integer, HashMap<Item, Integer>> prevStoredCarts;
  private Translator trans;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_choose_cart_restore);

    trans = new Translator(CustomerView_ChooseCartRestoreActivity.this);

    header = findViewById(R.id.chooseCartRestoreText);
    cartNumber = findViewById(R.id.cartNumberInput);
    confirmed = findViewById(R.id.cartNumberConfirm);
    showCarts = findViewById(R.id.carts);

    header.setText(
        trans.translate(header.getText().toString(), LanguageSingleton.getInstance().getValue()));
    cartNumber.setHint(trans
        .translate(cartNumber.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    confirmed.setText(trans
        .translate(confirmed.getText().toString(), LanguageSingleton.getInstance().getValue()));
    showCarts.setText(trans
        .translate(showCarts.getText().toString(), LanguageSingleton.getInstance().getValue()));

    Intent prevView = getIntent();
    cart = (ShoppingCart) prevView.getSerializableExtra("CART");
    cart.setContext(this);
    prevStoredCarts = cart.getPrevCart();
    showCarts.setText(getPrevCarts());

    confirmed.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(cartNumber.getText().toString())) {
          Toast.makeText(CustomerView_ChooseCartRestoreActivity.this,
              trans.translate("Please input number", LanguageSingleton.getInstance().getValue()),
              Toast.LENGTH_SHORT).show();
        } else {
          cartSelect = Integer.parseInt(cartNumber.getText().toString());
          if (prevStoredCarts.containsKey(cartSelect) == true) {
            Intent back = new Intent(CustomerView_ChooseCartRestoreActivity.this,
                CustomerView.class);
            cart.restoreCart(cartSelect);
            Toast.makeText(CustomerView_ChooseCartRestoreActivity.this,
                trans.translate("Cart restored!", LanguageSingleton.getInstance().getValue()),
                Toast.LENGTH_SHORT).show();
            back.putExtra("CART", cart);
            back.putExtra("FROM_ACTIVITY", "UPDATE");
            startActivity(back);
          } else {
            Toast.makeText(CustomerView_ChooseCartRestoreActivity.this,
                trans.translate("Invalid input", LanguageSingleton.getInstance().getValue()),
                Toast.LENGTH_SHORT).show();
          }
        }
      }
    });
  }

  private String getPrevCarts() {
    trans = new Translator(CustomerView_ChooseCartRestoreActivity.this);
    String prevCarts = "";
    for (Entry<Integer, HashMap<Item, Integer>> entry : prevStoredCarts.entrySet()) {
      prevCarts = prevCarts.concat(trans.translate("Account: ", LanguageSingleton
          .getInstance().getValue()) + entry.getKey() + ": \n");
      for (Item item : entry.getValue().keySet()) {
        prevCarts = prevCarts.concat(trans.translate("Item: ", LanguageSingleton
            .getInstance().getValue()) + item.getName() + trans
            .translate("Quantity: ", LanguageSingleton
                .getInstance().getValue()) + entry.getValue().get(item) + "\n");
      }
    }
    return prevCarts;
  }
}
