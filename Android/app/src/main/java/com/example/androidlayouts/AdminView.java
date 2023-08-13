package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.exceptions.IdNotInDatabaseException;
import com.b07.exceptions.InvalidInputException;
import com.b07.serializer.DatabaseDeserializer;
import com.b07.serializer.DatabaseSerializer;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.Admin;
import java.io.IOException;

public class AdminView extends AppCompatActivity {

  private TextView header;
  private TextView greeting;
  private Button deserialize;
  private Button serialize;
  private Button promoteEmploy;
  private Button inventoryStock;
  private Button viewSale;
  private Button changePrice;
  private Button printInactive;
  private Button printActive;

  private Intent intent;
  private Admin admin;
  private Translator trans;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_view);
    intent = getIntent();
    admin = (Admin) intent.getSerializableExtra("USER");
    trans = new Translator(this);

    header = findViewById(R.id.tvHeaderAV);
    greeting = findViewById(R.id.tvGreetings);
    deserialize = findViewById(R.id.bDeserialize);
    serialize = findViewById(R.id.bSerialize);
    promoteEmploy = findViewById(R.id.bOpt1AV);
    inventoryStock = findViewById(R.id.bOpt2AV);
    viewSale = findViewById(R.id.bOpt3AV);
    changePrice = findViewById(R.id.bOpt4AV);
    printInactive = findViewById(R.id.bOpt5AV);
    printActive = findViewById(R.id.bOpt6AV);

    header.setText(
        trans.translate(header.getText().toString(), LanguageSingleton.getInstance().getValue()));
    greeting.setText(
        trans.translate(greeting.getText().toString() + admin.getName(),
            LanguageSingleton.getInstance().getValue()));
    deserialize.setText(trans
        .translate(deserialize.getText().toString(), LanguageSingleton.getInstance().getValue()));
    serialize.setText(trans
        .translate(serialize.getText().toString(), LanguageSingleton.getInstance().getValue()));
    promoteEmploy.setText(trans
        .translate(promoteEmploy.getText().toString(), LanguageSingleton.getInstance().getValue()));
    inventoryStock.setText(trans.translate(inventoryStock.getText().toString(),
        LanguageSingleton.getInstance().getValue()));
    viewSale.setText(
        trans.translate(viewSale.getText().toString(), LanguageSingleton.getInstance().getValue()));
    changePrice.setText(trans
        .translate(changePrice.getText().toString(), LanguageSingleton.getInstance().getValue()));
    printInactive.setText(trans
        .translate(printInactive.getText().toString(), LanguageSingleton.getInstance().getValue()));
    printActive.setText(trans
        .translate(printActive.getText().toString(), LanguageSingleton.getInstance().getValue()));
    promoteEmploy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int1 = new Intent(AdminView.this, AdminView_PromoteEmployeeActivity.class);
        int1.putExtra("USER", admin);
        startActivity(int1);
      }
    });

    inventoryStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int2 = new Intent(AdminView.this, AdminView_ViewInventoryStockActivity.class);
        int2.putExtra("USER", admin);
        startActivity(int2);
      }
    });

    viewSale.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int3 = new Intent(AdminView.this, AdminView_ViewSaleHistoryActivity.class);
        int3.putExtra("USER", admin);
        startActivity(int3);
      }
    });

    changePrice.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int4 = new Intent(AdminView.this, AdminView_ChangePriceActivity.class);
        int4.putExtra("USER", admin);
        startActivity(int4);
      }
    });

    printInactive.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int5 = new Intent(AdminView.this, AdminView_PrintInactiveAccActivity.class);
        int5.putExtra("USER", admin);
        startActivity(int5);
      }
    });

    printActive.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int6 = new Intent(AdminView.this, AdminView_PrintActiveAccActivity.class);
        int6.putExtra("USER", admin);
        startActivity(int6);
      }
    });

    serialize.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatabaseSerializer serializer = new DatabaseSerializer(AdminView.this);
        try {
          serializer.serialize();
          return;
        } catch (IOException | IdNotInDatabaseException e) {
          Toast.makeText(AdminView.this,
              new Translator(AdminView.this).translate("Error with serialization",
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT).show();
        }
      }
    });

    deserialize.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatabaseDeserializer deserializer = new DatabaseDeserializer(AdminView.this);
        try {
          deserializer.deserialize();
          finish();
          return;
        } catch (ClassNotFoundException | InvalidInputException e) {
          Toast.makeText(AdminView.this,
              new Translator(AdminView.this).translate("Error with deserialization",
                  LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
          DatabaseSerializer serializer = new DatabaseSerializer(AdminView.this);
          try {
            deserializer.deserialize();
            finish();
            return;
          } catch (IOException | ClassNotFoundException | InvalidInputException e2) {
            Toast.makeText(AdminView.this,
                new Translator(AdminView.this).translate("Failed to roll back to previous database",
                    LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
                .show();
          }
        } catch (IOException e) {
          Toast.makeText(AdminView.this, new Translator(AdminView.this).translate("File not found",
              LanguageSingleton.getInstance().getValue()), Toast.LENGTH_SHORT)
              .show();
        }
        return;
      }
    });
  }
}