package smtech.com.log;

import android.content.Context;
import android.os.Environment;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;


public class LogUtil {

    //配置log4j
    public static void configLog4j(Context context) {
        String pn = context.getPackageName();

        LogConfigurator logConfigurator = new LogConfigurator();
        // 设置文件名
        logConfigurator.setFileName(Environment.getExternalStorageDirectory()
                + File.separator + pn + File.separator + "logs"
                + File.separator + "log4j.txt");
        // 设置root日志输出级别 默认为DEBUG
        logConfigurator.setRootLevel(Level.DEBUG);
        // 设置日志输出级别
        logConfigurator.setLevel("org.apache", Level.ERROR);
        // 设置 输出到日志文件的文字格式 默认 %d %-5p [%c{2}]-[%L] %m%n
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        // 设置输出到控制台的文字格式 默认%m%n
        logConfigurator.setLogCatPattern("%m%n");
        // 设置总文件大小
        logConfigurator.setMaxFileSize(1024 * 1024 * 5);
        // 设置最大产生的文件个数
        logConfigurator.setMaxBackupSize(1);
        // 设置所有消息是否被立刻输出 默认为true,false 不输出
        logConfigurator.setImmediateFlush(true);
        // 是否本地控制台打印输出 默认为true ，false不输出
        logConfigurator.setUseLogCatAppender(true);
        // 设置是否启用文件附加,默认为true。false为覆盖文件
        logConfigurator.setUseFileAppender(true);
        // 设置是否重置配置文件，默认为true
        logConfigurator.setResetConfiguration(true);
        // 是否显示内部初始化日志,默认为false
        logConfigurator.setInternalDebugging(false);

        logConfigurator.configure();

    }
}
