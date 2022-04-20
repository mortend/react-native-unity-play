package no.fuse.rnunity;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.unity3d.player.UnityPlayer;

import javax.annotation.Nonnull;

public class RNUnityManager extends SimpleViewManager<UnityPlayer> implements LifecycleEventListener, View.OnAttachStateChangeListener {
    public static final String REACT_CLASS = "UnityView";

    public RNUnityManager(ReactApplicationContext reactContext) {
        super();
        reactContext.addLifecycleEventListener(this);
    }

    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    static UnityPlayer player;

    @Nonnull
    @Override
    protected UnityPlayer createViewInstance(@Nonnull ThemedReactContext reactContext) {
        Log.d("RNUnityManager", "createViewInstance");
        final Activity activity = reactContext.getCurrentActivity();
        if (player == null)
            player = new UnityPlayer(activity);
        player.addOnAttachStateChangeListener(this);
        player.windowFocusChanged(true);
        player.requestFocus();
        player.resume();
        return player;
    }

    @Override
    public void onDropViewInstance(UnityPlayer view) {
        Log.d("RNUnityManager", "onDropViewInstance");
        view.removeOnAttachStateChangeListener(this);
        super.onDropViewInstance(view);
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
}
