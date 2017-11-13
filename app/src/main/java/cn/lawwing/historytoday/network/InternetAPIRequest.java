package cn.lawwing.historytoday.network;

import static cn.lawwing.historytoday.network.InterfaceParameters.DEFAULT_TIMEOUT;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.zhy.http.okhttp.OkHttpUtils;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 * 项目名称：surfond
 * 类描述：网络请求
 * 创建人：David
 * 创建时间：2016/11/10 16:36
 * 邮箱：zb@clearcom.com.cn
 * </pre>
 */

public class InternetAPIRequest
{
    private static InternetAPIRequest instance;
    
    private Context mContext;
    
    private OkHttpClient okHttpClient;
    
    private InternetAPIRequest(Context context)
    {
        this.mContext = context;
        
        /** 在响应请求之后在 data/data/<包名>/cache OkHttpCache 文件夹，保持缓存数据。 */
        File httpCacheDirectory = new File(mContext.getCacheDir(),
                "OkHttpCache");
        
        /** 官方日志 */
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        
        /** 打印信息类型的等级，分别是NONE，BASIC，HEADERS，BODY */
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                .addInterceptor(new OkHttpInterceptor())
                .retryOnConnectionFailure(true)
                .addInterceptor(logging)
                .build();
        // 把这对代码放到OkHttpUtils里面去
        OkHttpUtils.initClient(okHttpClient);
        
    }
    
    public Retrofit RetrofitClientFactoryCommon()
    {
        
        return new Retrofit.Builder()
                .baseUrl(InterfaceParameters.REQUEST_HTTP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
    
    public static InternetAPIRequest getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new InternetAPIRequest(context);
        }
        return instance;
    }
    
    // 建议是拆开写了，但是很久，如果只是实现功能，可以在这里加一个类似上面这个获取单利的方法，返回不同的instance,zhege
    // instance持有的retrofit对象创建的时候和上面的差不多，把URl改了，但是这么些是很垃圾的，要分开写，但是这个狗比把这个类写这么大，分开写会有很多重复代码，你把他获取okthhpCline的代码抽出去，然后复用，大概就是这样。
    
    public <T> T create(Class<T> service)
    {
        return RetrofitClientFactoryCommon().create(service);
    }
    
}
