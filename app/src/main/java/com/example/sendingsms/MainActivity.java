package com.example.sendingsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 0;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtphoneNo = (EditText) findViewById(R.id.editText);
        txtMessage = (EditText) findViewById(R.id.editText2);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED)
                {
                    sendSMS();
                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},0);
                }
            }

            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

                switch (requestCode) {
                    case PERMISSION_CODE: {
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            sendSMS();
                        } else {
                            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                }
            }
            private void sendSMS() {
                String phone=txtphoneNo.getText().toString();
                String message=txtMessage.getText().toString();
                if(!phone.isEmpty() && !message.isEmpty())
                {
                    SmsManager smsManager=SmsManager.getDefault();

                    smsManager.sendTextMessage(phone,null,message,null,null);
                    Toast.makeText(getApplicationContext(),"Message sent successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter phone number and message",Toast.LENGTH_LONG).show();
                }
            }

        });

    }
}

