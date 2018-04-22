package com.example.shaban.alarmdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shaban.alarmdemo.R;
import com.example.shaban.alarmdemo.model.Alarm;

import java.util.List;


public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {
    private List<Alarm> mAlarms;
    OnItemLongClickListener longClickListener;
    OnItemClickListener clickListener;

    public AlarmListAdapter(List<Alarm> mAlarms) {
        this.mAlarms = mAlarms;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alarm, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Alarm alarm = mAlarms.get(position);
        holder.tvTitle.setText(alarm.getAlarmTitle());
        holder.tvTime.setText(alarm.getHour()+":"+alarm.getMinute());

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(longClickListener != null) {
                    return longClickListener.onItemLongClicked(position);
                } else {
                    return false;
                }
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) {
                    clickListener.onItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvTitle;
        public TextView tvTime;
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
        }
    }


    public void setOnItemLongClickLister(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public void update(List<Alarm> alarm) {
        this.mAlarms.clear();
        this.mAlarms.addAll(alarm);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        public void onItemClicked(int position);
    }


    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }
}
