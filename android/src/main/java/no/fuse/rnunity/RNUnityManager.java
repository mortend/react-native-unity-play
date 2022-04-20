package no.fuse.rnunity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import javax.annotation.Nonnull;

public class RNUnityManager extends SimpleViewManager<UnityView> implements LifecycleEventListener, View.OnAttachStateChangeListener {
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

    @Nonnull
    @Override
    protected UnityView createViewInstance(@Nonnull ThemedReactContext reactContext) {
        Log.d("RNUnityManager", "createViewInstance");
        final UnityView view = new UnityView(reactContext);
        view.addOnAttachStateChangeListener(this);

        if (UnityUtils.getPlayer() != null) {
            view.setUnityPlayer(UnityUtils.getPlayer());
        } else {
            UnityUtils.createPlayer(reactContext.getCurrentActivity(), new UnityUtils.CreateCallback() {
                @Override
                public void onReady() {
                    view.setUnityPlayer(UnityUtils.getPlayer());
                }
            });
        }
        return view;
    }

    @Override
    public void onDropViewInstance(UnityView view) {
        Log.d("RNUnityManager", "onDropViewInstance");
        view.removeOnAttachStateChangeListener(this);
        super.onDropViewInstance(view);
    }

    @Override
    public void onHostResume() {
        Log.d("RNUnityManager", "onHostResume");
        if (UnityUtils.isUnityReady()) {
            UnityUtils.getPlayer().resume();
            restoreUnityUserState();
        }
    }

    @Override
    public void onHostPause() {
        Log.d("RNUnityManager", "onHostPause");
        if (UnityUtils.isUnityReady()) {
            // Don't use UnityUtils.pause()
            UnityUtils.getPlayer().pause();
        }
    }

    @Override
    public void onHostDestroy() {
        Log.d("RNUnityManager", "onHostDestroy");
        if (UnityUtils.isUnityReady()) {
            UnityUtils.getPlayer().unload();
        }
    }

    private void restoreUnityUserState() {
        Log.d("RNUnityManager", "restoreUnityUserState");
        // restore the unity player state
        if (UnityUtils.isUnityPaused()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (UnityUtils.getPlayer() != null) {
                        UnityUtils.getPlayer().pause();
                    }
                }
            }, 300); //TODO: 300 is the right one?
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        Log.d("RNUnityManager", "onViewAttachedToWindow");
        restoreUnityUserState();
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        Log.d("RNUnityManager", "onViewDetachedFromWindow");
    }
}
