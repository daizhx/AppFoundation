package smtech.com.user;

//登陆的用户
public class User {
    private static final User ourInstance = new User();
    private static final String SUCCESS = "success";
    //登录成功后系统发放的token，用于鉴权和身份确定
    private String token;
    //登陆成功后保存用户基本信息,不能保存密码等重要信息
    private String userName;
    private String nickName;



    public static User getInstance() {
        return ourInstance;
    }

    private User() {
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

    //自动登陆,比如微信支付宝登陆一次后不需要重新登陆
    public void autoSignIn(){
        //TODO
    }


    //注册
    public void signUp(){
        //TODO
    }
}
