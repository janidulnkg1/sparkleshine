package com.example.sparkleshine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class frmViewUsers extends AppCompatActivity {

    Button back,viewuserdetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_view_users);

        back = (Button) findViewById(R.id.btnBack2Admin);
        viewuserdetails = (Button) findViewById(R.id.btnViewUserDetails);

        sqlite myDB = new sqlite(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Back = new Intent(frmViewUsers.this, frmAdmin.class);
                startActivity(Back);
            }
        });

        viewuserdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor respond = myDB.viewAllUsers();
                if (respond.getCount() == 0) {
                    Toast.makeText(frmViewUsers.this, "Sorry no User Details Available....!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StringBuffer sb = new StringBuffer();
                    while (respond.moveToNext()) {
                        sb.append("User ID : " + respond.getString(0) + "\n");
                        sb.append("User Full Name : " + respond.getString(1) + "\n");
                        sb.append("Username : " + respond.getString(2) + "\n");
                        sb.append("Password :" + respond.getString(3) + "\n");
                        sb.append("UserType :" + respond.getString(4) + "\n");
                        sb.append("========END========\n");
                        showUsers("All Users", sb.toString());
                    }
                }
            }
        });
    }
    public void showUsers(String title, String msg) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.show();
    }
}