package com.example.sparkleshine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class frmAddOrder extends AppCompatActivity {

    Button Back, BrowseImage, ViewPriceList, CalculateTotalPrice, AddOrder;
    EditText CustomerName,NoOfRooms, NoOfBathrooms,PhoneNumber,OrderAddress, OrderTotalPrice;
    Spinner FloorType;
    ImageView HouseImage;
    //dbCode myDB;
    Bitmap bp;
    int totalprice1,totalprice2,PriceperRoom,PriceperBathroom,NoofRooms,NoofBathrooms,PriceperFloorType1,PriceperFloorType2,Labourcost;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_add_order);

        Back=(Button) findViewById(R.id.btnBack2Customerpage);
        BrowseImage=(Button) findViewById(R.id.btnBrowse);
        ViewPriceList=(Button) findViewById(R.id.btnViewPrices);
        CalculateTotalPrice=(Button) findViewById(R.id.btnCalculateTotPrice);
        AddOrder=(Button) findViewById(R.id.btnAddOrderDetails);
        CustomerName=(EditText) findViewById(R.id.txtCustomerFname);
        NoOfRooms=(EditText) findViewById(R.id.txtNoofRooms);
       NoOfBathrooms=(EditText) findViewById(R.id.txtNoofBathrooms);
        PhoneNumber=(EditText) findViewById(R.id.txtPno);
        OrderAddress=(EditText) findViewById(R.id.txtOrderAddress);
        OrderTotalPrice=(EditText) findViewById(R.id.txtTotalPrice);
        FloorType=(Spinner) findViewById(R.id.spFloorType);
        HouseImage=(ImageView) findViewById(R.id.imgHouse);


        sqlite myDB = new sqlite(this);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerfrm = new Intent(frmAddOrder.this, frmCustomer.class);
                startActivity(customerfrm);
            }
        });

        BrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);

            }
        });
        AddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInsert = myDB.AddOrders(CustomerName.getText().toString(), NoOfRooms.getText().toString(), NoOfBathrooms.getText().toString(), FloorType.getSelectedItem().toString(), bp, PhoneNumber.getText().toString(), OrderAddress.getText().toString(), OrderTotalPrice.getText().toString());

                if (isInsert) {
                    Toast.makeText(frmAddOrder.this, "Order has been Added..!!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(frmAddOrder.this, "Order has not been added..!!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        ViewPriceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor respond = myDB.viewAllPrices();
                if (respond.getCount() == 0) {
                    Toast.makeText(frmAddOrder.this, "Sorry no Price Details Available....!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StringBuffer sb = new StringBuffer();
                    while (respond.moveToNext()) {
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

        CalculateTotalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(frmAddOrder.this, FloorType.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                String roomcost="",bathroomcost="",floorcost="",labourcost="";

           Cursor pricelist = myDB.viewAllPrices();
                if (pricelist.getCount() == 0) {
                    //Toast.makeText(frmAddOrder.this, "Sorry no Price Details Available....!", Toast.LENGTH_SHORT).show();
                    return;
                } else
                {
                    //Toast.makeText(frmAddOrder.this, "1", Toast.LENGTH_SHORT).show();
                    //StringBuffer sb = new StringBuffer();
                     floorcost="0";
                    while (pricelist.moveToNext()) {
                        //Toast.makeText(frmAddOrder.this, "2", Toast.LENGTH_SHORT).show();
                         roomcost=pricelist.getString(1);
                         bathroomcost=pricelist.getString(2);

                        if (FloorType.getSelectedItem().toString().equals("Tiled"))
                        {
                            floorcost=pricelist.getString(3);
                        }
                        else
                        {
                            floorcost=pricelist.getString(4);
                        }
                         labourcost=pricelist.getString(5);
                    }
                    //Toast.makeText(frmAddOrder.this, floorcost, Toast.LENGTH_SHORT).show();
                    int noOfRooms=Integer.parseInt(NoOfRooms.getText().toString());
                    int noOfBathrooms=Integer.parseInt(NoOfBathrooms.getText().toString());
                    int totprice=((noOfRooms*Integer.parseInt(roomcost.toString())+(noOfBathrooms*Integer.parseInt(bathroomcost.toString()))+(Integer.parseInt(floorcost.toString()))+(Integer.parseInt(labourcost.toString()))));
                    OrderTotalPrice.setText(String.valueOf(totprice));

                }




            }
        });




    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri choosenImage = data.getData();

                    if (choosenImage != null) {
                        bp = decodeUri(choosenImage, 400);
                        HouseImage.setImageBitmap(bp);
                    }
                }
        }
    }

    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void showPrices(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

}
