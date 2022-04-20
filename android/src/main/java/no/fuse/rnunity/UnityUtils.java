package no.fuse.rnunity;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;

import com.unity3d.player.UnityPlayer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

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

                // start unity
                addUnityViewToBackground();
                player.windowFocusChanged(true);
                player.requestFocus();
                player.resume();

                ready = true;
                callback.onReady();
            }
        });
    }

    public static void addUnityViewToBackground() {
        Log.d("UnityUtils", "addUnityViewToBackground");
        if (player == null) {
            return;
        }
        if (player.getParent() != null) {
            ((ViewGroup) player.getParent()).removeView(player);
        }
        if (true) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            player.setZ(-1f);
        }
        final Activity activity = ((Activity) player.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(1, 1);
        activity.addContentView(player, layoutParams);
    }

    public static void addUnityViewToGroup(ViewGroup group) {
        Log.d("UnityUtils", "addUnityViewToGroup");
        if (player == null) {
            return;
        }
        if (player.getParent() != null) {
            ((ViewGroup) player.getParent()).removeView(player);
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        group.addView(player, 0, layoutParams);
        player.windowFocusChanged(true);
        player.requestFocus();
        player.resume();
    }
}
