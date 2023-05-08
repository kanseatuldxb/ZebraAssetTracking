package com.zebra.rfid.demo.sdksample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.zebra.rfid.api3.TagData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RFIDHandler.ResponseHandlerInterface {

    public TextView statusTextViewRFID = null;
    private TextView textCount,textCount1,textCount2,textCount3;
    private static final int BLUETOOTH_PERMISSION_REQUEST_CODE = 100;

    RFIDHandler rfidHandler;
    final static String TAG = "RFID_SAMPLE";

    ArrayList<String> arrayList = new ArrayList<String>();


    class Datax {

        // Global variables of the class
        String Tag;
        String name;
        int Status;

        // Constructor has type of data that is required
        Datax(String Tag, String name, int Status)
        {
            this.Tag = Tag;
            this.name = name;
            this.Status = Status;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrayList.add("ssss");
        // UI
        statusTextViewRFID = findViewById(R.id.textStatus);

        textCount = findViewById(R.id.textCount);
        textCount1 = findViewById(R.id.textCount1);
        textCount2 = findViewById(R.id.textCount2);
        textCount3 = findViewById(R.id.textCount3);

        // RFID Handler
        rfidHandler = new RFIDHandler();
        rfidHandler.Defaults();
        //Scanner Initializations
        //Handling Runtime BT permissions for Android 12 and higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT},
                        BLUETOOTH_PERMISSION_REQUEST_CODE);
            }else{
                rfidHandler.onCreate(this);
            }

        }else{
            rfidHandler.onCreate(this);
        }


        // set up button click listener
//        Button test = findViewById(R.id.button);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String result = rfidHandler.Test1();
//                testStatus.setText(result);
//            }
//        });

//        Button test2 = findViewById(R.id.button2);
//        test2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String result = rfidHandler.Test2();
//                testStatus.setText(result);
//            }
//        });
//
//        Button defaultButton = findViewById(R.id.button3);
//        defaultButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String result = rfidHandler.Defaults();
//                testStatus.setText(result);
//            }
//        });
        Statusx.add(new Datax("AA0004EF55555555AA1496D0","Asset 1",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496CE","Asset 2",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496D4","Asset 3",1));



//        Statusx.add(new Datax("AA0004EF55555555AA1496D1","Asset 5",1));
//        Statusx.add(new Datax("AA0004EF55555555AA1496CF","Asset 6",1));
//        Statusx.add(new Datax("AA0004EF55555555AA1496D3","Asset 7",1));

//        Statusx.add(new Datax("E200903794070083169066DB","Asset 8",1));
//        Statusx.add(new Datax("E2806995000050067B933D89","Asset 4",1));

//        Statusx.add(new Datax("E2806890000000018A6C9F93","Asset 16",1));
//        Statusx.add(new Datax("E2806890000000001F120A55","Asset 13",1));
//        Statusx.add(new Datax("E2806890000000001F11E993","Asset 14",1));

        Statusx.add(new Datax("AA0004EF55555555AA1496E1","Asset 9",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496E0","Asset 10",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496E3","Asset 11",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496E2","Asset 12",1));
        Statusx.add(new Datax("AA0004EF55555555AA149C73","Asset 15",1));

        Statusx.add(new Datax("AA0004EF55555555AA1496DE","Asset 17",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496D9","Asset 18",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496DD","Asset 19",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496DF","Asset 20",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496D5","Asset 21",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496D2","Asset 22",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496D7","Asset 23",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496D8","Asset 24",1));
        Statusx.add(new Datax("AA0004EF55555555AA1496D6","Asset 25",1));

        PopulateLog(Statusx);
        textCount.setText(Statusx.size() + "");
        textCount1.setText(CountStatus0(Statusx) + "");
        textCount2.setText(CountStatus1(Statusx) + "");
        textCount3.setText(CountStatus2(Statusx) + "");
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == BLUETOOTH_PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                rfidHandler.onCreate(this);
            }
            else {
                Toast.makeText(this, "Bluetooth Permissions not granted", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        rfidHandler.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        String status = rfidHandler.onResume();
        statusTextViewRFID.setText(status);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rfidHandler.onDestroy();
    }

    List<Datax> Statusx = new ArrayList<Datax>();
    @Override
    public void handleTagdata(TagData[] tagData) {
        final StringBuilder sb = new StringBuilder();
        for (int index = 0; index < tagData.length; index++) {
            sb.append(tagData[index].getTagID() + "\n");
            Log.d("sss",tagData[index].getTagID() + "\n");
            int xxx = GetIndexById(Statusx,tagData[index].getTagID());
            if(xxx > -1){
                Statusx.get(xxx).Status = 0;
            }else{
                Snackbar.make(findViewById(android.R.id.content), "Unidentified Tag :" + tagData[index].getTagID(), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
            if(!containsName(Statusx,tagData[index].getTagID())){
                Datax x = new Datax(tagData[index].getTagID(),"Unidentified Tag",2);
                Statusx.add(x);
            }

        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PopulateLog(Statusx);
                textCount.setText(Statusx.size() + "");
                textCount1.setText(CountStatus0(Statusx) + "");
                textCount2.setText(CountStatus1(Statusx) + "");
                textCount3.setText(CountStatus2(Statusx) + "");
            }
        });
    }

    public boolean containsName(final List<Datax> list, final String name){
        return list.stream().filter(o -> o.Tag.equals(name)).findFirst().isPresent();
    }


    public int CountStatus0(final List<Datax> list){
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            int userListName = list.get(i).Status;
            if(userListName == 0){
                count ++;
            }
        }
        return count;
    }

    public int CountStatus1(final List<Datax> list){
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            int userListName = list.get(i).Status;
            if(userListName == 1){
                count ++;
            }
        }
        return count;
    }

    public int CountStatus2(final List<Datax> list){
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            int userListName = list.get(i).Status;
            if(userListName == 2){
                count ++;
            }
        }
        return count;
    }

    public int GetIndexById(final List<Datax> list, final String name){
        for (int i = 0; i < list.size(); i++) {
            String userListName = list.get(i).Tag;
            if(userListName.equals(name) && list.get(i).Status != 2){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void handleTriggerPress(boolean pressed) {
        if (pressed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Statusx.clear();
                    //textCount.setText(Statusx.size() + "");
                }
            });
            rfidHandler.performInventory();
        } else
            rfidHandler.stopInventory();
    }

    RecyclerViewAdapter adapterx;
    RecyclerView recyclerView;

    public void PopulateLog(List<Datax> Status){
        Log.d("Iasd","I am In Report Section");
        recyclerView = (RecyclerView) findViewById(R.id.loglist);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapterx = new RecyclerViewAdapter(MainActivity.this, Status);
        recyclerView.setAdapter(adapterx);
    }
}
