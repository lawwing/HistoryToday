package cn.lawwing.historytoday;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.lawwing.historytoday.adapter.HistoryInfoAdapter;
import cn.lawwing.historytoday.gen.HistoryInfoDb;
import cn.lawwing.historytoday.gen.HistoryInfoDbDao;
import cn.lawwing.historytoday.model.HistoryBean;
import cn.lawwing.historytoday.network.APIMaster;
import cn.lawwing.historytoday.network.ApiCallback;
import cn.lawwing.historytoday.network.entity.HistoryResultModel;

public class MainActivity extends AppCompatActivity {
    private TextView showAll;

    private EditText monthEdittext;

    private EditText dayEdittext;

    private Button searchBtn;

    private Button outputBtn;

    private RecyclerView recyclerView;

    private ArrayList<String> dayStrings;

    private ArrayList<HistoryBean> historyBeens;

    private int count = 0;

    private HistoryInfoDbDao mHistoryInfoDbDao;

    private HistoryInfoAdapter adapter;

    private ArrayList<HistoryInfoDb> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAll = (TextView) findViewById(R.id.showAll);
        monthEdittext = (EditText) findViewById(R.id.monthEdittext);
        dayEdittext = (EditText) findViewById(R.id.dayEdittext);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        outputBtn = (Button) findViewById(R.id.outputBtn);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        historyBeens = new ArrayList<>();
        datas = new ArrayList<>();

        initRecycler();
        mHistoryInfoDbDao = HistoryApp.get()
                .getDaoSession()
                .getHistoryInfoDbDao();

        if (mHistoryInfoDbDao.loadAll().size() == 0) {
            showAll.setText("数据正在加载中....");
            initDayData();
            for (int a = 0; a < dayStrings.size(); a++) {
                getHistoryAPI(dayStrings.get(a));
            }
        } else {
            showAll.setText("请输入日期进行查询");
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas.clear();

                datas.addAll(mHistoryInfoDbDao.queryBuilder()
                        .where(HistoryInfoDbDao.Properties.Month
                                        .eq(monthEdittext.getText().toString()),
                                HistoryInfoDbDao.Properties.Day
                                        .eq(dayEdittext.getText().toString()))
                        .list());
                if (datas.size() > 0) {
                    adapter.notifyDataSetChanged();
                    showAll.setVisibility(View.GONE);
                } else {
                    showAll.setText("没有查到数据");
                    showAll.setVisibility(View.VISIBLE);
                }
            }
        });
        // outputBtn.setOnClickListener(new View.OnClickListener()
        // {
        // @Override
        // public void onClick(View view)
        // {
        // Gson gson = new Gson();
        // String string = gson.toJson(mHistoryInfoDbDao.loadAll());
        // FileUtils.writeTxtFile(string,
        // FileManager.getSaveFolder().getAbsolutePath()
        // + "/history.json");
        // }
        // });
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new HistoryInfoAdapter(MainActivity.this, datas);
        recyclerView.setAdapter(adapter);
    }

    private void getHistoryAPI(String day) {
        APIMaster master = new APIMaster(this);
        master.getHistory(new ApiCallback<HistoryResultModel>() {
            @Override
            public void onSuccess(HistoryResultModel response) {
                historyBeens.addAll(response.getResult());
                count++;
                if (count == 366) {
                    showAll.setText("数据加载完毕");
                    saveToDb();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
                count++;
                if (count == 366) {
                    showAll.setText("数据加载完毕");
                    saveToDb();
                }
            }
        }, "123456", day);
    }

    private void saveToDb() {
        ArrayList<HistoryInfoDb> allhistory = new ArrayList<>();
        for (HistoryBean bean : historyBeens) {
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

    private void initDayData() {
        dayStrings = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            switch (i) {
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

    private void init30Day(int i) {
        for (int j = 1; j < 31; j++) {
            String temp = String.format("%02d", i) + String.format("%02d", j);
            dayStrings.add(temp);
        }
    }

    private void init29Day(int i) {
        for (int j = 1; j < 30; j++) {
            String temp = String.format("%02d", i) + String.format("%02d", j);
            dayStrings.add(temp);
        }
    }

    private void init31Day(int i) {
        for (int j = 1; j < 32; j++) {
            String temp = String.format("%02d", i) + String.format("%02d", j);
            dayStrings.add(temp);
        }
    }
}
