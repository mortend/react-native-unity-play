package no.fuse.rnunity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.UnityPlayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

class UnityPlayer2 extends UnityPlayer {

    public UnityPlayer2(Context context, IUnityPlayerLifecycleEvents iUnityPlayerLifecycleEvents) {
        super(context, iUnityPlayerLifecycleEvents);
        //super(context);
    }

    @Override
    protected void removeDetachedView(View child, boolean animate) {
        Log.d("UnityPlayer2", "removeDetachedView");
        super.removeDetachedView(child, false);
    }

    void resetParent() {
        try {
            Method method = View.class.getDeclaredMethod("assignParent", new Class<?>[]{ ViewParent.class });
            method.setAccessible(true);
            method.invoke(this, new Object[]{ null });
            method.setAccessible(false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

public class RNUnityManager extends SimpleViewManager<UnityView> implements LifecycleEventListener, View.OnAttachStateChangeListener, IUnityPlayerLifecycleEvents {
    public static final String REACT_CLASS = "UnityView";

    public static UnityPlayer2 player;

    public RNUnityManager(ReactApplicationContext reactContext) {
        super();
        reactContext.addLifecycleEventListener(this);
    }

    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Nonnull
    @Override
    protected UnityView createViewInstance(@Nonnull ThemedReactContext reactContext) {
        Log.d("RNUnityManager", "createViewInstance");

        if (player == null) {
            Activity activity = reactContext.getCurrentActivity();
            player = new UnityPlayer2(activity, this);
        } else {
            // We must pause the player to avoid hanging on last frame
            player.pause();

            // Repeat pause/resume after delay to workaround another glitch
            final Activity activity = reactContext.getCurrentActivity();
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("RNUnityManager", "pause/resume player!");
                            player.pause();
                            player.resume();
                        }
                    });
                }
            }, 199);
        }

        if (player.getParent() != null) {
            Log.d("RNUnityManager", "createViewInstance1");
            ((ViewGroup) player.getParent()).removeView(player);
            player.resetParent();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("RNUnityManager", "createViewInstance2");
            player.setZ(1f);
        }

        Log.d("RNUnityManager", "createViewInstance3");

        UnityView view = new UnityView(reactContext.getReactApplicationContext(), player);
        view.addOnAttachStateChangeListener(this);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        view.addView(player, 0, layoutParams);

        player.windowFocusChanged(true);
        player.requestFocus();
        player.resume();

        return view;
    }

    @Override
    public void onDropViewInstance(UnityView view) {
        Log.d("RNUnityManager", "onDropViewInstance");
        view.removeOnAttachStateChangeListener(this);

        if (player.getParent() != null) {
            Log.d("RNUnityManager", "onDropViewInstance1");
            ((ViewGroup) player.getParent()).removeView(view);
            player.resetParent();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("RNUnityManager", "onDropViewInstance2");
            player.setZ(-1f);
        }

        Log.d("RNUnityManager", "onDropViewInstance3");
        Activity activity = ((ReactApplicationContext) view.getContext()).getCurrentActivity();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(1, 1);
        activity.addContentView(player, layoutParams);

        Log.d("RNUnityManager", "onDropViewInstance4");
    }

    @Override
    public void onHostResume() {
        Log.d("RNUnityManager", "onHostResume");

        if (player != null)
            player.resume();
    }

    @Override
    public void onHostPause() {
        Log.d("RNUnityManager", "onHostPause");

        if (player != null)
            player.pause();
    }

    @Override
    public void onHostDestroy() {
        Log.d("RNUnityManager", "onHostDestroy");
    }

    @Override
    public void onViewAttachedToWindow(View view) {
        Log.d("RNUnityManager", "onViewAttachedToWindow: " + view);
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        Log.d("RNUnityManager", "onViewDetachedFromWindow: " + view);
    }

    @Override
    public void onUnityPlayerUnloaded() {
        Log.d("RNUnityManager", "onUnityPlayerUnloaded");
    }

    @Override
    public void onUnityPlayerQuitted() {
        Log.d("RNUnityManager", "onUnityPlayerQuitted");
    }
}
