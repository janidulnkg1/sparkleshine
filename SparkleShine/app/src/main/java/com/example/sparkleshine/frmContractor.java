package com.example.sparkleshine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class frmContractor extends AppCompatActivity {

    Button logout,checkorder,sendreview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_contractor);

        logout=(Button) findViewById(R.id.btnLogout);
        checkorder=(Button) findViewById(R.id.btnCheckOrder);
        sendreview=(Button) findViewById(R.id.btnSendReviewtoCustomer);


        sqlite myDB = new sqlite(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Logout= new Intent(frmContractor.this, frmLogin.class);
                startActivity(Logout);
            }
        });
        checkorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkorder= new Intent(frmContractor.this, frmCheckOrder.class);
                startActivity(checkorder);
            }
        });
        sendreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendreview= new Intent(frmContractor.this, frmSendReviewtoCustomer.class);
                startActivity(sendreview);
            }
        });

    }
}