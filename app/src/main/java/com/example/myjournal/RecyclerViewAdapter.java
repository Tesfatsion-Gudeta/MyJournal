package com.example.myjournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    List<Journal> journalList=new ArrayList<>();
    JournalClickListener listener;

    public RecyclerViewAdapter(Context context, List<Journal> journalList, JournalClickListener listener) {
        this.context = context;
        this.journalList = journalList;
        this.listener = listener;
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

        holder.textDate.setText(journalList.get(position).getDate());
        holder.textDate.setSelected(true);

        holder.textTitle.setText(journalList.get(position).getTitle());

        List<Integer> colors=new ArrayList<>();
        colors.add(R.color.color1);
        colors.add(R.color.color2);
        colors.add(R.color.color3);
        colors.add(R.color.color4);
        colors.add(R.color.color5);
        colors.add(R.color.color8);



        Random color=new Random();
        int randomColor=color.nextInt(colors.size());
        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(colors.get(randomColor)));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onClick(journalList.get(holder.getAdapterPosition()));


            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                listener.onLongClick(journalList.get(holder.getAdapterPosition()),holder.cardView);

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return journalList.size() ;
    }

    public void filterView(List<Journal> filteredList){
        journalList=filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView,cardView2;
        private TextView textDate,textTitle,title2,date2,note2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.journalCardview);
            textDate=itemView.findViewById(R.id.dateText);
            textTitle=itemView.findViewById(R.id.journalTitle);
            cardView2=itemView.findViewById(R.id.journalCardview2);
            title2=itemView.findViewById(R.id.journalTitle2);
            date2=itemView.findViewById(R.id.dateText2);
            note2=itemView.findViewById(R.id.journalNote2);

        }

    }
}
