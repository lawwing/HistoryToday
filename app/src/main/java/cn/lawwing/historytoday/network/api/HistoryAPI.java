package cn.lawwing.historytoday.network.api;

import cn.lawwing.historytoday.network.entity.HistoryResultModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lawwing on 2017/11/13.
 */

public interface HistoryAPI
{
    @GET("appstore/history/query")
    Call<HistoryResultModel> getHistory(@Query("key") String key,
            @Query("day") String day);
}
