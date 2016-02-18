package app.tip.frankie.com.tip_out;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Eve on 2/5/2016.
 */
public class RunningTotalFragment extends Fragment {

    DatabaseOperations myDb;
    View rootView;
    EditText etId;
    Button dbpull, delbutton;



    public static RunningTotalFragment newInstance() { // set up a newinstance of the Fragment in RunningTotalFragment
        RunningTotalFragment fragment = new RunningTotalFragment();
        return fragment;

    }

    public RunningTotalFragment(){ // RunningTotalFragment Construct

    }


    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle SavedInstanceState){
        myDb= new DatabaseOperations(getActivity());
        rootView = inflater.inflate(R.layout.fragment_running_total,container,false);

        delbutton = (Button) rootView.findViewById(R.id.button_deleteData);
        delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.deleteData(etId.getText().toString());



                if(deleteRows > 0)

                    Toast.makeText(getActivity(), "Row is deleted ",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Row not deleted",
                            Toast.LENGTH_SHORT).show();


            }
        });


        dbpull=(Button) rootView.findViewById(R.id.button_datapull);
        etId = (EditText) rootView.findViewById(R.id.edtextshow);
        dbpull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        Cursor res =myDb.getAllData();
                            if(res.getCount() ==0){
                                //show message
                                showMessage("Error ", "Nothing found ");
                                return;
                            }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("ID: " + res.getString(0) + "\n");
                            buffer.append("Date: " + res.getString(1) + "\n");
                            buffer.append("Bill Price: $" + res.getString(2) +"\n \n");
                            //Log.d("RunningTotalFragment ", "This is the buffer.append while loop");

                        }
                    // Show all data
                showMessage("Data ",buffer.toString());
            }
        });
       // viewAll();

        return rootView;
    }//end of our RunningTotalFragment
}
