package com.example.i6m1s1;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private EditText editText;
    private Button button;
    private Button button2;
    private ScrollView scrollView;
    private ConstraintLayout constraintLayout;
    private Switch theSwitch;
    private TextClock textClock;
    private CheckBox checkBox;
    private RadioButton radioButtonLondon;
    private RadioButton radioButtonWarsaw;
    private ImageView imageView;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button10);
        button2 = findViewById(R.id.button2);
        constraintLayout = findViewById(R.id.constraintLayout);
        theSwitch = findViewById(R.id.theSwitch);
        textClock = findViewById(R.id.textClock);
        radioButtonLondon = findViewById(R.id.London);
        radioButtonWarsaw = findViewById(R.id.Warsaw);
        imageView = findViewById(R.id.imageView);
        scrollView = findViewById(R.id.scroll);
        root = findViewById(R.id.scroll);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(this, "The device has no light sensor !", Toast.LENGTH_SHORT).show();
            finish();
        }

        // max value for light sensor
        maxValue = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                getSupportActionBar().setTitle("Luminosity : " + value + " lx");
                if (theSwitch.isChecked()) {
                    if (value < 40) {
                        night();

                    } else {
                        day();
                    }
                }
                // between 0 and 255
                // int newValue = (int) (255f * value / maxValue);
                //root.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    Bundle b1 = data.getExtras();
                    Toast.makeText(this,
                            String.format("result = %d",
                                    b1.getInt("result")),
                            Toast.LENGTH_LONG).show();

                  //  textView.setText(String.format("Ilosc elementow listy: = %d", b1.getInt("result")));
                }
                break;
            case 0:
                if (resultCode == RESULT_OK) {
                    // super.onActivityResult(requestCode, resultCode, data);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    //  Uri selectedImage1 = data.getData();
                    // imageView.setImageURI(selectedImage1);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;
            default:
               // textView.setText(String.format("Nie dziala"));

                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }

    public void onClick2(View view) {
        openActivity2();
    }

    private void openActivity2() {
        Intent go2ActIntent = new Intent(MainActivity.this, ListViewDemo.class);
        go2ActIntent.putExtra("nm", theSwitch.isChecked());
        startActivityForResult(go2ActIntent, 1234);
    }

    public void nightMode(View view) {
        if (((ColorDrawable) scrollView.getBackground()).getColor() == Color.WHITE) {
        //    night();
        } else {
          //  day();
        }
    }

    public void night() {
        constraintLayout.setBackgroundColor(Color.DKGRAY);
        // textView.setBackgroundColor(Color.DKGRAY);
        // textView.setTextColor(Color.WHITE);
        editText.setTextColor(Color.WHITE);
         theSwitch.setTextColor(Color.WHITE);
        textClock.setTextColor(Color.WHITE);
        radioButtonLondon.setTextColor(Color.WHITE);
        radioButtonWarsaw.setTextColor(Color.WHITE);
        scrollView.setBackgroundColor(Color.DKGRAY);
    }

    public void day() {
        // textView.setBackgroundColor(Color.WHITE);
        // textView.setTextColor(Color.BLACK);
        constraintLayout.setBackgroundColor(Color.WHITE);
        editText.setTextColor(Color.BLACK);
        theSwitch.setTextColor(Color.BLACK);
        textClock.setTextColor(Color.BLACK);
        radioButtonWarsaw.setTextColor(Color.BLACK);
        radioButtonLondon.setTextColor(Color.BLACK);
        scrollView.setBackgroundColor(Color.WHITE);
    }

    public void onRadioButtonClicked(View view) {
        textClock.setTimeZone("GMT+00:00");
    }

    public void onRadioButtonClicked2(View view) {
        textClock.setTimeZone("GMT+01:00");
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        imageView.setVisibility(View.VISIBLE);
    }


    public void pickFromGallery(View view) {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, 1);
        imageView.setVisibility(View.VISIBLE);
    }

    public void searchInGoogle2(View view) {

        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, editText.getText().toString());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void openListOfComponents(View view) {
        Intent go2ActIntent = new Intent(MainActivity.this, Compo.class);
        go2ActIntent.putExtra("nm", theSwitch.isChecked());
        startActivityForResult(go2ActIntent, 1111);
    }


    public void autoNM(View view) {
        Intent go2ActIntent = new Intent(MainActivity.this, Test.class);

        MainActivity.this.startActivity(go2ActIntent);
    }


}









