package com.example.sdaassign02_2024nickmaher;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String emailAddress;
    String emailBody;
    String emailSubject;


    TextView textView_act;
    TextView textView_cam;
    TextView textView_emailValues;
    TextView textView_pic;

    EditText editText_address;
    EditText editText_body;
    EditText editText_subject;

    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadActivity();
    }
    private void loadActivity(){ //loadActivity is required to re-assign textView functionality on return from email_details layout
        //Open Camera Application
        textView_cam = findViewById(R.id.textView_cam);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }
        textView_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //example of calling a class as per lab
                startActivity(intent);
            }
        });

        //View Gallery
        textView_pic = findViewById(R.id.textView_pic);
        textView_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(gallery);
            }
        });

        //Call an activity
        textView_act = findViewById(R.id.textView_act);
        textView_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.email_layout);
            }
        });
    }

    //email button onClick
    @SuppressLint("MissingInflatedId")
    public void emailSave(View v) {
        editText_address = findViewById(R.id.editText_address);
        editText_subject = findViewById(R.id.editText_subject);
        editText_body = findViewById(R.id.editText_body);

        emailAddress = editText_address.getText().toString();
        emailSubject = editText_subject.getText().toString();
        emailBody = editText_body.getText().toString();

        setContentView(R.layout.activity_main); //return to previous screen
        loadActivity();

        textView_emailValues = findViewById(R.id.textView_emailValues);
        textView_emailValues.setText(emailAddress + "\n" +  emailSubject + "\n" + emailBody);

        sendButton = findViewById(R.id.send_button);
        sendButton.setEnabled(true);
    }

    //send email button onClick
    public void sendEmail(View v) {
        Intent emailAppIntent = new Intent(Intent.ACTION_SEND);
        emailAppIntent.setType("text/plain");
        emailAppIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        emailAppIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailAppIntent.putExtra(Intent.EXTRA_TEXT,emailBody);
        startActivity(Intent.createChooser(emailAppIntent, "Send mail"));
    }
}
