package com.example.androidlayouts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.InvalidInputException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.store.ShoppingCart;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import java.util.List;

public class CustomerView_AddRemoveItemsActivity extends AppCompatActivity implements
    AdapterView.OnItemSelectedListener {

  private int itemQuantity;
  private Item itemKind;
  private ShoppingCart cart;
  private DatabaseSelectHelper selector;

  private EditText quantity;
  private Spinner itemSelector;
  private Button add;
  private Button remove;
  private Button exit;
  private Translator trans;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_or_remove_cart);

    Intent intent = getIntent();
    cart = (ShoppingCart) intent.getSerializableExtra("CART");
    cart.setContext(this);
    selector = new DatabaseSelectHelper(this);

    trans = new Translator(CustomerView_AddRemoveItemsActivity.this);

    itemSelector = findViewById(R.id.sItemType);
    quantity = findViewById(R.id.etQuantity);
    add = findViewById(R.id.bAdd);
    remove = findViewById(R.id.bRemove);
    exit = findViewById(R.id.bExitAddRem);

    quantity.setHint(
        trans.translate(quantity.getHint().toString(), LanguageSingleton.getInstance().getValue()));
    add.setText(
        trans.translate(add.getText().toString(), LanguageSingleton.getInstance().getValue()));
    remove.setText(
        trans.translate(remove.getText().toString(), LanguageSingleton.getInstance().getValue()));
    exit.setText(
        trans.translate(exit.getText().toString(), LanguageSingleton.getInstance().getValue()));

    ArrayAdapter<String> getItemTypeAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1,
        getResources().getStringArray(R.array.itemTypes));
    getItemTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    itemSelector.setAdapter(getItemTypeAdapter);
    itemSelector.setOnItemSelectedListener(this);
    itemSelector.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            ((TextView) itemSelector.getSelectedView()).setTextColor(Color.WHITE);
          }
        });

    add.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        getInfo();
        try {
          cart.addItem(itemKind, itemQuantity);
          Toast.makeText(
              CustomerView_AddRemoveItemsActivity.this, trans.translate("Added ", LanguageSingleton
                  .getInstance().getValue()) + itemQuantity + " " + itemKind.getName().toString(),
              Toast.LENGTH_SHORT).show();
          Toast.makeText(CustomerView_AddRemoveItemsActivity.this,
              trans.translate("New total: ", LanguageSingleton.getInstance().getValue()) + cart
                  .getTotal().toString(), Toast.LENGTH_SHORT).show();
        } catch (InvalidInputException e) {
          Toast.makeText(CustomerView_AddRemoveItemsActivity.this,
              trans.translate("Failed to add item", LanguageSingleton.getInstance().getValue()),
              Toast.LENGTH_SHORT)
              .show();
        }
      }

    });

    remove.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        getInfo();
        try {
          cart.removeItem(itemKind, itemQuantity);
          Toast.makeText(CustomerView_AddRemoveItemsActivity.this,
              trans.translate("Removed ", LanguageSingleton
                  .getInstance().getValue()) + itemQuantity + " " + itemKind.getName().toString(),
              Toast.LENGTH_SHORT).show();
        } catch (InvalidInputException e) {
          Toast
              .makeText(CustomerView_AddRemoveItemsActivity.this,
                  trans.translate("Failed to remove item", LanguageSingleton
                      .getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
        }
      }

    });

    exit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent back = new Intent(CustomerView_AddRemoveItemsActivity.this, CustomerView.class);
        back.putExtra("CART", cart);
        back.putExtra("FROM_ACTIVITY", "UPDATE");
        startActivity(back);
      }
    });
  }


  private void getInfo() {
    itemQuantity = Integer.parseInt(quantity.getText().toString());
    List<Item> allItems = selector.getAllItemsHelper();
    for (Item item : allItems) {
      if (ItemTypes.getEnum(itemSelector.getSelectedItem().toString()).equals(item.getName())) {
        itemKind = item;
      }
    }
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    List<Item> allItems = selector.getAllItemsHelper();
    for (Item item : allItems) {
      if (ItemTypes.getEnum(itemSelector.getSelectedItem().toString()).equals(item.getName())) {
        itemKind = item;
      }
    }
    return;
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    itemKind = null;
  }
}
