package com.example.pqi13.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");

        //DRS 20160822b - Added 1
        System.loadLibrary("opencv_java3");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DRS 20160822a - Added 2
        TextView textView = (TextView)findViewById(R.id.sample_text);
        textView.setText(stringFromJNI());

        //DRS 20160822b - Added 'if/else'
        if (!OpenCVLoader.initDebug()) {
            textView.setText(textView.getText() + "\n OpenCVLoader.initDebug(), not working.");
        } else {
            textView.setText(textView.getText() + "\n OpenCVLoader.initDebug(), WORKING.");
            //DRS 20160822c Added 1
            textView.setText(textView.getText() + "\n" + validate(0L, 0L));
        }




        // Write up the button to do stuff
        // .... get the button
        Button face_btn = findViewById(R.id.face_button);
        Button circule_btn = findViewById(R.id.circue_button);
        Button feature_btn = findViewById(R.id.feature_button);


        //... set what happend when the button is pressed
        face_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            Intent i = new Intent(getApplicationContext(),OpenCVFaceDetection.class);
            startActivity(i);

            }
        });

        //... set what happend when the button is pressed
        circule_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent j = new Intent(getApplicationContext(),OpenCVCucire.class);
                startActivity(j);

            }
        });

        //... set what happend when the button is pressed
        feature_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent k = new Intent(getApplicationContext(),OpencvFeatureSelection.class);
                startActivity(k);

            }
        });

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    //DRS 20160822c - Added 1
    public native String validate(long matAddrGr, long matAddrRgba);

}