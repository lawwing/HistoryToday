package cn.lawwing.historytoday.network;

/**
 * <pre>
 * 项目名称：surfond
 * 类描述：API回调
 * 创建人：David
 * 创建时间：2016/11/10 15:48
 * 邮箱：zb@clearcom.com.cn
 * </pre>
 */

public interface ApiCallback<T> {

    /**
     * 数据请求成功
     */
    void onSuccess(T response);

    /**
     * 数据请求失败
     */
    void onFailure(Throwable t);
}
