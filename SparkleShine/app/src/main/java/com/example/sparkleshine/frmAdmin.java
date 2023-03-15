package com.example.sparkleshine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class frmAdmin extends AppCompatActivity {

    Button logout,viewusers,addprices;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_admin);

        logout=(Button) findViewById(R.id.btnLogout3);
        viewusers=(Button) findViewById(R.id.btnViewUsers);
        addprices=(Button) findViewById(R.id.btnAddPrices);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Logout = new Intent(frmAdmin.this, frmLogin.class);
                startActivity(Logout);
            }
        });

        viewusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ViewUsers = new Intent(frmAdmin.this, frmViewUsers.class);
                startActivity(ViewUsers);
            }
        });

        addprices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddPrices = new Intent(frmAdmin.this, frmAddPrices.class);
                startActivity(AddPrices);
            }
        });


    }
}