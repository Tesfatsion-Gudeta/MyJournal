package com.example.myjournal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private Button enterButton;

    private static JournalDB journalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        password=findViewById(R.id.editTextNumberPassword);
        enterButton=findViewById(R.id.enterbutton);


        try {
            SharedPreferences encryptedPrefs = getEncryptedSharedPreferences();
            String storedPasscode = encryptedPrefs.getString("passcode", "");


            if (storedPasscode.equals("")) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Error accessing encrypted preferences", Toast.LENGTH_SHORT).show();
        }





        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordNumber=password.getText().toString();

                try {
                    SharedPreferences encryptedPrefs = getEncryptedSharedPreferences();
                    String storedPasscode = encryptedPrefs.getString("passcode", "");


                    if (passwordNumber.equals(storedPasscode)) {
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect passcode", Toast.LENGTH_SHORT).show();
                    }
                } catch (GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error accessing encrypted preferences", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    private SharedPreferences getEncryptedSharedPreferences() throws GeneralSecurityException, IOException {
        MasterKey masterKey = new MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        return EncryptedSharedPreferences.create(
                this,
                "JournalAppPrefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    }
