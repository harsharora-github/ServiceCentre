package com.example.xyz.servicecentre;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;



public  class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView lv;
    static String sp1, sp2, sp3,tx1;

    private ProgressDialog pDialog;
    private static String url = "http://karbonnklinic.com/wsp.php?action=ASD&country=india&city";
    ArrayList<HashMap<String, String>> servicelist;
    DatabaseHandler task1;
    Spinner spinner1, spinner2, spinner3;
    TextView text,text1,text2,text3,text4;

    String[] countary_array = {"India"};


    String[] state_array = {"AP", "Assam", "Bihar", "Chhattisgarh", "Delhi", "Goa", "Gujrat", "Haryana", "HP", "Jammu And Kashmir", "Jharkhand"
            , "Karnataka", "Kerala", "Maharashtra", "Meghalaya", "Mizoram", "MP", "Nagaland", "Orissa", "Punjab", "Rajasthan", "Sikkim", "Tamilnadu",
            "Telangana", "Tripura", "UP", "Uttarakhand", "West Bengal"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        //  text = (TextView) findViewById(R.id.textViewfordialog);
        lv = (ListView) findViewById(R.id.list);

        // text2 = (TextView) findViewById(R.id.textView7);
        // text3 = (TextView) findViewById(R.id.textView8);
        // text4 = (TextView) findViewById(R.id.textView11);

        //  text.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //  public void onClick(View view) {
        //    final Dialog dialog = new Dialog(MainActivity.this);
        //  dialog.setContentView(R.layout.dialog);


        //dialog.show();
        //        }
        //  });


        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countary_array);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, state_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

        task1 = new DatabaseHandler(this);
        task1.getWritableDatabase();
        task1.getReadableDatabase();


            AsyncTaskClass task = new AsyncTaskClass(this);
            task.execute();

    }

    private void setSupportActionBar(Toolbar myToolbar) {

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

      //  sp1 = String.valueOf(spinner1.getSelectedItem());

        Spinner spinner = (Spinner) arg0;
        if(spinner.getId() == R.id.spinner2){
        sp2 = String.valueOf(spinner2.getSelectedItem());

           List<String> lables = task1.selectRecords(sp2);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lables);
           adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           adapter2.notifyDataSetChanged();
            spinner3.setAdapter(adapter2);
        }
        else if(spinner.getId() == R.id.spinner3) {
            sp3 = String.valueOf(spinner3.getSelectedItem());

            List<String> labless = task1.serviceService(sp3);
           // text.setText(String.valueOf(labless.get(0)));
             ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, labless);
            lv.setAdapter(adapter);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id)  {
                final String item = (String) parent.getItemAtPosition(position);
               // tx1=String.valueOf(text.getText());
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                TextView text1 = (TextView) dialog.findViewById(R.id.textView5);
                List<String> lablessN = task1.serviceName(item);
                text1.setText(String.valueOf(lablessN.get(0)));

                TextView text2 = (TextView) dialog.findViewById(R.id.textView7);
                List<String> lablessA = task1.serviceA(item);
                text2.setText(String.valueOf(lablessA.get(0)));

                TextView text3 = (TextView) dialog.findViewById(R.id.textView9);
                final List<String> lablessE = task1.serviceE(item);
                text3.setText(String.valueOf(lablessE.get(0)));


                text3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                        emailIntent.setType("message/rfc822");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { lablessE.get(0) });
                        startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
                    }
                });

                final TextView text4 = (TextView) dialog.findViewById(R.id.textView11);
                final List<String> lablessNo = task1.serviceNo(item);
                text4.setText(String.valueOf(lablessNo.get(0)));

                text4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = text4.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
                });

                final TextView text5 = (TextView) dialog.findViewById(R.id.textView2);
                final List<String> lablessNoo = task1.serviceNoo(item);
                text5.setText(String.valueOf(lablessNoo.get(0)));

                text5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = text5.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
                });

                Button dialogButton = (Button) dialog.findViewById(R.id.buttonok);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }

        });

    }







    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    }



