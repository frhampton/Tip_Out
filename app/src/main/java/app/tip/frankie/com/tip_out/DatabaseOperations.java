package app.tip.frankie.com.tip_out;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Frankie Hampton on 2/6/2016.
 */

/* Id           Date            BILLTOTAL
    1           08-03-2016     $20.00
    2           09-04-2016     $9.00
    3           10-05-2016     $15.00
*
*
* */
public class DatabaseOperations extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="TipOutDataBase.db";
    public static final String TABLE_NAME ="price_info";
    public static final String KEY_ID ="ID";
    public static final String DATE ="DATE";
    public static final String BILL_TOTAL = "BILLTOTAL";
    public static final int database_version =1;
   //Query to create table
   // public String CREATE_QUERY ="create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, BILLTOTAL TEXT)");


    @Override
    public void onCreate(SQLiteDatabase sdb){
        //Creating the Database
        sdb.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE INTEGER,BILLTOTAL INTEGER)");
        Log.d("Database operations", "Table created onCreate");
    }


    // The database
    public DatabaseOperations(Context context) {
        super(context, DATABASE_NAME, null, database_version);
      //  SQLiteDatabase sdb = this.getWritableDatabase();
        Log.d("Database operations", "Database created");

    }



    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion){
        sdb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sdb);
        Log.d("Database operation", "Cake boy on Upgrade");

    }



   public boolean insertData(String date, String bill){ // insert Date and BillTotal // Testing with Intgerser

       SQLiteDatabase sdb = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put(DATE, date);
       contentValues.put(BILL_TOTAL, bill);

       long result = sdb.insert(TABLE_NAME,null,contentValues);

       if(result ==-1) {

           Log.d("Database operation", "Did not insertdata");
           return false;
       } else{

       Log.d("Database operation", "insertData on Click");
       return true;}

   }

    public Cursor getAllData(){
        SQLiteDatabase sdb = this.getWritableDatabase();
        Cursor res= sdb.rawQuery("select * from " + TABLE_NAME,null);
        Log.d("Database operation", "getAddData");
        return res;

    }

    public  Cursor getWeekData(){
        SQLiteDatabase sdb = this.getWritableDatabase();
      //  String[] colums = {DATE,BILL_TOTAL};
        Cursor res= sdb.rawQuery("select * from DATE" + TABLE_NAME+ "WHERE", null);
        Log.d("Database operation", "getWeekDate");
        return res;

    }

    public  Cursor getMonthData(){
        SQLiteDatabase sdb = this.getWritableDatabase();
        Cursor res= sdb.rawQuery("select * from " + TABLE_NAME, null);
        Log.d("Database operation", "getWeekDate");
        return res;

    }

    public  Cursor getYearData(){
        SQLiteDatabase sdb = this.getWritableDatabase();
        Cursor res= sdb.rawQuery("select * from " + TABLE_NAME, null);
        Log.d("Database operation", "getWeekDate");
        return res;

    }


    public Integer deleteData (String id){
        SQLiteDatabase sdb = this.getWritableDatabase();
        return sdb.delete(TABLE_NAME,"ID =?", new String[] {id});

    }


}
