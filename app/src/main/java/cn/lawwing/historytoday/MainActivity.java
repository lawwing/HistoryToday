package cn.lawwing.historytoday;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.lawwing.historytoday.gen.HistoryInfoDb;
import cn.lawwing.historytoday.gen.HistoryInfoDbDao;
import cn.lawwing.historytoday.model.HistoryBean;
import cn.lawwing.historytoday.network.APIMaster;
import cn.lawwing.historytoday.network.ApiCallback;
import cn.lawwing.historytoday.network.entity.HistoryResultModel;

public class MainActivity extends AppCompatActivity
{
    private TextView showAll;
    
    private EditText monthEdittext;
    
    private EditText dayEdittext;
    
    private Button searchBtn;
    
    private ArrayList<String> dayStrings;
    
    private ArrayList<HistoryBean> historyBeens;
    
    private int count = 0;
    
    private HistoryInfoDbDao mHistoryInfoDbDao;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAll = (TextView) findViewById(R.id.showAll);
        monthEdittext = (EditText) findViewById(R.id.monthEdittext);
        dayEdittext = (EditText) findViewById(R.id.dayEdittext);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        historyBeens = new ArrayList<>();
        
        mHistoryInfoDbDao = HistoryApp.get()
                .getDaoSession()
                .getHistoryInfoDbDao();
        
        if (mHistoryInfoDbDao.loadAll().size() == 0)
        {
            initDayData();
            for (int a = 0; a < dayStrings.size(); a++)
            {
                getHistoryAPI(dayStrings.get(a));
            }
        }
        else
        {
            Toast.makeText(MainActivity.this,
                    mHistoryInfoDbDao.loadAll().size() + "条数据",
                    Toast.LENGTH_LONG).show();
        }
        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                
            }
        });
    }
    
    private void getHistoryAPI(String day)
    {
        APIMaster master = new APIMaster(this);
        master.getHistory(new ApiCallback<HistoryResultModel>()
        {
            @Override
            public void onSuccess(HistoryResultModel response)
            {
                historyBeens.addAll(response.getResult());
                count++;
                if (count == 366)
                {
                    Toast.makeText(MainActivity.this, "加载完毕", Toast.LENGTH_LONG)
                            .show();
                    saveToDb();
                }
            }
            
            @Override
            public void onFailure(Throwable t)
            {
                Toast.makeText(MainActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
                count++;
                if (count == 366)
                {
                    Toast.makeText(MainActivity.this, "加载完毕", Toast.LENGTH_LONG)
                            .show();
                    saveToDb();
                }
            }
        }, "123456", day);
    }
    
    private void saveToDb()
    {
        ArrayList<HistoryInfoDb> allhistory = new ArrayList<>();
        for (HistoryBean bean : historyBeens)
        {
            HistoryInfoDb history = new HistoryInfoDb();
            history.setTitle(bean.getTitle());
            history.setDate(bean.getDate());
            history.setDay(bean.getDay());
            history.setEvent(bean.getEvent());
            history.setMonth(bean.getMonth());
            history.setHistoryid(bean.getId());
            allhistory.add(history);
        }
        mHistoryInfoDbDao.insertOrReplaceInTx(allhistory);
    }
    
    private void initDayData()
    {
        dayStrings = new ArrayList<>();
        for (int i = 1; i < 13; i++)
        {
            switch (i)
            {
                case 1:
                    init31Day(i);
                    break;
                case 2:
                    init29Day(i);
                    break;
                case 3:
                    init31Day(i);
                    break;
                case 4:
                    init30Day(i);
                    break;
                case 5:
                    init31Day(i);
                    break;
                case 6:
                    init30Day(i);
                    break;
                case 7:
                    init31Day(i);
                    break;
                case 8:
                    init31Day(i);
                    break;
                case 9:
                    init30Day(i);
                    break;
                case 10:
                    init31Day(i);
                    break;
                case 11:
                    init30Day(i);
                    break;
                case 12:
                    init31Day(i);
                    break;
            }
        }
    }
    
    private void init30Day(int i)
    {
        for (int j = 1; j < 31; j++)
        {
            String temp = String.format("%02d", i) + String.format("%02d", j);
            dayStrings.add(temp);
        }
    }
    
    private void init29Day(int i)
    {
        for (int j = 1; j < 30; j++)
        {
            String temp = String.format("%02d", i) + String.format("%02d", j);
            dayStrings.add(temp);
        }
    }
    
    private void init31Day(int i)
    {
        for (int j = 1; j < 32; j++)
        {
            String temp = String.format("%02d", i) + String.format("%02d", j);
            dayStrings.add(temp);
        }
    }
}
