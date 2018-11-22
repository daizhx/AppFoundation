package smtech.com.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceToString {
	public static String getExceptionTrace(Throwable e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			pw.close();
			return sw.toString();
		} else {
			String result = "No Exception";
			return result;
		}
	}
}