package com.zebra.rfid.demo.sdksample;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  implements View.OnLongClickListener {

    private List<MainActivity.Datax> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<MainActivity.Datax> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.log_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.LogIDn.setText(mData.get(position).Tag);
        holder.LogName.setText(mData.get(position).name);
        if(mData.get(position).Status == 0){
            holder.LogColor.setBackgroundColor(Color.parseColor("#FF7ac1ab"));
        }else if(mData.get(position).Status == 1){
            holder.LogColor.setBackgroundColor(Color.parseColor("#23739839"));
        }else{
            holder.LogColor.setBackgroundColor(Color.parseColor("#45623164"));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (view.getId() == holder.LogIDn.getId()) {
            mData.remove(holder.getPosition());

            notifyDataSetChanged();

            Toast.makeText(view.getContext(), "Item " + holder.LogIDn.getText() + " has been removed from list",
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView LogIDn,LogName;
        View LogColor;

        ViewHolder(View itemView) {
            super(itemView);
            LogIDn = itemView.findViewById(R.id.LogIDn);
            LogName = itemView.findViewById(R.id.LogName);
            LogColor = itemView.findViewById(R.id.LogColor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).Tag;
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}