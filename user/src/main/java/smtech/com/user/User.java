package smtech.com.user;

import android.content.Context;

import java.lang.ref.WeakReference;

//登陆的用户
public class User {
    private static User ourInstance;
    private static final String SUCCESS = "success";

    //应用的context
    private Context applicationContext;
    //登录成功后系统发放的token，用于鉴权和身份确定
    private String token;
    //登陆成功后保存用户基本信息,不能保存密码等重要信息
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



    //用户名密码登陆
    public void signIn(String userName,String pwd){
        //TODO
    }

    //邮箱登陆
    public void signInWithEmail(String email,String pwd){
        //TODO
    }

    //手机号登陆
    public void signInWithTel(String tel,String pwd){
        //TODO
    }

    //自动登陆,注册完成后可调用该方法实现自动登录
    public void autoSignIn(){
        //TODO
    }


    //注册
    public void signUp(){
        //TODO
    }
}
