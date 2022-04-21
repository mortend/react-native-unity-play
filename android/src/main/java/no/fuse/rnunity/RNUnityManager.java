package no.fuse.rnunity;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.UnityPlayer;

import javax.annotation.Nonnull;

public class RNUnityManager extends SimpleViewManager<UnityPlayer> implements LifecycleEventListener, View.OnAttachStateChangeListener, IUnityPlayerLifecycleEvents {
    public static final String REACT_CLASS = "UnityView";

    static UnityPlayer player;

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
    protected UnityPlayer createViewInstance(@Nonnull ThemedReactContext reactContext) {
        Log.d("RNUnityManager", "createViewInstance");

        if (player == null) {
            Activity activity = reactContext.getCurrentActivity();
            player = new UnityPlayer(activity, this);
            player.resume();
        }

        player.addOnAttachStateChangeListener(this);
        player.windowFocusChanged(true);
        player.requestFocus();
        return player;
    }

    @Override
    public void onDropViewInstance(UnityPlayer view) {
        Log.d("RNUnityManager", "onDropViewInstance");
        view.removeOnAttachStateChangeListener(this);
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
    public void onViewAttachedToWindow(View v) {
        Log.d("RNUnityManager", "onViewAttachedToWindow");
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        Log.d("RNUnityManager", "onViewDetachedFromWindow");
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
