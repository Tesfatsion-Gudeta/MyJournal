package com.example.myjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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

        journalDB=JournalDB.getJournalDB(this);
       recyclerView=findViewById(R.id.homeRecylerView);
       relativeLayout=findViewById(R.id.main);
       addButton=findViewById(R.id.imageButton);


       journalList =journalDB.getJournalDAO().getAllNotes() ;


       recyclerViewAdapter=new RecyclerViewAdapter(this,journalList);
       recyclerView.setAdapter(recyclerViewAdapter);
       recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,WritingActivity.class);
                startActivityForResult(intent,101);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if(resultCode==Activity.RESULT_OK){
                Journal journalTobeAdded= (Journal) data.getSerializableExtra("journal");
                journalDB.getJournalDAO().add(journalTobeAdded);
                journalList.clear();
                journalList.addAll(journalDB.getJournalDAO().getAllNotes());
                recyclerViewAdapter.notifyDataSetChanged();


            }
        }

    }
}