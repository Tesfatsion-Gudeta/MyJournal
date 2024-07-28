package com.example.myjournal;

import androidx.cardview.widget.CardView;

public interface JournalClickListener {

    void onClick(Journal journals);
    void onLongClick(Journal journals, CardView cardView);
}
