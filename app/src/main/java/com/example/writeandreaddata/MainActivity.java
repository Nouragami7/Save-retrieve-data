package com.example.writeandreaddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText message_et,phone_et;
    Button sh_save_btn,sh_retrieve_btn,file_save_btn,file_retrieve_btn;
    public static final String SHARD_PREF_NAME = "Data";
    public static final String MESSANDE_KEY = "Mess_key";
    public static final String PHONE_KEY = "Phone_key";
    public static final String FILE_NAME= "Phone_key";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message_et= findViewById(R.id.message_ed);
        phone_et=findViewById(R.id.phone_ed);
        sh_save_btn = findViewById(R.id.shared_pref_save);
        sh_retrieve_btn= findViewById(R.id.shared_pref_retrieve);
        file_save_btn= findViewById(R.id.F_save);
        file_retrieve_btn=findViewById(R.id.F_retrieve);

        //shared Prefrerance to save data
        sh_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARD_PREF_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String message = message_et.getText().toString();
                String phone = phone_et.getText().toString();
                editor.putString(MESSANDE_KEY,message);
                editor.putString(PHONE_KEY,phone);
                editor.commit();
                message_et.setText("");
                phone_et.setText("");
            }
        });


        //shared Prefrerance to retrieve data
        sh_retrieve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARD_PREF_NAME,MODE_PRIVATE);
                String message = sharedPreferences.getString(MESSANDE_KEY,"No messages are stored");
                String phone = sharedPreferences.getString(PHONE_KEY,"No phones are stored");
                message_et.setText(message);
                phone_et.setText(phone);
            }
        });


        // to write at file
        file_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fileOS = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    DataOutputStream dataOS= new DataOutputStream(fileOS);
                    dataOS.writeUTF(message_et.getText().toString());
                    dataOS.writeUTF(phone_et.getText().toString());

                    dataOS.flush();
                    dataOS.close();
                    fileOS.close();
                    message_et.setText("");
                    phone_et.setText("");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //to read from file
        file_retrieve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fileIS = openFileInput(FILE_NAME);
                    DataInputStream dataIS = new DataInputStream(fileIS);
                    message_et.setText(dataIS.readUTF());
                    phone_et.setText(dataIS.readUTF());
                    dataIS.close();
                    fileIS.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}