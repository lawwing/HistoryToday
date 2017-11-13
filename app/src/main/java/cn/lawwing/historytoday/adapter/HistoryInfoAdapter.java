package cn.lawwing.historytoday.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lawwing.historytoday.HistoryTodayInfoActivity;
import cn.lawwing.historytoday.R;
import cn.lawwing.historytoday.gen.HistoryInfoDb;

/**
 * Created by lawwing on 2017/11/13.
 */

public class HistoryInfoAdapter
        extends RecyclerView.Adapter<HistoryInfoAdapter.HistoryInfoHolder>
{
    private Activity activity;
    
    private ArrayList<HistoryInfoDb> datas;
    
    private LayoutInflater inflater;
    
    public HistoryInfoAdapter(Activity activity, ArrayList<HistoryInfoDb> datas)
    {
        this.activity = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public HistoryInfoHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new HistoryInfoHolder(
                inflater.inflate(R.layout.item_history_layout, parent, false));
    }
    
    @Override
    public void onBindViewHolder(HistoryInfoHolder holder, int position)
    {
        final HistoryInfoDb model = datas.get(position);
        if (model != null)
        {
            holder.dateText.setText(model.getDate());
            holder.titleText.setText(model.getTitle());
            holder.contentText.setText(model.getEvent());
            holder.bossLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    activity.startActivity(HistoryTodayInfoActivity
                            .newInstance(activity, model.getId()));
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        return datas.size();
    }
    
    public class HistoryInfoHolder extends RecyclerView.ViewHolder
    {
        TextView dateText;
        
        TextView titleText;
        
        TextView contentText;
        
        LinearLayout bossLayout;
        
        public HistoryInfoHolder(View itemView)
        {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.dateText);
            contentText = (TextView) itemView.findViewById(R.id.contentText);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            bossLayout = (LinearLayout) itemView.findViewById(R.id.bossLayout);
        }
    }
}
