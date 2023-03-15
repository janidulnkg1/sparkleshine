package com.example.sparkleshine;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

    public class sqlite extends SQLiteOpenHelper {

        public static final String DBname="sparkleshineDB";
        public static final String uTBL="UserDet";
        public static final String oTBL="OrderDet";
        public static final String pTBL="PriceDet";
        public static final String fTBL="FeedbackDet";

        //User Information
        public static final String uCOL1="uID";
        public static final String uCOL2="FName";
        public static final String uCOL3="Username";
        public static final String uCOL4="Password";
        public static final String uCOL5="UserType";

        //Order Information
        public static final String oCOL1="oID";
        public static final String oCOL2="CustomerFName";
        public static final String oCOL3="NoOfRooms";
        public static final String oCOL4="NoOfBathrooms";
        public static final String oCOL5="FloorType";
        public static final String oCOL6="HouseImage";
        public static final String oCOL7="PhoneNumber";
        public static final String oCOL8="orderAddress";
        public static final String oCOL9="orderTotalPrice";



        //Price Information
        public static final String pCOL1="pID";
        public static final String pCOL2="noOfRooms";
        public static final String pCOL3="noOfBathrooms";
        public static final String pCOL4="TiledFCost";
        public static final String pCOL5="ConcreteFCost";
        public static final String pCOL6="LabourCost";

        //Feedback Information
        public static final String fCOL1="fID";
        public static final String fCOL2="FullName";
        public static final String fCOL3="CustomerFeedback";
        public static final String fCOL4="ContractorFeedback";

        public sqlite(@Nullable Context context) {
            super(context, DBname, null, 1);
            SQLiteDatabase db=this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+uTBL+"(uID INTEGER PRIMARY KEY AUTOINCREMENT, FName TEXT,Username TEXT,Password TEXT, UserType TEXT)");
            db.execSQL("CREATE TABLE "+oTBL+"(oID INTEGER PRIMARY KEY AUTOINCREMENT, CustomerFName TEXT,NoOfRooms INTEGER,NoOfBathrooms INTEGER, FloorType TEXT, HouseImage BLOB,PhoneNumber TEXT,orderAddress TEXT,orderTotalPrice INTEGER)");
            db.execSQL("CREATE TABLE "+pTBL+"(pID INTEGER PRIMARY KEY AUTOINCREMENT, noOfRooms INTEGER, noOfBathrooms INTEGER,TiledFCost INTEGER,ConcreteFCost INTEGER, LabourCost INTEGER)");
            db.execSQL("CREATE TABLE "+fTBL+"(fID INTEGER PRIMARY KEY AUTOINCREMENT, FullName TEXT, CustomerFeedback TEXT,ContractorFeedback TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS "+uTBL);
            db.execSQL("DROP TABLE IF EXISTS "+oTBL);
            db.execSQL("DROP TABLE IF EXISTS "+pTBL);
            db.execSQL("DROP TABLE IF EXISTS "+fTBL);

            onCreate(db);
        }


        //method to view all data in the database table
        public Cursor viewAllUsers()
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor request = db.rawQuery("SELECT * FROM "+ uTBL, null);
            return request;
        }

        public Cursor getlogin()
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor Login = db.rawQuery("SELECT * FROM " +uTBL,null);
            return Login;
        }

        public boolean AddUser(String FName, String Username, String Password, String UserType) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues adduser = new ContentValues();
            adduser.put(uCOL2, FName);
            adduser.put(uCOL3, Username);
            adduser.put(uCOL4, Password);
            adduser.put(uCOL5, UserType);

            long result = db.insert(uTBL,null,adduser);

            if (result == -1){
                return false;
            }else{
                return true;
            }
        }

          public boolean AddPrices(String noOfRooms, String noOfBathrooms, String TiledFCost, String ConcreteFCost, String LabourCost) {
               SQLiteDatabase db = this.getWritableDatabase();
               ContentValues addprice = new ContentValues();
              addprice.put(pCOL2, noOfRooms);
              addprice.put(pCOL3, noOfBathrooms);
              addprice.put(pCOL4, TiledFCost);
              addprice.put(pCOL5, ConcreteFCost);
              addprice.put(pCOL6, LabourCost);


               long result = db.insert(pTBL,null,addprice);

               if (result == -1){
                   return false;
               }else{
                   return true;
               }
           }

       public Cursor viewAllPrices()
       {
           SQLiteDatabase db = this.getReadableDatabase();
           Cursor request = db.rawQuery("SELECT * FROM "+ pTBL, null);
           return request;
       }

        public ArrayList<String> getPriceID()
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> list=new ArrayList<String>();
            String strSQL = "SELECT pID FROM "+pTBL;
            Cursor cursor = db.rawQuery(strSQL,null);
            if (cursor.getCount()> 0)
            {
                while (cursor.moveToNext())
                {
                    @SuppressLint("Range") String pid = cursor.getString(cursor.getColumnIndex("pID"));
                    list.add(pid);
                }

            }
            return list;
        }


          public boolean updatePrices(String pID, String noOfRooms, String noOfBathrooms, String TiledFCost, String ConcreteFCost, String LabourCost)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues updateprices = new ContentValues();
            updateprices.put(pCOL2, noOfRooms);
            updateprices.put(pCOL3, noOfBathrooms);
            updateprices.put(pCOL4, TiledFCost);
            updateprices.put(pCOL5, ConcreteFCost);
            updateprices.put(pCOL6, LabourCost);

            db.update(pTBL,updateprices,"pID=?", new String[]{pID});
            return true;
        }

        public Cursor getPriceDetailstospinner(String pid){
            SQLiteDatabase dbspn = this.getReadableDatabase();
            Cursor getpricedetails = dbspn.rawQuery("SELECT * FROM "+pTBL+" WHERE pID="+pid, null);
            return getpricedetails;
        }

                       public boolean SendReviewtoContractor(String FullName, String CustomerFeedback) {
                   SQLiteDatabase db = this.getWritableDatabase();
                   ContentValues addreview = new ContentValues();
                   addreview.put(fCOL2, FullName);
                   addreview.put(fCOL3, CustomerFeedback);

                   long result = db.insert(fTBL,null,addreview);

                   if (result == -1){
                       return false;
                   }else{
                       return true;
                   }
               }


        public boolean SendReviewtoCustomer(String FullName, String ContractorFeedback) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues addreview = new ContentValues();
            addreview.put(fCOL2, FullName);
            addreview.put(fCOL4, ContractorFeedback);

            long result = db.insert(fTBL,null,addreview);

            if (result == -1){
                return false;
            }else{
                return true;
            }
        }

               public Cursor viewAllReviews()
               {
                   SQLiteDatabase db = this.getReadableDatabase();
                   Cursor request = db.rawQuery("SELECT * FROM "+ fTBL, null);
                   return request;
               }

        public boolean AddOrders(String CustomerFname,String NoofRooms,String NoofBathrooms,String FloorType, Bitmap HouseImage, String PhoneNumber, String orderAddress, String orderTotalPrice) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cValue = new ContentValues();
            cValue.put(oCOL2, CustomerFname);
            cValue.put(oCOL3, NoofRooms);
            cValue.put(oCOL4, NoofBathrooms);
            cValue.put(oCOL5, FloorType);
            cValue.put(oCOL7, PhoneNumber);
            cValue.put(oCOL8, orderAddress);
            cValue.put(oCOL9, orderTotalPrice);


            //###############################################

            // Convert the image into byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HouseImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] buffer = baos.toByteArray();
            cValue.put(oCOL6, buffer);

            //###############################################

            long result = db.insert(oTBL, null, cValue);
            if (result == -1) {
                return false;
            } else {
                return true;

            }
        }
            public Cursor viewAllOrders()
            {
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor request = db.rawQuery("SELECT * FROM "+ oTBL, null);
                return request;
            }

        public Cursor getOrdersDetailstospinner(String oid){
            SQLiteDatabase dbspn = this.getReadableDatabase();
            Cursor getorderdetails = dbspn.rawQuery("SELECT oID FROM "+oTBL+" WHERE oID="+oid, null);
            return getorderdetails;
        }

        public ArrayList<String> getOrderID()
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> list=new ArrayList<String>();
            String strSQL = "SELECT oID FROM "+oTBL;
            Cursor cursor = db.rawQuery(strSQL,null);
            if (cursor.getCount()> 0)
            {
                while (cursor.moveToNext())
                {
                    @SuppressLint("Range") String oID = cursor.getString(cursor.getColumnIndex("oID"));
                    list.add(oID);
                }

            }
            return list;
        }

        public Cursor SearchImage(String oID)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor searchImg=db.rawQuery("SELECT HouseImage FROM  "+oTBL+" WHERE oID ='"+oID+"'",null);
            return searchImg;

        }

        public Bitmap getImage(String oID) {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap bt = null;
            Cursor searchimg = db.rawQuery("SELECT img FROM "+oTBL+" WHERE imgNumber ='" + oID + "'", null);
            if (searchimg.moveToNext()) {
                byte[] img = searchimg.getBlob(0);
                bt = BitmapFactory.decodeByteArray(img, 0, img.length);
            }
            return bt;

        }

    }


