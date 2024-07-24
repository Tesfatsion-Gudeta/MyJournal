package com.example.myjournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    ArrayList<JournalModel> journalModel=new ArrayList<>();

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.journals,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return journalModel.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView textDate,textNote;
        private EditText textTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.journalCardview);
            textDate=itemView.findViewById(R.id.dateText);
            textTitle=itemView.findViewById(R.id.journalTitle);
            textNote=itemView.findViewById(R.id.jouranlText);
        }

    }
}
