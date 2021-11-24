package com.example.assignment3;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spProducts;
    List<String> productNames = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    private ImageView ivProduct;
    private TextView tvPrice;
    private EditText etQuantity;
    private RadioGroup rgDeliveryOption;
    private Button btnFinalPrice;
    private TextView tvFinalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateData();
        initViews();
        configViews();
    }

    private void populateData() {
        productList.add(new Product("TV", R.drawable.tv, 200));
        productList.add(new Product("PC", R.drawable.pc, 350));
        productList.add(new Product("Mobile", R.drawable.mobile, 150));
        productList.add(new Product("Laptop", R.drawable.laptop, 325));
        productList.add(new Product("Tablet", R.drawable.tablet, 100));
        productList.add(new Product("HeadPhone", R.drawable.headphone, 75));

        for (Product product : productList) {
            productNames.add(product.getName());
        }
    }

    private void initViews() {
        spProducts = findViewById(R.id.sp_products);
        ivProduct = findViewById(R.id.iv_product);
        tvPrice = findViewById(R.id.tv_price);
        etQuantity = findViewById(R.id.et_quantity);
        rgDeliveryOption = findViewById(R.id.rg_delivery_option);
        btnFinalPrice = findViewById(R.id.btn_get_price);
        tvFinalAmount = findViewById(R.id.tv_final_price);
    }

    private void configViews() {
        spProducts.setOnItemSelectedListener(this);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, productNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProducts.setAdapter(aa);

        ivProduct.setImageDrawable(ContextCompat.getDrawable(this,
                productList.get(0).getImgID()));
        tvPrice.setText(String.format("Price: $%s", productList.get(0).getPrice()));

        btnFinalPrice.setOnClickListener(view -> getFinalPriceAndUpdate());
    }

    private void getFinalPriceAndUpdate() {
        if (TextUtils.isEmpty(etQuantity.getText()) ||
                Integer.parseInt(etQuantity.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Quantity!!!", Toast.LENGTH_SHORT).show();
        } else {
            Product selectedProduct = productList.get(0);
            for (Product product : productList) {
                if (product.getName().equalsIgnoreCase(spProducts.getSelectedItem().toString())) {
                    selectedProduct = product;
                }
            }

            double finalAmount = Integer.parseInt(etQuantity.getText().toString()) *
                    selectedProduct.getPrice();
            if (rgDeliveryOption.getCheckedRadioButtonId() == R.id.rb_delivery &&
                    finalAmount > 100) {
                finalAmount = finalAmount - (finalAmount * 0.1);
            }
            tvFinalAmount.setText(String.format("Final payable amount is: $%s", finalAmount));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ivProduct.setImageDrawable(ContextCompat.getDrawable(this,
                productList.get(i).getImgID()));
        tvPrice.setText(String.format("Price: %s", productList.get(i).getPrice()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //todo handle on no product selected
    }
}