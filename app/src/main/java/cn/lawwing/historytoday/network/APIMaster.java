package cn.lawwing.historytoday.network;

import android.content.Context;

import cn.lawwing.historytoday.network.api.HistoryAPI;
import cn.lawwing.historytoday.network.entity.HistoryResultModel;
import retrofit2.Call;

/**
 * <pre>
 * 项目名称：surfond
 * 类描述：封装所有的接口方法
 * 创建人：David
 * 创建时间：2016/11/10 18:18
 * 邮箱：zb@clearcom.com.cn
 * </pre>
 */

public class APIMaster
{
    private HistoryAPI mHistoryAPI;
    
    public APIMaster(Context mContext)
    {
        mHistoryAPI = InternetAPIRequest.getInstance(mContext)
                .create(HistoryAPI.class);
    }
    
    public void getHistory(ApiCallback<HistoryResultModel> callback, String key,
            String day)
    {
        Call<HistoryResultModel> mCall = mHistoryAPI.getHistory(key, day);
        mCall.enqueue(new InternetAPICallback<>(callback));
    }
    
}