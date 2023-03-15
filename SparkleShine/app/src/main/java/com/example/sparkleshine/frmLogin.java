package com.example.sparkleshine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class frmLogin extends AppCompatActivity {

    Button loginbtn,AcCreatebtn;
    EditText us, pw;
    String username,password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_login);

        loginbtn=(Button) findViewById(R.id.btnLogin);
        us=(EditText) findViewById(R.id.txtUN);
        pw=(EditText) findViewById(R.id.txtPass);
        AcCreatebtn=(Button)findViewById(R.id.btnCrtUser);


        sqlite myDB = new sqlite(this);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username=us.getText().toString();
               password=pw.getText().toString();
                if(username.equals("admin")) {
                    if (password.equals("admin123")) {
                        Intent adminform = new Intent(frmLogin.this, frmAdmin.class);
                        startActivity(adminform);
                    }
                }
                else if (!us.getText().toString().equals(""))
                {
                    if (!pw.getText().toString().equals(""))
                    {
                        Cursor check = myDB.getlogin();
                        if (check.getCount()!=0)
                        {
                            while (check.moveToNext())
                            {
                                if (us.getText().toString().equals(check.getString(2)))
                                {
                                    if (pw.getText().toString().equals(check.getString(3)))
                                    {
                                        if((check.getString(4).equals("Contractor")))
                                        {
                                            Intent contractor = new Intent(frmLogin.this,frmContractor.class);
                                            startActivity(contractor);
                                            us.setText("");
                                            pw.setText("");
                                        }
                                        else if ((check.getString(4).equals("Customer")))
                                        {
                                            Intent customer = new Intent(frmLogin.this,frmCustomer.class);
                                            startActivity(customer);
                                            us.setText("");
                                            pw.setText("");
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(frmLogin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    //Toast.makeText(frmLogin.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(frmLogin.this, "No Details Available", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(frmLogin.this, "Please Enter the Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(frmLogin.this, "Please Enter the Username", Toast.LENGTH_SHORT).show();
                }


            }
        });

        AcCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createuserfrm= new Intent(frmLogin.this, frmCreateAccount.class);
                startActivity(createuserfrm);
            }
        });

    }

}

