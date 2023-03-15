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

public class frmSendReviewtoCustomer extends AppCompatActivity {

    Button Back, ClearReview, SendReview, CheckReviewFeedback;
    EditText Fullname,ReviewtoCustomer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_send_reviewto_customer);

        Back=(Button) findViewById(R.id.btnbacktocontractor);
        ClearReview=(Button) findViewById(R.id.btnClearReview2);
        SendReview=(Button) findViewById(R.id.btnSendReview2customer);
        CheckReviewFeedback=(Button) findViewById(R.id.btnCheckReviewFeedback2);
        ReviewtoCustomer=(EditText) findViewById(R.id.txtReviewtoCustomer);
        Fullname=(EditText) findViewById(R.id.txtContractorName);


        sqlite myDB = new sqlite(this);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(frmSendReviewtoCustomer.this, frmContractor.class);
                startActivity(back);
            }
        });

        ClearReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewtoCustomer.setText(null);
                Fullname.setText(null);


                Toast.makeText(frmSendReviewtoCustomer.this, "Contractor Review has been Cleared......", Toast.LENGTH_SHORT).show();
            }
        });

        SendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isAdded = myDB.SendReviewtoCustomer(Fullname.getText().toString(),ReviewtoCustomer.getText().toString());
                if (isAdded) {
                    Toast.makeText(frmSendReviewtoCustomer.this, "New Contractor Review has been added", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(frmSendReviewtoCustomer.this, "Contractor Review has not been Added....", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CheckReviewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor respond = myDB.viewAllReviews();
                if (respond.getCount() == 0) {
                    Toast.makeText(frmSendReviewtoCustomer.this, "Sorry no Reviews available....!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StringBuffer sb = new StringBuffer();
                    while (respond.moveToNext()) {
                        //sb.append("Review ID : " + respond.getString(0) + "\n");
                        sb.append("Contractor Full Name : " + respond.getString(1) + "\n");
                        sb.append("Customer Review : " + respond.getString(2) + "\n");
                        sb.append("Review from Contractor : " + respond.getString(3) + "\n");
                        sb.append("========END========\n");
                        sb.append("Feedback from Contractor!\n");
                        sb.append("========END========\n");
                        showReviews("Available Reviews", sb.toString());
                    }
                }
            }
        });

       /* UpdateContractorReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isupdate = myDB.updateContractorReview(ReviewID.getSelectedItem().toString(),ReviewtoCustomer.getText().toString());
                if(isupdate)
                {
                    Toast.makeText(frmSendReviewtoCustomer.this,"Contractor Review has been updated...", Toast.LENGTH_LONG).show();
                    ReviewtoCustomer.setText(null);

                }
                else
                {
                    Toast.makeText(frmSendReviewtoCustomer.this,"Contractor Review has not been updated...",Toast.LENGTH_LONG).show();
                }
            }
        });





        //Recall Price ID adding to spinner Method

        ArrayList<String> allIDS = myDB.getReviewID();
        ArrayAdapter<String> IDAdapter = new ArrayAdapter<String>(frmSendReviewtoCustomer.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, allIDS);
        ReviewID.setAdapter(IDAdapter);

        ReviewID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor receivedReviews = myDB.getReviewDetailstospinner(ReviewID.getSelectedItem().toString());
                if (receivedReviews.getCount() == 0) {
                    Toast.makeText(frmSendReviewtoCustomer.this, "Reviews Are not available....", Toast.LENGTH_SHORT).show();

                }else
                {

                    while (receivedReviews.moveToNext())
                    {

                        ReviewtoCustomer.setText(receivedReviews.getString(2));
                    }


                    Toast.makeText(frmSendReviewtoCustomer.this, "Here are Reviews....!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
    */
    }
    public void showReviews(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();



    }
}