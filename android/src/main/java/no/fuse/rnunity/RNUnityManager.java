package no.fuse.rnunity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
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

public class RNUnityManager extends SimpleViewManager<UnityPlayer2> implements LifecycleEventListener, View.OnAttachStateChangeListener, IUnityPlayerLifecycleEvents {
    public static final String REACT_CLASS = "UnityView";

    static UnityPlayer2 player;

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
    protected UnityPlayer2 createViewInstance(@Nonnull ThemedReactContext reactContext) {
        Log.d("RNUnityManager", "createViewInstance");

        if (player == null) {
            Activity activity = reactContext.getCurrentActivity();
            player = new UnityPlayer2(activity, this);
            player.resume();
        } else {

            if (player.getParent() != null) {
                Log.d("RNUnityManager", "createViewInstance1");
                ((ViewGroup) player.getParent()).removeView(player);
                player.resetParent();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d("RNUnityManager", "createViewInstance2");
                player.setZ(1f);
            }

            player.resume();
        }

        Log.d("RNUnityManager", "createViewInstance3");
        player.addOnAttachStateChangeListener(this);
        player.windowFocusChanged(true);
        player.requestFocus();
        return player;
    }

    @Override
    public void onDropViewInstance(UnityPlayer2 view) {
        Log.d("RNUnityManager", "onDropViewInstance");
        view.removeOnAttachStateChangeListener(this);

        if (view.getParent() != null) {
            Log.d("RNUnityManager", "onDropViewInstance1");
            ((ViewGroup) view.getParent()).removeView(view);
            view.resetParent();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("RNUnityManager", "onDropViewInstance2");
            view.setZ(-1f);
        }

        Log.d("RNUnityManager", "onDropViewInstance3");
        Activity activity = ((Activity) view.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(view.getWidth(), view.getHeight());
        activity.addContentView(view, layoutParams);

        Log.d("RNUnityManager", "onDropViewInstance4");
    }

    @Override
    public void onHostResume() {
        Log.d("RNUnityManager", "onHostResume");
    }

    @Override
    public void onHostPause() {
        Log.d("RNUnityManager", "onHostPause");
    }

    @Override
    public void onHostDestroy() {
        Log.d("RNUnityManager", "onHostDestroy");
    }

    @Override
    public void onViewAttachedToWindow(View view) {
        Log.d("RNUnityManager", "onViewAttachedToWindow");
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        Log.d("RNUnityManager", "onViewDetachedFromWindow");
        Log.d("RNUnityManager", view.getParent().toString());
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
