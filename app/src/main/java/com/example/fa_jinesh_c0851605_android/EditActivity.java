package com.example.fa_jinesh_c0851605_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fa_jinesh_c0851605_android.dbHalper;

public class EditActivity extends AppCompatActivity {


    private EditText title;
    private Button editBtn;
    private dbHalper db;
    private Bundle extras ;

    private String latitude ="" ;
    private String longitude ="" ;
    private int testNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = findViewById(R.id.nameEditpage);
        editBtn = findViewById(R.id.buttonEditpage) ;
        db = new dbHalper(this);
        extras = getIntent().getExtras();





        if(extras != null){

            title.setText(extras.getString("title"));
            latitude = extras.getString("latitude");
            longitude = extras.getString("longitude");
         testNum =  extras.getInt("id") ;

        }



        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testNum != 0 && !latitude.isEmpty() && !longitude.isEmpty()){
                    db.editPlace(new Place(testNum,latitude   ,longitude ,
                            title.getText().toString()));

                    Intent intent = new Intent( EditActivity.this,MapsActivity.class );
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
