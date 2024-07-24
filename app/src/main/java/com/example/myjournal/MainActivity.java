package com.example.myjournal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private JournalDB journalDB;
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ImageView addButton;

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
        RoomDatabase.Callback callback=new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        journalDB= Room.databaseBuilder(getApplicationContext(),JournalDB.class,"journalDB").addCallback(callback).build();



       recyclerView=findViewById(R.id.homeRecylerView);
       relativeLayout=findViewById(R.id.main);
       addButton=findViewById(R.id.imageButton);
       addButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              View view2=getLayoutInflater().inflate(R.layout.journals,relativeLayout,false);
              relativeLayout.addView(view2);

           }
       });
       RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(this);
       ArrayList<JournalModel> journalModel=new ArrayList<>();


       recyclerView.setAdapter(recyclerViewAdapter);
       recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }
}