package smtech.com.network;

//http服务器配置类
public class HttpServerConfig {
    private static String host;
    private static int port;

    private static String cmdPath;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        HttpServerConfig.host = host;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        HttpServerConfig.port = port;
    }

    public static String getCmdPath() {
        return cmdPath;
    }

    public static void setCmdPath(String cmdPath) {
        HttpServerConfig.cmdPath = cmdPath;
    }
}
