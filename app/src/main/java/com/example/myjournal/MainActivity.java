package com.example.myjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
    CardView cardView2;
    TextView title2,date2,note2;

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

       recyclerView.setHasFixedSize(true);
       recyclerViewAdapter=new RecyclerViewAdapter(this,journalList,journalClickListener);
       recyclerView.setAdapter(recyclerViewAdapter);
       recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,WritingActivity.class);
                startActivityForResult(intent, 101);

            }
        });

//        cardView2=findViewById(R.id.journalCardview2);
//        title2=findViewById(R.id.journalTitle2);
//        date2=findViewById(R.id.dateText2);
//        note2=findViewById(R.id.journalNote2);



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
            else if(resultCode==Activity.RESULT_CANCELED){

            }
        }}

        private final JournalClickListener journalClickListener=new JournalClickListener() {
            @Override
            public void onClick(Journal journals) {

            }

            @Override
            public void onLongClick(Journal journals, CardView cardView) {

            }
        };






}