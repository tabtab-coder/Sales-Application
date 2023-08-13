package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import java.util.List;

public class CustomerView_ViewItemCartActivity extends AppCompatActivity {

  private TextView listItems;
  private TextView header;
  private Button exit;
  private Translator trans;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_items_cart);

    trans = new Translator(this);

    header = findViewById(R.id.tvViewCart);
    listItems = findViewById(R.id.tvPrintCart);
    exit = findViewById(R.id.bExitViewCart);

    header.setText(
        trans.translate(header.getText().toString(), LanguageSingleton.getInstance().getValue()));
    exit.setText(
        trans.translate(exit.getText().toString(), LanguageSingleton.getInstance().getValue()));

    Intent intent = getIntent();
    ShoppingCart cart = (ShoppingCart) intent.getSerializableExtra("CART");
    cart.setContext(this);
    List<Item> allItems = cart.getItems();
    if (allItems.isEmpty() == true) {
      Toast.makeText(this,
          trans.translate("Shopping cart is empty", LanguageSingleton.getInstance().getValue()),
          Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this,
          trans.translate("Printed cart!", LanguageSingleton.getInstance().getValue()),
          Toast.LENGTH_SHORT).show();
    }
    String itemList = trans.translate("Cart: ", LanguageSingleton.getInstance().getValue()) + "\n";
    for (Item item : allItems) {
      itemList = itemList.concat(
          trans.translate("Item: ", LanguageSingleton.getInstance().getValue()) + item.getName()
              + " " + trans.translate("Quantity: ", LanguageSingleton.getInstance().getValue())
              + cart.getQuantityInCart(item.getId()) + "\n");
    }
    listItems.setText(itemList);

    exit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
  }


}
