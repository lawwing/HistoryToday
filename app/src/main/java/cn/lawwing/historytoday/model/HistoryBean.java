package cn.lawwing.historytoday.model;

/**
 * Created by lawwing on 2017/11/13.
 */

public class HistoryBean {
    //id
    private String id;
    //日期 19931213
    private String date;
    //月份
    private int month;
    //日期
    private int day;
    //事件描述
    private String event;
    //标题
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", month=" + month +
                ", day=" + day +
                ", event='" + event + '\'' +
                ", title='" + title + '\'' +
                '}' + "\n";
    }
}
