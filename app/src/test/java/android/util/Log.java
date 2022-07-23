package android.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

public class Log {

    public static final String V = "V";
    public static final String D = "D";
    public static final String I = "I";
    public static final String W = "W";
    public static final String E = "E";

    public static int v(String tag, String msg) {
        return println(V, tag, msg);
    }
    public static int v(String tag, String msg, Throwable tr) {
        return println(V, tag, msg + '\n' + getStackTraceString(tr));
    }
    public static int d(String tag, String msg) {
        return println(D, tag, msg);
    }
    public static int d(String tag, String msg, Throwable tr) {
        return println(D, tag, msg + '\n' + getStackTraceString(tr));
    }
    public static int i(String tag, String msg) {
        return println(I, tag, msg);
    }
    public static int i(String tag, String msg, Throwable tr) {
        return println(I, tag, msg + '\n' + getStackTraceString(tr));
    }
    public static int w(String tag, String msg) {
        return println(W, tag, msg);
    }
    public static int w(String tag, String msg, Throwable tr) {
        return println(W, tag, msg + '\n' + getStackTraceString(tr));
    }
    public static int w(String tag, Throwable tr) {
        return println(W, tag, getStackTraceString(tr));
    }
    public static int e(String tag, String msg) {
        return println(E, tag, msg);
    }
    public static int e(String tag, String msg, Throwable tr) {
        return println(E, tag, msg + '\n' + getStackTraceString(tr));
    }
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static int println(String priority, String tag, String msg) {
        System.out.println(priority + "/" + tag + ": " + msg);
        return 0;
    }
}