package at.fhj.moappdev.n073b00k.helpers;

import android.app.Application;
import at.fhj.moappdev.n073b00k.BuildConfig;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class Log extends Application {

    private static Log instance;
    private static final String TAG = Log.class.getSimpleName();
    private static final String EMPTY = "";

    public static int v(String tag, String format, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.v(tag, format(format, args));
    }

    public static int v(String tag, String msg, Throwable e) {
        Log.log(msg);
        return android.util.Log.v(tag, msg, e);
    }

    public static int v(String tag, String format, Throwable e, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.v(tag, format(format, args), e);
    }

    public static int d(String tag, String format, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.d(tag, format(format, args));
    }

    public static int d(String tag, String msg, Throwable e) {
        Log.log(msg);
        return android.util.Log.d(tag, msg, e);
    }

    public static int d(String tag, String format, Throwable e, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.d(tag, format(format, args), e);
    }

    public static int w(String tag, String format, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.w(tag, format(format, args));
    }

    public static int w(String tag, String msg, Throwable e) {
        Log.log(msg);
        return android.util.Log.w(tag, msg, e);
    }

    public static int w(String tag, String format, Throwable e, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.w(tag, format(format, args), e);
    }

    public static int i(String tag, String format, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.i(tag, format(format, args));
    }

    public static int i(String tag, String msg, Throwable e) {
        Log.log(msg);
        return android.util.Log.i(tag, msg, e);
    }

    public static int i(String tag, String format, Throwable e, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.i(tag, format(format, args), e);
    }

    public static int e(String tag, String format, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.e(tag, format(format, args));
    }

    public static int e(String tag, String msg, Throwable e) {
        Log.log(msg);
        return android.util.Log.e(tag, msg, e);
    }

    public static int e(String tag, String format, Throwable e, Object... args) {
        Log.log(format(format, args));
        return android.util.Log.e(tag, format(format, args), e);
    }

    private static String format(String format, Object... args) {
        try {
            return String.format(format == null ? EMPTY : format, args);
        } catch (RuntimeException e) {
            Log.w(TAG, "format error. reason=%s, format=%s", e.getMessage(), format);
            return String.format(EMPTY, format);
        }
    }

    private Log(String url) {
        this.initSocket(url);
    }

    private Socket socket;

    public Socket getSocket() {
        return this.socket;
    }

    private static void log(String message) {
        Log.getInstance().getSocket().connect().emit("event", message);
    }

    private Socket initSocket(String url) {
        if (url == null) throw new IllegalArgumentException("URL parameter is null.");
        try {
            this.socket = IO.socket(url);
        } catch (URISyntaxException e) {
            android.util.Log.e("Error", "Cannot connect to 5urv31ll4nc3 574710n.");
        }
        return this.socket;
    }

    public static Log getInstance() {
        if (Log.instance == null) Log.instance = new Log(BuildConfig.HOST);
        return Log.instance;
    }

}