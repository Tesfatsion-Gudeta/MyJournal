package com.example.myjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final JournalClickListener journalClickListener = new JournalClickListener() {
        @Override
        public void onClick(Journal journals) {
            Intent intent = new Intent(MainActivity.this, WritingActivity.class);
            intent.putExtra("savedJournal", journals);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Journal journals, CardView cardView) {

        }
    };
    JournalDB journalDB;
    RecyclerView recyclerView;
    List<Journal> journalList;
    RecyclerViewAdapter recyclerViewAdapter;
    ImageView addButton;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        journalDB = JournalDB.getJournalDB(this);
        recyclerView = findViewById(R.id.homeRecylerView);
        relativeLayout = findViewById(R.id.main);
        addButton = findViewById(R.id.imageButton);

        journalList = journalDB.getJournalDAO().getAllNotes();

        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new RecyclerViewAdapter(this, journalList, journalClickListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WritingActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {

            return;
        }

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Journal journalToBeAdded = (Journal) data.getSerializableExtra("journals");
                if (journalToBeAdded != null) {
                    journalDB.getJournalDAO().add(journalToBeAdded);
                    journalList.clear();
                    journalList.addAll(journalDB.getJournalDAO().getAllNotes());
                    recyclerViewAdapter.notifyDataSetChanged();
                } else {

                    System.out.println("journalToBeAdded is null");
                }
            }
        } else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                Journal updatedJournal = (Journal) data.getSerializableExtra("journals");
                if (updatedJournal != null) {
                    journalDB.getJournalDAO().update(updatedJournal.getId(), updatedJournal.getTitle(), updatedJournal.getNote());
                    journalList.clear();
                    journalList.addAll(journalDB.getJournalDAO().getAllNotes());
                    recyclerViewAdapter.notifyDataSetChanged();
                } else {
                    System.out.println("updatedJournal is null");
                }
            }
        }
    }
}
