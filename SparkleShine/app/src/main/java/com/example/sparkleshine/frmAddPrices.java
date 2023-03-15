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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class frmAddPrices extends AppCompatActivity {

    Button back, AddPrice,ViewPrices, UpdatePrice, Clear;
    EditText noOfRooms, noOfBathrooms,TiledFCost,ConcreteFCost,LabourCost;
    Spinner PriceID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_add_prices);

        back=(Button) findViewById(R.id.btnBack2Admin2);
        AddPrice=(Button) findViewById(R.id.btnAddPrice);
        ViewPrices=(Button) findViewById(R.id.btnViewPrice);
        UpdatePrice=(Button) findViewById(R.id.btnUpdatePrice);
        Clear=(Button) findViewById(R.id.btnClearPrice);
        noOfRooms=(EditText) findViewById(R.id.txtNoOFRooms);
        noOfBathrooms=(EditText) findViewById(R.id.txtNoOFBathrooms);
        TiledFCost=(EditText) findViewById(R.id.txtTileFloorCost);
        ConcreteFCost=(EditText) findViewById(R.id.txtConcreteFloorCost);
        LabourCost=(EditText) findViewById(R.id.txtLabourCost);

        PriceID=(Spinner) findViewById(R.id.spPriceID);

        sqlite myDB = new sqlite(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Back = new Intent(frmAddPrices.this, frmAdmin.class);
                startActivity(Back);
            }
        });

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noOfRooms.setText(null);
                noOfBathrooms.setText(null);
                TiledFCost.setText(null);
                ConcreteFCost.setText(null);
                LabourCost.setText(null);


                Toast.makeText(frmAddPrices.this, "All Price Details have been Cleared......", Toast.LENGTH_SHORT).show();
            }
        });

        AddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isAdded = myDB.AddPrices(noOfRooms.getText().toString(),noOfBathrooms.getText().toString(),TiledFCost.getText().toString(),ConcreteFCost.getText().toString(),LabourCost.getText().toString());
                if (isAdded) {
                    Toast.makeText(frmAddPrices.this, "New Price Details have been added", Toast.LENGTH_SHORT).show();

                    //Recall Price ID adding spinner Method
                    ArrayList<String> allIDS = myDB.getPriceID();
                    ArrayAdapter<String> IDAdapter = new ArrayAdapter<String>(frmAddPrices.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, allIDS);
                    PriceID.setAdapter(IDAdapter);
                }
                else
                {
                    Toast.makeText(frmAddPrices.this, "Price Details have not been Added....", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ViewPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor respond = myDB.viewAllPrices();
                if (respond.getCount() == 0) {
                    Toast.makeText(frmAddPrices.this, "Sorry no Price Details Available....!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StringBuffer sb = new StringBuffer();
                    while (respond.moveToNext()) {
                        sb.append("Price ID : " + respond.getString(0) + "\n");
                        sb.append("Cost per Room : " + respond.getString(1) + "\n");
                        sb.append("Cost per Bathroom : " + respond.getString(2) + "\n");
                        sb.append("Tiled Floor Cost : " + respond.getString(3) + "\n");
                        sb.append("Concrete Floor Cost : " + respond.getString(4) + "\n");
                        sb.append("Fixed Labour Cost : " + respond.getString(5) + "\n");
                        sb.append("========END========\n");
                        showPrices("Available Price Details", sb.toString());
                    }
                }
            }
        });




        UpdatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isupdate = myDB.updatePrices(PriceID.getSelectedItem().toString(),noOfRooms.getText().toString(),noOfBathrooms.getText().toString(),TiledFCost.getText().toString(),ConcreteFCost.getText().toString(),LabourCost.getText().toString());
                if(isupdate)
                {
                    Toast.makeText(frmAddPrices.this,"Price Details have been updated...", Toast.LENGTH_LONG).show();
                    noOfRooms.setText(null);
                    noOfBathrooms.setText(null);
                    TiledFCost.setText(null);
                    ConcreteFCost.setText(null);
                    LabourCost.setText(null);

                }
                else
                {
                    Toast.makeText(frmAddPrices.this,"Price Details have not been updated...",Toast.LENGTH_LONG).show();
                }
            }
        });





        //Recall Price ID adding to spinner Method

        ArrayList<String> allIDS = myDB.getPriceID();
        ArrayAdapter<String> IDAdapter = new ArrayAdapter<String>(frmAddPrices.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, allIDS);
        PriceID.setAdapter(IDAdapter);

        PriceID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor receivedPriceDetails = myDB.getPriceDetailstospinner(PriceID.getSelectedItem().toString());
                if (receivedPriceDetails.getCount() == 0) {
                    Toast.makeText(frmAddPrices.this, "Price Details Are not available....", Toast.LENGTH_SHORT).show();

                }else
                {

                    while (receivedPriceDetails.moveToNext())
                    {
                        noOfRooms.setText(receivedPriceDetails.getString(1));
                        noOfBathrooms.setText(receivedPriceDetails.getString(2));
                        TiledFCost.setText(receivedPriceDetails.getString(3));
                        ConcreteFCost.setText(receivedPriceDetails.getString(4));
                        LabourCost.setText(receivedPriceDetails.getString(5));
                    }


                    Toast.makeText(frmAddPrices.this, "Here are Price information....!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    public void showPrices(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
}