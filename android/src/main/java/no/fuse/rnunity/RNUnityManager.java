package no.fuse.rnunity;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.UnityPlayer;

import javax.annotation.Nonnull;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class RNUnityManager extends SimpleViewManager<UnityView> implements LifecycleEventListener, View.OnAttachStateChangeListener, IUnityPlayerLifecycleEvents {
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
    static UnityView singleton;

    @Nonnull
    @Override
    protected UnityView createViewInstance(@Nonnull ThemedReactContext reactContext) {
        Log.d("RNUnityManager", "createViewInstance");

        if (singleton != null) {
            //player.displayChanged(0, null);
            player.requestFocus();
            return singleton;
        }

        if (player == null) {
            Activity activity = reactContext.getCurrentActivity();
            player = new UnityPlayer(activity, this);
            player.resume();
        } else {

        }

        UnityView view = new UnityView(reactContext.getReactApplicationContext());
        view.addOnAttachStateChangeListener(this);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        view.addView(player, 0, layoutParams);

        player.windowFocusChanged(true);
        player.requestFocus();

        singleton = view;
        return view;
    }

    @Override
    public void onDropViewInstance(UnityView view) {
        Log.d("RNUnityManager", "onDropViewInstance");
        return;

        //view.removeOnAttachStateChangeListener(this);
        //view.removeView(player);
        //view.removeAllViews();
        //super.onDropViewInstance(view);

        //player.unload();
        //player = null;
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
