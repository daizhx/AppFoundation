package smtech.com.network;

import org.json.JSONObject;

//http请求回调接口
public interface HttpRequestCallback {
    //请求失败
    void onFailure(String failMsg);
    //请求成功
    void onSuccess(JSONObject res);
}
