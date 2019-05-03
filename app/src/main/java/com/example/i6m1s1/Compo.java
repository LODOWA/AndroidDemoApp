package com.example.i6m1s1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Compo extends AppCompatActivity {




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
        setContentView(R.layout.activity_compo);
        listView = (ListView) findViewById(R.id.compo);
        editText = findViewById(R.id.editTextList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        constraintLayout = findViewById(R.id.constraintLayoutList);

        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for	(Sensor	currentSensor	:	sensor	)	{
            try{
            arrayList.add(currentSensor.getName().substring(9));}catch (Exception ex){
                arrayList.add(currentSensor.getName());
            }; }

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,  arrayList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ListViewDemo.this, "nacisnąles:" + position + " " + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
                //  sparseBooleanArray = listView.getCheckedItemPositions();
            }
        });
    }
}
