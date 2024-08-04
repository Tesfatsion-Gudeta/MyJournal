package com.example.myjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.search.SearchBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private final JournalClickListener journalClickListener = new JournalClickListener() {
        @Override
        public void onClick(Journal journals) {
            Intent intent = new Intent(MainActivity.this, WritingActivity.class);
            intent.putExtra("savedJournal", journals);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Journal journals, CardView cardView) {

            tobeDeleted=new Journal();
            tobeDeleted=journals;
            showPopup(cardView);

        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu=new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.pop_up);
        popupMenu.show();
    }

    JournalDB journalDB;
    RecyclerView recyclerView;
    List<Journal> journalList;
    RecyclerViewAdapter recyclerViewAdapter;
    ImageView addButton,settingsButton;
    RelativeLayout relativeLayout;
    SearchView searchView;
    Journal tobeDeleted;
    Toolbar toolbar;



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
        searchView = findViewById(R.id.searching);
//        settingsButton=findViewById(R.id.settingsbutton);

        journalList = journalDB.getJournalDAO().getAllNotes();

        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new RecyclerViewAdapter(this, journalList, journalClickListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WritingActivity.class);
                startActivityForResult(intent, 101);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterResult(newText);
                return true;
            }
        });


        //functionality for the settings button

//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


//
//        try {
//            PopupMenu popupMenu1=new PopupMenu(this,toolbar);
//            popupMenu1.setOnMenuItemClickListener(this);
//            popupMenu1.inflate(R.menu.change_passcode);
//            popupMenu1.show();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


        // Handle toolbar menu clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.changepasscodebutton) {



                    Toast.makeText(MainActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();

                    return true;
                }
                return false;
            }
        });



    }

    private void filterResult(String newText) {
        List<Journal> filteredList=new ArrayList<>();

        for(Journal singleItem:journalList){
            if(singleItem.getTitle().toLowerCase().contains(newText.toLowerCase())||singleItem.getNote().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleItem);


            }
        }
        recyclerViewAdapter.filterView(filteredList);

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

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        if(menuItem.getItemId()==R.id.delete){
            journalDB.getJournalDAO().delete(tobeDeleted);
            journalList.remove(tobeDeleted);
            recyclerViewAdapter.notifyDataSetChanged();

        }
        return false;
    }
}
