package com.example.myjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WritingActivity extends AppCompatActivity {

    private EditText textTitle, textNote;
    private TextView textDate;
    private ImageView saveButton, cancelButton;
    private Journal journal2;
    private boolean savedJournal = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_writing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textTitle = findViewById(R.id.editText_title);
        textDate = findViewById(R.id.editText_date);
        textNote = findViewById(R.id.editText_text);
        saveButton = findViewById(R.id.imageButton5);
        cancelButton = findViewById(R.id.imageButton3);

        SimpleDateFormat format = new SimpleDateFormat("EE, d MM yyyy HH:mm a");
        Date date2 = new Date();

        textDate.setText("  Date: " + format.format(date2));

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("savedJournal")) {
            journal2 = (Journal) intent.getSerializableExtra("savedJournal");
            if (journal2 != null) {
                textDate.setText(journal2.getDate());
                textTitle.setText(journal2.getTitle());
                textNote.setText(journal2.getNote());
                savedJournal = true;
            } else {
                journal2 = new Journal();
            }
        } else {
            journal2 = new Journal();
        }



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title2 = textTitle.getText().toString();
                String note2 = textNote.getText().toString();


                if (note2.isEmpty()) {
                    Toast.makeText(WritingActivity.this, "you haven't written your journal", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!savedJournal) {
                    journal2 = new Journal();
                }
                journal2.setTitle(title2);
                journal2.setDate(format.format(date2));

                journal2.setNote(note2);

                Intent intent = new Intent();
                intent.putExtra("journals", journal2);
                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();


            }
        });

    }


}