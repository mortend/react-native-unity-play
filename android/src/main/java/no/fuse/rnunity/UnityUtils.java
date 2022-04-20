package no.fuse.rnunity;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;

import com.unity3d.player.UnityPlayer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/*
public class UnityUtils {
    public interface CreateCallback {
        void onReady();
    }

    private static UnityPlayer player;
    private static boolean ready;

    public static UnityPlayer getPlayer() {
        Log.d("UnityUtils", "getPlayer");
        if (!ready) {
            return null;
        }
        return player;
    }

    public static boolean isReady() {
        Log.d("UnityUtils", "isReady");
        return ready;
    }

    public static void createPlayer(final Activity activity, final CreateCallback callback) {
        Log.d("UnityUtils", "createPlayer");
        if (player != null) {
            callback.onReady();
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getWindow().setFormat(PixelFormat.RGBA_8888);

                player = new UnityPlayer(activity);

                ready = true;
                callback.onReady();
            }
        });
    }

    static ViewGroup lastGroup;

    public static void addUnityViewToBackground() {
        Log.d("UnityUtils", "addUnityViewToBackground");

        lastGroup.removeAllViews();
        lastGroup = null;

        //player.pause();
    }

    public static void addUnityViewToGroup(ViewGroup group) {
        Log.d("UnityUtils", "addUnityViewToGroup");

        if (lastGroup != null) return;

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        group.addView(player, 0, layoutParams);
        lastGroup = group;

        //player.windowFocusChanged(true);
        //player.requestFocus();
        //player.resume();
    }
}
*/
