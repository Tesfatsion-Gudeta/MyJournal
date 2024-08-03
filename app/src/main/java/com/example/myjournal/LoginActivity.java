package com.example.myjournal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
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
        String passwordNumber=password.getText().toString();
//        Passcode pass=new Passcode();
//        pass.setPasscode("1234");
//        String pass2=pass.getPasscode();
//        if(passwordNumber.equals("4")) {
//
//
//            if (passwordNumber.equals(pass2)) {
//                Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(LoginActivity.this, "Incorrect Passcode", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}