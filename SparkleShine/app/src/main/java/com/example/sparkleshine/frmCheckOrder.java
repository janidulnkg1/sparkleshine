package com.example.sparkleshine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class frmCheckOrder extends AppCompatActivity {

    Button back,vieworders;
    ImageView HouseImage;
    Spinner OrderID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_check_order);

        back=(Button) findViewById(R.id.btnbacktocontractor2);
        vieworders=(Button) findViewById(R.id.btnViewOrders);
        HouseImage=(ImageView) findViewById(R.id.imgHouse2);
        OrderID=(Spinner) findViewById(R.id.spOrderID);

        sqlite myDB = new sqlite(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(frmCheckOrder.this, frmContractor.class);
                startActivity(back);
            }
        });
        vieworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor respond = myDB.viewAllOrders();
                if (respond.getCount() == 0) {
                    Toast.makeText(frmCheckOrder.this, "Sorry no Orders are Available....!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StringBuffer sb = new StringBuffer();
                    while (respond.moveToNext()) {
                        sb.append("Order ID:"+ respond.getString(0) + "\n");
                        sb.append("Customer Full Name:"+ respond.getString(1) + "\n");
                        sb.append("No Of Rooms:"+ respond.getString(2) + "\n");
                        sb.append("No Of Bathrooms:"+ respond.getString(3) + "\n");
                        sb.append("Floor Type:"+ respond.getString(4) + "\n");
                        //sb.append("House Image:"+ respond.getString(5) + "\n");
                        sb.append("Phone Number:"+ respond.getString(6) + "\n");
                        sb.append("order Address:"+ respond.getString(7) + "\n");
                        sb.append("order Total Price:"+ respond.getString(8) + "\n");
                        sb.append("========END========\n");
                        showOrders("Available Orders ", sb.toString());
                    }
                }
            }
        });


        //Recall Order ID adding to spinner Method
        ArrayList<String> allIDS = myDB.getOrderID();
        ArrayAdapter<String> IDAdapter = new ArrayAdapter<String>(frmCheckOrder.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, allIDS);
        OrderID.setAdapter(IDAdapter);


        /*OrderID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor receivedOrderDetails = myDB.getOrdersDetailstospinner(OrderID.getSelectedItem().toString());
                if (receivedOrderDetails.getCount() == 0) {
                    Toast.makeText(frmCheckOrder.this, "House Image not available....", Toast.LENGTH_SHORT).show();

                }else
                {
                    Cursor res = myDB.SearchImage(OrderID.getSelectedItem().toString());

                    if (res.getCount() == 0) {
                        return;
                    } else {
                        StringBuffer buffer = new StringBuffer();

                        while (res.moveToNext()) {
                            //buffer.append("Image ID : " + res.getString(0) + "\n");

                            //itexttxt.setText(res.getString(1));
                            //buffer.append("Image Name : " + res.getString(1) + "\n");
                            //
                        }
                        HouseImage.setImageBitmap(myDB.getImage(OrderID.getSelectedItem().toString()));

                    }



                    Toast.makeText(frmCheckOrder.this, "Here is the House Image....!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


         */


    }
    public void showOrders(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
}