package com.example.sparkleshine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class frmCustomer extends AppCompatActivity {

    Button logout,addorder,sendreview;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_customer);

        logout=(Button) findViewById(R.id.btnLogout2);
        addorder=(Button) findViewById(R.id.btnAddOrder);
        sendreview=(Button) findViewById(R.id.btnSendreviewtoContractor);

        sqlite myDB = new sqlite(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Logout= new Intent(frmCustomer.this, frmLogin.class);
                startActivity(Logout);
            }
        });

        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Addorder= new Intent(frmCustomer.this, frmAddOrder.class);
                startActivity(Addorder);
            }
        });

        sendreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Sendreview= new Intent(frmCustomer.this, frmSendReviewtoContractor.class);
                startActivity(Sendreview);
            }
        });




    }
}