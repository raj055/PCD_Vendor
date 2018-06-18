package com.example.grasp__raj.pcd_vendor;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder extends Fragment {

    private Context context;
    String PDF_FETCH_URL = "http://dert.co.in/gFiles/purchaseorder.php";
    ListView listView;
    Purchaselist_Adepter adapter;
    List<PurchaseData> localdata;
    InputStream is = null;
    String line = null;
    String result = null;
    String[] data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.purchased_order_fregment, container, false);

        listView = (ListView) view.findViewById(R.id.purchased_fregment);


        localdata = new ArrayList<PurchaseData>();

        adapter = new Purchaselist_Adepter(context, localdata, this);
        listView.setAdapter(adapter);

        //Allow network in main thread
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        get_Po_List();

        adapter.notifyDataSetChanged();

        //setting listView on item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
    }

    private void get_Po_List() {

        try {
            URL url = new URL(PDF_FETCH_URL);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            is = new BufferedInputStream(con.getInputStream());

        }catch (Exception e){
            e.printStackTrace();
        }

        //Read in content into String
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null)
            {
                sb.append(line+"\n");
            }

            is.close();
            result = sb.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        //Parse json data
        try {

            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            data = new String[ja.length()];

            for (int i=0; i<ja.length();i++){

                jo=ja.getJSONObject(i);
                String id = jo.getString("id");
                String name = jo.getString("name");
                String urlname = jo.getString("url");
                String email = jo.getString("email");
                String bill = jo.getString("Billing");
                String purchseOrder = jo.getString("purchaseorder");
                String completeOrder = jo.getString("completeorder");

                PurchaseData purchaseData= new PurchaseData(id,name,urlname,email,bill,purchseOrder,completeOrder);
                localdata.add(purchaseData);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
