package cn.lawwing.historytoday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lawwing.historytoday.gen.HistoryInfoDb;
import cn.lawwing.historytoday.gen.HistoryInfoDbDao;

public class HistoryTodayInfoActivity extends AppCompatActivity
{
    TextView dateText;
    
    TextView titleText;
    
    TextView contentText;
    
    private long id = 0;
    
    private HistoryInfoDbDao mHistoryInfoDbDao;
    
    private HistoryInfoDb model;
    
    public static Intent newInstance(Activity activity, Long id)
    {
        Intent intent = new Intent(activity, HistoryTodayInfoActivity.class);
        intent.putExtra("id", id);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getIntentData();
        setContentView(R.layout.activity_history_today_info);
        
        dateText = (TextView) findViewById(R.id.dateText);
        contentText = (TextView) findViewById(R.id.contentText);
        titleText = (TextView) findViewById(R.id.titleText);
        mHistoryInfoDbDao = HistoryApp.get()
                .getDaoSession()
                .getHistoryInfoDbDao();
        model = mHistoryInfoDbDao.load(id);
        if (model != null)
        {
            dateText.setText(model.getDate());
            titleText.setText(model.getTitle());
            contentText.setText(model.getEvent());
        }
    }
    
    private void getIntentData()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            if (intent.hasExtra("id"))
            {
                id = intent.getLongExtra("id", 1);
            }
        }
    }
    
}
