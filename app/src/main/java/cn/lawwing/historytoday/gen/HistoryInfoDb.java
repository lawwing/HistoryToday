package cn.lawwing.historytoday.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lawwing on 2017/11/13.
 */
@Entity
public class HistoryInfoDb
{
    @Id
    private Long id;
    
    // id
    private String historyid;
    
    // 日期 19931213
    private String date;
    
    // 月份
    private int month;
    
    // 日期
    private int day;
    
    // 事件描述
    private String event;
    
    // 标题
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHistoryid() {
        return this.historyid;
    }

    public void setHistoryid(String historyid) {
        this.historyid = historyid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1091896779)
    public HistoryInfoDb(Long id, String historyid, String date, int month,
            int day, String event, String title) {
        this.id = id;
        this.historyid = historyid;
        this.date = date;
        this.month = month;
        this.day = day;
        this.event = event;
        this.title = title;
    }

    @Generated(hash = 1365693911)
    public HistoryInfoDb() {
    }
    
}
