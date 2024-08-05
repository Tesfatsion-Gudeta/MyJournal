package com.example.myjournal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class SettingActivity extends AppCompatActivity {
    private TextView setPasscodeText;
    private EditText enteredPasscode;
    private Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setPasscodeText = findViewById(R.id.textView2);
        enteredPasscode = findViewById(R.id.editTextNumberPassword2);
        enterButton = findViewById(R.id.enterbutton2);


        //functionality for setting for resetting the passcode
        try {
            SharedPreferences code = getEncryptedSharedPreferences();
            String storedPasscode = code.getString("passcode", "");

            if (!storedPasscode.isEmpty()) {
                setPasscodeText.setText("Enter Passcode");
                enterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (enteredPasscode.getText().toString().equals(storedPasscode)) {
                            setPasscodeText.setText("Set a new passcode");
                            enteredPasscode.getText().clear();

                            enterButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String newPasscode = enteredPasscode.getText().toString();


                                    if (!newPasscode.isEmpty()) {

                                        try {
                                            SharedPreferences encryptedPrefs = getEncryptedSharedPreferences();


                                            // Update a stored value
                                            SharedPreferences.Editor editor = encryptedPrefs.edit();
                                            editor.putString("passcode", newPasscode);
                                            editor.apply();
                                            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } catch (GeneralSecurityException | IOException e) {
                                            e.printStackTrace();

                                        }
                                    } else {
                                        Toast.makeText(SettingActivity.this, "Enter a passcode", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(SettingActivity.this, "Incorrect passcode", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            } else {
                enterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newPasscode = enteredPasscode.getText().toString();


                        if (!newPasscode.isEmpty()) {

                            try {
                                SharedPreferences encryptedPrefs = getEncryptedSharedPreferences();


                                // Update a stored value
                                SharedPreferences.Editor editor = encryptedPrefs.edit();
                                editor.putString("passcode", newPasscode);
                                editor.apply();
                                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();


                            } catch (GeneralSecurityException | IOException e) {
                                e.printStackTrace();

                            }
                        } else {
                            Toast.makeText(SettingActivity.this, "Enter a passcode", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private SharedPreferences getEncryptedSharedPreferences() throws GeneralSecurityException, IOException {
        MasterKey masterKey = new MasterKey.Builder(SettingActivity.this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        return EncryptedSharedPreferences.create(
                SettingActivity.this,
                "JournalAppPrefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

}