package smtech.com.user;

import android.content.Context;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import smtech.com.network.HttpClient;

import static android.text.TextUtils.isEmpty;

//当前用户,封装用户相关的操作
public class User {
    private static User ourInstance;
    public static final String SUCCESS = "success";
    public static final String EXCEPTION = "程序异常";
    public static final String DONE_NOTHING = "dn";

    //身份认证器
    private Authentication authentication;

    //应用的context
    private Context applicationContext;

    //用户基本信息,不能保存密码等重要信息
    private String token;//登录成功后系统发放的token，用于鉴权和身份确定
    private String userName;
    private String nickName;
    private String email;

    public static User getInstance(Context context) {
        if(ourInstance == null){
            synchronized (User.class){
                if(ourInstance == null){
                    context.getApplicationContext();
                    ourInstance = new User(context.getApplicationContext());
                }
            }
        }
        return ourInstance;
    }

    private User(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    //md5加密密码
    public static String md5(String string) {
        if (isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //用户名密码登陆
    public String signIn(String userName,String pwd){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName",userName);
            jsonObject.put("pwd",md5(pwd));
        } catch (JSONException e) {
            e.printStackTrace();
            return EXCEPTION;
        }

        return authentication != null ? authentication.signIn(jsonObject.toString()) : "未登录";
    }

    //邮箱登陆
    public String signInWithEmail(String email,String pwd){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",userName);
            jsonObject.put("pwd",md5(pwd));
        } catch (JSONException e) {
            e.printStackTrace();
            return EXCEPTION;
        }

        return authentication != null ? authentication.signIn(jsonObject.toString()) : "未登录";
    }

    //手机号登陆
    public String signInWithTel(String tel,String pwd){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tel",userName);
            jsonObject.put("pwd",md5(pwd));
        } catch (JSONException e) {
            e.printStackTrace();
            return EXCEPTION;
        }

        return authentication != null ? authentication.signIn(jsonObject.toString()) : "未登录";
    }

    //自动登陆,注册完成后可调用该方法实现自动登录
    public void autoSignIn(){
        //TODO
    }


    //注册
    public void signUp(){
        //TODO
    }


    //退出登陆
    public void SignOut(){
        token = null;
        userName = null;
        nickName = null;
        email = null;
    }
}
