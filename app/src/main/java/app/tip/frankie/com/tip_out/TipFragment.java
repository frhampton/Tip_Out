package app.tip.frankie.com.tip_out;



import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Frankie Hampton on 2/5/2016.
 */

//notes for fragments where ever you see this replace with getActivity()
public class TipFragment extends Fragment  {


    String tot, formattedDate;
    View rootView;
    Button btnSave;
    double dbBacontent, dbTipcontnet, dbBaamount, dbTipamount;
    Context ctx;
    DatabaseOperations myDb;

    EditText editText_bill_amount,editText_tip_percent, editText_tip_amount,editText_num_of_people,editText_total_per_person;
    TextView tvtotal, tvtipamount;



    public static TipFragment newInstance() { // set up a newinstance of the Fragment in TipFragment
        TipFragment fragment = new TipFragment();

        return fragment;

    }



    //looks out for when users inputs int's in editTExt
    public TextWatcher watcher = new TextWatcher(){
        int numOfPpl;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            if(editText_bill_amount !=null){
                dbBacontent = Double.parseDouble(!editText_bill_amount.getText().toString().equals("")?
                "0"+editText_bill_amount.getText().toString():"0");
            }

            if(editText_tip_percent !=null){
                dbTipcontnet = Double.parseDouble(!editText_tip_percent.getText().toString().equals("")?
                editText_tip_percent.getText().toString():"0");

            }
            if(editText_num_of_people !=null){
                numOfPpl = Integer.parseInt(!editText_num_of_people.getText().toString().equals("")?
                editText_num_of_people.getText().toString():"1");

            }

            if(numOfPpl != 0) {
                dbBaamount = (dbBacontent + (dbBacontent * (dbTipcontnet * .01))) / numOfPpl;
            }else{
                dbBaamount= 0;
            }

            dbTipamount = (dbBacontent * (dbTipcontnet*.01));
            tot = String.format("%.2f",dbBaamount);
            String tipTot = String.format("%.2f", dbTipamount);
            tvtotal.setText(tot);
            tvtipamount.setText("Tip amount \n" + tipTot);
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public TipFragment(){ // TipFragment Construct

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //This is where you initialize your views
                             Bundle SavedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_tip,container,false);

        myDb= new DatabaseOperations(getActivity());

        //Get the date
        //Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(new Date());




        editText_bill_amount= (EditText)rootView.findViewById(R.id.editText_bill_amount);
        editText_bill_amount.addTextChangedListener(watcher);

        editText_tip_percent=(EditText) rootView.findViewById(R.id.editText_tip_percent);
        editText_tip_percent.addTextChangedListener(watcher);

        editText_num_of_people=(EditText) rootView.findViewById(R.id.editText_num_of_people);
        editText_num_of_people.addTextChangedListener(watcher);

        tvtotal =(TextView) rootView.findViewById(R.id.tv_total);
        tvtipamount =(TextView) rootView.findViewById(R.id.tv_tip_amount);

        btnSave =(Button) rootView.findViewById(R.id.btn_save_to_data);
        btnSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Test", "onClickListener is gestartet");
                //When the button gets Clicked
                if (dbBaamount == 0) {
                    Toast.makeText(getActivity(), "Will not Save when your Total is $0.00",
                            Toast.LENGTH_SHORT).show();
                } else {
                 boolean isInserted= myDb.insertData(formattedDate,tot);// insert Current Date and Current bill total in TipOutDataBase.db
                        if(isInserted = true) {
                            editText_bill_amount.setText("");
                            editText_tip_percent.setText("");
                            editText_num_of_people.setText("");
                            Toast.makeText(getActivity(), "Bill Amounts Saved to DB",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Bill Amounts Saved didn't save to DB",
                                    Toast.LENGTH_SHORT).show();
                        }
                }



            }
        });

        return rootView;
    }//end of our TipFragment


    public void onStart(){

        super.onStart();

    }

}
