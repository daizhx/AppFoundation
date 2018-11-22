package smtech.com.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpClient {

    //通过OkHttpClient实现http请求接口
    private static OkHttpClient client = new OkHttpClient();
    //local server ip
    private String serverHost;
    private int serverPort;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public HttpClient(String host,int port){
        this.serverHost = host;
        this.serverPort = port;
    }

    public HttpClient(){
        serverHost = HttpServerConfig.getHost();
        serverPort = HttpServerConfig.getPort();
        if(serverHost == null || "".equals(serverHost)
                || serverPort == 0){
            throw new RuntimeException("未配置服务器信息");
        }
    }

    private String genUrl(String api){
        if(api == null){
            //不同请求用一个URL转发,通过不同参数来标识不同的请求
            String path = HttpServerConfig.getCmdPath();
            if(!path.startsWith("/")){
                path += "/" + path;
            }
            return "http://" + serverHost + ":" + serverPort + path;
        }
        return "http://" + serverHost + ":" + serverPort +"/" + api;
    }

    //异步提交数据接口
    public void post(String json, final HttpRequestCallback callback){
        post(null,json,callback);
    }

    public void post(String api, String json, final HttpRequestCallback callback){
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(genUrl(api))
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int httpCode = response.code();
                if(httpCode != 200){
                    callback.onFailure(response.message());
                    return;
                }

                String ret = response.body().string();
                JSONObject json = null;
                try {
                    json = new JSONObject(ret);
                } catch (JSONException e) {
                    e.printStackTrace();
                    json = new JSONObject();
                }
                callback.onSuccess(json);
            }
        });
    }

    //同步提交数据
    public String post(String json) throws IOException{
        return post(null,json);
    }

    public String post(String api,String json) throws IOException{
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(genUrl(api)).post(body).build();
        Response res = client.newCall(request).execute();
        return res.body().string();
    }


    public JSONObject post2(String json) throws IOException{
        return post2(null,json);
    }

    public JSONObject post2(String api,String json) throws IOException{
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(genUrl(api)).post(body).build();
        Response res = client.newCall(request).execute();
        String retStr= res.body().string();

        JSONObject ret = null;
        try {
            ret = new JSONObject(retStr);
        } catch (JSONException e) {
            e.printStackTrace();
            ret = new JSONObject();
        }
        return ret;
    }

    public HttpRequestResult post3(String json) throws IOException{
        return post3(null,json);
    }

    public HttpRequestResult post3(String api,String json) throws IOException{
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(genUrl(api)).post(body).build();
        Response res = client.newCall(request).execute();
        String retStr= res.body().string();

        JSONObject ret = null;
        try {
            ret = new JSONObject(retStr);
        } catch (JSONException e) {
            e.printStackTrace();
            ret = new JSONObject();
        }
        return new HttpRequestResult(ret);
    }
}
