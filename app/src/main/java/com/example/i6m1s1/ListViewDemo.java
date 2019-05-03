package com.example.i6m1s1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ListViewDemo extends AppCompatActivity {


    private ConstraintLayout constraintLayout;
    private EditText editText;
    private ListView listView;
    private int a1;
    private int a2;
    boolean nm;
    SensorManager smm;
    List<Sensor> sensor;
    ListView lv;
    SparseBooleanArray sparseBooleanArray;
  //  TextView item = (TextView) super.getView(position,convertView,parent);

    private static final String FILE_NAME = "example.txt";

    EditText mEditText;

    @Override
    public String toString() {
        return "ListViewDemo{" +
                "arrayList=" + arrayList +
                '}';
    }

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo);
        listView = (ListView) findViewById(R.id.listViewDemo);
        editText = findViewById(R.id.editTextList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        constraintLayout = findViewById(R.id.constraintLayoutList);
        arrayList.add("Ja");
        arrayList.add("Lubie");
        arrayList.add("Placki");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewDemo.this, "nacisnąles:" + position + " " + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
                //  sparseBooleanArray = listView.getCheckedItemPositions();
            }
        });
    }


   /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo);
        listView = (ListView) findViewById(R.id.listViewDemo);
        editText = findViewById(R.id.editTextList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        constraintLayout = findViewById(R.id.constraintLayoutList);

        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for	(Sensor	currentSensor	:	sensor	)	{
            arrayList.add(currentSensor.getName().substring(9)); }

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,  arrayList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ListViewDemo.this, "nacisnąles:" + position + " " + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
                //  sparseBooleanArray = listView.getCheckedItemPositions();
            }
        });
    }*/


    public void addListItem(View view) {
        arrayList.add(editText.getText().toString());
        arrayAdapter.notifyDataSetChanged();
    }

    public void deleteListItem(View view) {
        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
        int itemCount = listView.getCount();
        for (int i = itemCount - 1; i >= 0; i--) {
            if (checkedItemPositions.get(i)) {
                arrayAdapter.remove(arrayList.get(i));
            }
        }
        checkedItemPositions.clear();
        arrayAdapter.notifyDataSetChanged();
        Toast.makeText(this, arrayList.toString(), Toast.LENGTH_LONG).show();
    }

    public void saveToFile(View view) {
        try {
            OutputStreamWriter outWriter = new OutputStreamWriter(openFileOutput("list.txt", Context.MODE_PRIVATE));
            for (int i = 0; i < arrayAdapter.getCount(); i++) {
                outWriter.write(arrayAdapter.getItem(i).toString() + "\n");
            }
            outWriter.close();
            Toast.makeText(this, "zakonczono zapis powodzeniem", Toast.LENGTH_SHORT);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<String> readFromFile() {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            InputStream inputStream = openFileInput("list.txt");
            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String recievedString = "";
                while ((recievedString = bufferedReader.readLine()) != null) {
                    arrayList.add(recievedString);
                }
                inputStream.close();


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

return arrayList;

    }

    public void  getList(View view){
        arrayAdapter.clear();
        arrayAdapter.addAll(readFromFile());
    }



    @Override
    public  void onStart() {


        super.onStart();

        String dane = "";

        Intent fromMainAct = getIntent();
        //Bundle bundle = fromMainAct.getBundleExtra("paczka");
      //  nm = bundle.getBoolean("nm");
     /*   a2 = bundle.getInt("a2");
        dane += String.format(" a1 = %d\n a2 = %d\n", a1, a2);
        dane += String.format(" param1 = %d\n param2 = %d",
                fromMainAct.getIntExtra("parameter1", 0),
                fromMainAct.getIntExtra("parameter2", 0));
        //tV4Params.setText(dane);
*/
        Toast.makeText(this, "Night mode: " + fromMainAct.getBooleanExtra("nm",false) ,Toast.LENGTH_SHORT ).show();

        nightMode();
    }


    @Override
    public void onBackPressed() {

        Intent intent = getIntent();

        intent.putExtra("result", arrayAdapter.getCount());

        this.setResult(RESULT_OK, intent);

        this.finish();

    }

    public void nightMode() {
        Intent fromMainAct = getIntent();
        if( fromMainAct.getBooleanExtra("nm",false)) {
            night();
        } else {
            day();
        }
    }

    public void night(){
        editText.setBackgroundColor(Color.DKGRAY);
        editText.setBackgroundColor(Color.DKGRAY);
        listView.setBackgroundColor(Color.DKGRAY);
       // listView.setTextC(Color.WHITE);
        constraintLayout.setBackgroundColor(Color.DKGRAY);
        editText.setTextColor(Color.WHITE);

    }

    public void  day(){

        editText.setBackgroundColor(Color.WHITE);
        //listView.setTextColor(Color.BLACK);
        constraintLayout.setBackgroundColor(Color.WHITE);
        listView.setBackgroundColor(Color.WHITE);
        editText.setTextColor(Color.BLACK);

    }




}