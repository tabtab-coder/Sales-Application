package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.inventory.ItemTypes;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.Admin;
import java.util.HashMap;

public class AdminView_ViewInventoryStockActivity extends AppCompatActivity {

  private Admin admin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inventory_stock);
    Translator translate = new Translator(AdminView_ViewInventoryStockActivity.this);
    Intent intent = getIntent();
    admin = (Admin) intent.getSerializableExtra("USER");
    admin.setContext(AdminView_ViewInventoryStockActivity.this);

    TextView headerView = findViewById(R.id.tvInventoryStock);
    TextView hereIsStockText = findViewById(R.id.tvPrintInventory);
    hereIsStockText.setText(translate.translate(hereIsStockText.getText().toString(),
        LanguageSingleton.getInstance().getValue()));
    String header = headerView.getText().toString();
    String headerTrans = translate.translate(header, LanguageSingleton.getInstance().
        getValue());
    headerView.setText(headerTrans);

    TextView promptView = findViewById(R.id.tvPrintInventory);
    String prompt = promptView.getText().toString();
    String promptTrans = translate.translate(prompt, LanguageSingleton.getInstance().
        getValue());

    headerView.setText(headerTrans);

    HashMap<ItemTypes, Integer> stockMap = admin.mapStockLevels();
    String inventory = "";
    String itemKey;
    String quantity;
    String translated = "";
    for (ItemTypes item : stockMap.keySet()) {
      itemKey = item.toString().replace("_", " ").toLowerCase();
      itemKey = translate.translate(itemKey,
          LanguageSingleton.getInstance().getValue()).toUpperCase();
      quantity = stockMap.get(item).toString();
      inventory = itemKey + " - " + quantity;
      translated = translated + translate.translate(inventory, LanguageSingleton.getInstance().
          getValue()) + "\n";
    }
    TextView textView = findViewById(R.id.tvStock);
    textView.setText(translated);
  }
}
