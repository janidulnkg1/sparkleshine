package com.example.sparkleshine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class frmCreateAccount extends AppCompatActivity {

    Button create, back, clear;
    EditText fullname, username, password, confirmpassword;
    Spinner designation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_create_account);

        create=(Button) findViewById(R.id.btnCreateAcc);
        back=(Button) findViewById(R.id.btnBack);
        clear=(Button) findViewById(R.id.btnClear);
        fullname=(EditText) findViewById(R.id.txtFname);
        username=(EditText) findViewById(R.id.txtUsername);
        password=(EditText) findViewById(R.id.txtPassword);
        confirmpassword=(EditText) findViewById(R.id.txtCPassword);
        designation=(Spinner) findViewById(R.id.spUserType);


        sqlite mydb=new sqlite(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(confirmpassword.getText().toString()) ){
                    boolean accadded = mydb.AddUser(fullname.getText().toString(),username.getText().toString(),password.getText().toString(),designation.getSelectedItem().toString());
                    if(accadded){
                        Toast.makeText(frmCreateAccount.this, "User Information have been added.....!", Toast.LENGTH_SHORT).show();
                        fullname.setText(null);
                        username.setText(null);
                        password.setText(null);
                        confirmpassword.setText(null);

                    }else{
                        Toast.makeText(frmCreateAccount.this,"User Information have not been added....!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(frmCreateAccount.this,"Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginfrm = new Intent(frmCreateAccount.this, frmLogin.class);
                startActivity(loginfrm);
            }
        });


    }


}





