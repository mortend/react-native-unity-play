package no.fuse.rnunity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.unity3d.player.UnityPlayer;

import java.util.concurrent.CopyOnWriteArraySet;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class UnityUtils {
    public interface CreateCallback {
        void onReady();
    }

    private static UnityPlayer unityPlayer;
    private static boolean _isUnityReady;
    private static boolean _isUnityPaused;

    public static UnityPlayer getPlayer() {
        Log.d("UnityUtils", "getPlayer");
        if (!_isUnityReady) {
            return null;
        }
        return unityPlayer;
    }

    public static boolean isUnityReady() {
        Log.d("UnityUtils", "isUnityReady");
        return _isUnityReady;
    }

    public static boolean isUnityPaused() {
        Log.d("UnityUtils", "isUnityPaused");
        return _isUnityPaused;
    }

    public static void createPlayer(final Activity activity, final CreateCallback callback) {
        Log.d("UnityUtils", "createPlayer");
        if (unityPlayer != null) {
            callback.onReady();
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getWindow().setFormat(PixelFormat.RGBA_8888);

                unityPlayer = new UnityPlayer(activity);

                // start unity
                addUnityViewToBackground();
                unityPlayer.windowFocusChanged(true);
                unityPlayer.requestFocus();
                unityPlayer.resume();

                _isUnityReady = true;
                callback.onReady();
            }
        });
    }

    public static void pause() {
        Log.d("UnityUtils", "pause");
        if (unityPlayer != null) {
            unityPlayer.pause();
            _isUnityPaused = true;
        }
    }

    public static void resume() {
        Log.d("UnityUtils", "resume");
        if (unityPlayer != null) {
            unityPlayer.resume();
            _isUnityPaused = false;
        }
    }

    public static void quit() {
        Log.d("UnityUtils", "quit");
        if (unityPlayer != null) {
            unityPlayer.unload();
            _isUnityReady = false;
        }
    }

    public static void addUnityViewToBackground() {
        Log.d("UnityUtils", "addUnityViewToBackground");
        if (unityPlayer == null) {
            return;
        }
        if (unityPlayer.getParent() != null) {
            ((ViewGroup)unityPlayer.getParent()).removeView(unityPlayer);
        }
        if (true) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            unityPlayer.setZ(-1f);
        }
        final Activity activity = ((Activity)unityPlayer.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(1, 1);
        activity.addContentView(unityPlayer, layoutParams);
    }

    public static void addUnityViewToGroup(ViewGroup group) {
        Log.d("UnityUtils", "addUnityViewToGroup");
        if (unityPlayer == null) {
            return;
        }
        if (unityPlayer.getParent() != null) {
            ((ViewGroup)unityPlayer.getParent()).removeView(unityPlayer);
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        group.addView(unityPlayer, 0, layoutParams);
        unityPlayer.windowFocusChanged(true);
        unityPlayer.requestFocus();
        unityPlayer.resume();
    }
}
