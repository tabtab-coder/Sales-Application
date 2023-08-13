package com.example.androidlayouts;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.InvalidInputException;
import com.b07.store.ShoppingCart;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.CustomerInterf;

public class CustomerView extends AppCompatActivity {

  private Button exitCust;
  private Button listItems;
  private Button addorRemoveItem;
  private Button viewTotal;
  private Button viewPurchases;
  private Button checkout;
  private TextView header;
  private TextView greeting;
  private DatabaseSelectHelper selector;
  private ShoppingCart cart;
  private CustomerInterf user;

  private Translator trans;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_view);

    trans = new Translator(this);

    header = findViewById(R.id.tvHeader);
    greeting = findViewById(R.id.customerViewGreeting);
    exitCust = findViewById(R.id.exitCust);
    listItems = findViewById(R.id.listItems);
    addorRemoveItem = findViewById(R.id.addOrRemoveItem);
    viewTotal = findViewById(R.id.viewTotal);
    viewPurchases = findViewById(R.id.viewPurchases);
    checkout = findViewById(R.id.checkout);
    selector = new DatabaseSelectHelper(this);

    greeting.setText(
        trans.translate(greeting.getText().toString(), LanguageSingleton.getInstance().getValue()));
    exitCust.setText(
        trans.translate(exitCust.getText().toString(), LanguageSingleton.getInstance().getValue()));
    listItems.setText(trans
        .translate(listItems.getText().toString(), LanguageSingleton.getInstance().getValue()));
    addorRemoveItem.setText(trans.translate(addorRemoveItem.getText().toString(),
        LanguageSingleton.getInstance().getValue()));
    viewTotal.setText(trans
        .translate(viewTotal.getText().toString(), LanguageSingleton.getInstance().getValue()));
    viewPurchases.setText(trans
        .translate(viewPurchases.getText().toString(), LanguageSingleton.getInstance().getValue()));
    checkout.setText(
        trans.translate(checkout.getText().toString(), LanguageSingleton.getInstance().getValue()));

    Intent previousIntent = getIntent();
    user = (CustomerInterf) previousIntent.getSerializableExtra("USER");
    if (previousIntent.getStringExtra("FROM_ACTIVITY").equals("UPDATE")) {
      cart = (ShoppingCart) previousIntent.getSerializableExtra("CART");
    } else {
      cart = new ShoppingCart(user, this);
      if (cart.hasAccount() == true && cart.getPrevCart().isEmpty() == false
          && cart.isRestored() == false) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerView.this);
        View myView = getLayoutInflater().inflate(R.layout.dialog_pop_restore_cart_prompt, null);
        TextView prompt = myView.findViewById(R.id.restoreCartPrompt);
        Button restoreYes = myView.findViewById(R.id.cartRestoreYes);
        Button restoreNo = myView.findViewById(R.id.cartRestoreNo);
        prompt.setText(trans
            .translate(prompt.getText().toString(), LanguageSingleton.getInstance().getValue()));
        restoreYes.setText(trans.translate(restoreYes.getText().toString(),
            LanguageSingleton.getInstance().getValue()));
        restoreNo.setText(trans
            .translate(restoreNo.getText().toString(), LanguageSingleton.getInstance().getValue()));

        builder.setView(myView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        restoreNo.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            dialog.dismiss();
          }
        });

        restoreYes.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent chooseCart = new Intent(CustomerView.this,
                CustomerView_ChooseCartRestoreActivity.class);
            chooseCart.putExtra("CART", cart);
            startActivity(chooseCart);
          }
        });

        builder.setView(myView);
        dialog.show();
      }
    }

    cart.setContext(this);

    header.setText(
        trans.translate("Hello, ", LanguageSingleton.getInstance().getValue()) + cart.getCustomer()
            .getName());

    listItems.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int1 = new Intent(CustomerView.this, CustomerView_ViewItemCartActivity.class);
        int1.putExtra("CART", cart);
        startActivity(int1);
      }
    });

    addorRemoveItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int2 = new Intent(CustomerView.this, CustomerView_AddRemoveItemsActivity.class);
        int2.putExtra("CART", cart);
        startActivity(int2);
      }
    });

    viewTotal.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int3 = new Intent(CustomerView.this, CustomerView_ViewSubtotalActivity.class);
        int3.putExtra("CART", cart);
        startActivity(int3);
      }
    });

    viewPurchases.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int4 = new Intent(CustomerView.this, CustomerView_ViewPrevPurchasesActivity.class);
        int4.putExtra("USER", cart.getCustomer());
        startActivity(int4);
      }
    });

    checkout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent int5 = new Intent(CustomerView.this, CustomerView_CheckOutActivity.class);
        int5.putExtra("CART", cart);
        startActivity(int5);
      }
    });

    exitCust.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (cart.hasAccount() == true && cart.getItems().isEmpty() == false) {
          AlertDialog.Builder builder = new AlertDialog.Builder(CustomerView.this);
          View myView = getLayoutInflater().inflate(R.layout.dialog_pop_save_cart_prompt, null);
          TextView question = myView.findViewById(R.id.saveCartQuestion);
          Button saveYes = myView.findViewById(R.id.cartSaveYes);
          Button saveNo = myView.findViewById(R.id.cartSaveNo);
          question.setText(trans.translate(question.getText().toString(),
              LanguageSingleton.getInstance().getValue()));
          saveYes.setText(trans
              .translate(saveYes.getText().toString(), LanguageSingleton.getInstance().getValue()));
          saveNo.setText(trans
              .translate(saveNo.getText().toString(), LanguageSingleton.getInstance().getValue()));

          saveYes.setOnClickListener(new OnClickListener() {
            boolean saved;

            @Override
            public void onClick(View v) {
              try {
                saved = cart.saveCart();
              } catch (InvalidInputException e) {
                Toast.makeText(CustomerView.this, trans.translate("Something went wrong with ID",
                    LanguageSingleton.getInstance().getValue()), Toast.LENGTH_LONG).show();
              }
              if (saved) {
                Toast.makeText(CustomerView.this,
                    trans.translate("Saved cart!", LanguageSingleton.getInstance().getValue()),
                    Toast.LENGTH_SHORT).show();
                Intent int6 = new Intent(CustomerView.this, LoginPromptActivity.class);
                startActivity(int6);
              } else {
                Toast.makeText(CustomerView.this, trans
                    .translate("No available accounts! Please contact an employee",
                        LanguageSingleton.getInstance().getValue()), Toast.LENGTH_LONG).show();
              }
            }
          });

          saveNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent int6 = new Intent(CustomerView.this, LoginPromptActivity.class);
              startActivity(int6);
            }
          });

          builder.setView(myView);
          AlertDialog dialog = builder.create();
          dialog.show();
        } else {
          Intent int6 = new Intent(CustomerView.this, LoginPromptActivity.class);
          startActivity(int6);
        }
      }
    });
  }
}
