package smtech.com.user;

import org.json.JSONObject;

public interface Authentication {
    /**
     * 实现用户登陆，登陆成功设置用户的基本信息到当前用户User对象中
     * @param params
     * @return 登陆结果
     */
    String signIn(String params);
}
