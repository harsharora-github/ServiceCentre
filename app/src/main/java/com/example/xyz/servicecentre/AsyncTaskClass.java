package com.example.xyz.servicecentre;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.list;



public class AsyncTaskClass extends AsyncTask<Void, Void, Void> {

    private Context mContext;

     MainActivity md;

    private static String url = "http://karbonnklinic.com/wsp.php?action=ASD&country=india&city";
    ProgressDialog pDialog;
private static String TAG = "hari";

    public AsyncTaskClass(Context context) {
        mContext = context;
    }
    DatabaseHandler d;

    Cursor cursor;
    String jsonStr;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Showing progress dialog
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        Log.d(TAG, "doInBackground: doinstart");
         d = new DatabaseHandler(mContext);

        if(isOnline() == true) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
             jsonStr = sh.makeServiceCall(url);
        }
        else{
           jsonStr = loadJSONFromAsset();

        }
        Log.d(TAG, "doInBackground: jstostart");
        if (jsonStr != null) {
            Log.d(TAG, "doInBackground: jsstarting");
            try {

                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("userlist");

                // looping through All Contacts
                Log.d(TAG, "doInBackground: jsonparse ");
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    Log.d(TAG, "doInBackground: data-"+i);
                    int j=i+1;
                    int id =j;
                    String name = c.getString("NAME");
                    String city = c.getString("CITY");
                    String state = c.getString("STATE");
                    String countary = c.getString("COUNTRY");
                    String address = c.getString("ADD");
                    String phone_number_1 = c.getString("CONT1");
                    String phone_number_2 = "";
                    if(c.getString("CONT2") == null || c.getString("CONT2") == "") {
                        phone_number_2 = "";
                    }else{
                        phone_number_2 = c.getString("CONT2");
                    }
                    String email = c.getString("EMAIL");
                    String pincode = c.getString("PINCODE");
                    String person = c.getString("CONTACTP");

                    Log.d(TAG, "doInBackground:-"+"name-"+name+",city-"+city+"state-"+state+"countary-"+countary+"add"+address+"phone+number_1"+phone_number_1+"phone_number_2"+phone_number_2+"email"+email+"pincode"+pincode+"person"+person);
                    Log.d(TAG, "doInBackground: datap-"+person);
                    d.createRecords(id,name, city,state, countary, address, phone_number_1,phone_number_2, email, pincode, person);

                }
               // cursor =   d.selectRecords();
                //Toast.makeText(mContext,data, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Log.d(TAG, "doInBackground: "+e.getMessage());
            }

        }
        else{
            Log.d(TAG, "doInBackground: json is null ");
        }
        return null;
    }
    public void onPostExecute(Void result){
        super.onPostExecute(result);
        if (pDialog.isShowing())
            pDialog.dismiss();
       // cursor =   d.selectRecords();
       // List<String> lables = d.selectRecords();
        d.DatabaseClose();
        String data = DatabaseUtils.dumpCursorToString(cursor);
       // Log.d(TAG, data);
     //   Log.d("list", lables.toString());


    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("wsp_modified.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}

