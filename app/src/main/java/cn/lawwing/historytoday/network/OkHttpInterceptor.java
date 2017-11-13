package cn.lawwing.historytoday.network;

import java.io.IOException;
import java.nio.charset.Charset;

import android.util.Log;

import okhttp3.Connection;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by lawwing on 2017/11/13.
 */
public class OkHttpInterceptor implements Interceptor
{
    
    private static final String TAG = OkHttpInterceptor.class.getSimpleName();
    
    private static final Charset UTF8 = Charset.forName("UTF-8");
    
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        
        Request request = chain.request();
        
        Connection connection = chain.connection();
        
        Protocol protocol = connection != null ? connection.protocol()
                : Protocol.HTTP_1_1;
        
        String requestStartMessage = "--> " + request.method() + ' '
                + request.url() + ' ' + protocol;
        
        if (request.method().equals("GET"))
        {
            
            Log.e("OkHttpInterceptor", requestStartMessage);
        }
        else if (request.method().equals("POST"))
        {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody)
            {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++)
                {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i)
                            + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                Log.e("OkHttpInterceptor",
                        "POST " + request.url() + "\n RequestParams:{"
                                + sb.toString() + "}");
            }
        }
        /** 打印 Response */
        Response response;
        try
        {
            response = chain.proceed(request);
        }
        catch (Exception e)
        {
            throw e;
        }
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        
        if (!bodyEncoded(response.headers()))
        {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            if (contentLength != 0)
            {
                String resp = buffer.clone().readString(charset);
                
                Log.e("OkHttpInterceptor", "Response = " + resp);
                
            }
        }
        
        return response;
    }
    
    private boolean bodyEncoded(Headers headers)
    {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity");
    }
}
