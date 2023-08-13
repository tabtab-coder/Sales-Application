package com.example.androidlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.translate.LanguageSingleton;
import com.b07.translate.Translator;
import com.b07.users.Admin;

public class AdminView_ViewSaleHistoryActivity extends AppCompatActivity {

  private Admin admin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_sale);
    Intent intent = getIntent();
    Translator translate = new Translator(AdminView_ViewSaleHistoryActivity.this);
    admin = (Admin) intent.getSerializableExtra("USER");
    admin.setContext(AdminView_ViewSaleHistoryActivity.this);
    String viewbook = admin.Viewbook();

    TextView promptView = findViewById(R.id.tvPrintSales);
    String prompt = promptView.getText().toString();
    String promptTrans = translate.translate(prompt, LanguageSingleton.getInstance().getValue());
    promptView.setText(promptTrans);

    TextView titleView = findViewById(R.id.tvViewSale);
    String title = titleView.getText().toString();
    String titleTrans = translate.translate(title, LanguageSingleton.getInstance().getValue());

    TextView printBook = findViewById(R.id.tvSales);
    printBook.setText(viewbook);
    printBook.setMovementMethod(new ScrollingMovementMethod());

  }
}
