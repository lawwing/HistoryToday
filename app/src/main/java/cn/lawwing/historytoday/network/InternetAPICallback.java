package cn.lawwing.historytoday.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <pre>
 * 项目名称：surfond
 * 类描述：Surfond统一Api回调
 * 创建人：David
 * 创建时间：2016/11/10 15:51
 * 邮箱：zb@clearcom.com.cn
 * </pre>
 */

public class InternetAPICallback<T> implements Callback<T> {

    private static final String TAG = InternetAPICallback.class
            .getCanonicalName();

    private ApiCallback<T> mCallback;

    public InternetAPICallback(ApiCallback<T> mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (mCallback == null) {
            throw new NullPointerException("mCallback == null");
        }

        if (response != null && response.body() != null) {
            mCallback.onSuccess(response.body());
        } else {
            mCallback.onFailure(new Throwable("null"));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (mCallback == null) {
            throw new NullPointerException("mCallback == null");
        }
        mCallback.onFailure(t);
    }
}
