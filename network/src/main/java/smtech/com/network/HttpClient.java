package smtech.com.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

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
    //执行http操作的上下文
    private Context context;
    private Handler handler;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public HttpClient(String host,int port){
        this.serverHost = host;
        this.serverPort = port;
        init();
    }

    //默认从HttpServerConfig获取服务器信息
    public HttpClient(){
        init();
    }

    //
    public HttpClient(Context context){
        this.context = context;
        handler = new Handler(context.getMainLooper());
        init();
    }

    private void init(){
        if(serverHost == null || serverHost.equals("")){
            serverHost = HttpServerConfig.getHost();
        }

        if(serverPort == 0) {
            serverPort = HttpServerConfig.getPort();
        }

        if(serverHost == null || "".equals(serverHost)
                || serverPort == 0){
            throw new RuntimeException("未配置服务器信息");
        }
    }

    //获取服务器上的api的完整URL路径
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


    public void post(String json, final HttpRequestCallback callback){
        post(null,json,callback);
    }

    //异步提交数据接口
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

    //异步提交数据接口，请求完成的回调函数在主线程中执行
    public void post2(String api, String json, final HttpRequestCallback callback){
        if(context == null || handler == null){
            throw new RuntimeException("调用该接口必须使用带Context参数的构造方法！");
        }

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(genUrl(api))
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e.getLocalizedMessage());
                    }
                });
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
                final JSONObject finalJson = json;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(finalJson);
                    }
                });

            }
        });
    }



    public String post(String json) throws IOException{
        return post(null,json);
    }

    //同步提交数据
    public String post(String api,String json) throws IOException{
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(genUrl(api)).post(body).build();
        Response res = client.newCall(request).execute();
        return res.body().string();
    }

    //同步提交数据,返回json对象
    public JSONObject post2(String json) throws IOException{
        return post2(null,json);
    }

    //接口返回为json格式数据
    public JSONObject post2(String api,String json) throws IOException{
        String retStr= post(api,json);
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

    //包装返回的json数据,对接口的通用字段进行处理，每个系统的业务接口应该会不一样，所以该方法不是通用方法，只是用来示例。
    public HttpRequestResult post3(String api,String json) throws IOException{
        JSONObject ret = post2(api,json);
        return new HttpRequestResult(ret);
    }
}
