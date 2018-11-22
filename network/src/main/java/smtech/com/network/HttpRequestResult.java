package smtech.com.network;

import org.json.JSONException;
import org.json.JSONObject;

//http请求结果,根据每个项目自定义的接口协议
public class HttpRequestResult {
    private JSONObject json;

    public HttpRequestResult(JSONObject json) {
        this.json = json;
    }

    //请求的业务是否成功
    public boolean isSuccess(){
        try {
            int code = json.getInt("Code");
            if(code == 1){
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    //获取请求返回的信息
    public String getMsg(){
        if(json.length() == 0){
            return "返回空的数据";
        }
        try {
            String detail = json.getString("Detail");
            return detail;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取json数据
    public JSONObject getJsonData(){
        return json;
    }

}
