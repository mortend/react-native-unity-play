package no.fuse.rnunity;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.UnityPlayer;

import javax.annotation.Nonnull;

class UnityPlayer2 extends FrameLayout {

    public UnityPlayer2(Context context, IUnityPlayerLifecycleEvents iUnityPlayerLifecycleEvents) {
        //super(context, iUnityPlayerLifecycleEvents);
        super(context);
    }

    @Override
    protected void removeDetachedView(View child, boolean animate) {
        Log.d("UnityPlayer2", "removeDetachedView");
        super.removeDetachedView(child, false);
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
            //player.resume();
        }

        player.addOnAttachStateChangeListener(this);
        //player.windowFocusChanged(true);
        player.requestFocus();
        return player;
    }

    @Override
    public void onDropViewInstance(UnityPlayer2 view) {
        Log.d("RNUnityManager", "onDropViewInstance");
        view.removeOnAttachStateChangeListener(this);

        if (view.getParent() != null) {
            Log.d("RNUnityManager", "onDropViewInstance1");
            ((ViewGroup) view.getParent()).setLayoutTransition(null);

            /*LayoutTransition layoutTransition = ((ViewGroup)view.getParent()).getLayoutTransition();
            layoutTransition.addTransitionListener(new LayoutTransition.TransitionListener(){

                @Override
                public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                    Log.d("RNUnityManager", "startTransition");
                }

                @Override
                public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                    Log.d("RNUnityManager", "endTransition");
                }
            });*/

            ((ViewGroup) view.getParent()).removeView(view);

            for (int i = 0; i < 10 && view.getParent() != null; i++) {
                Log.d("RNUnityManager", "still has parent!?");
                Log.d("RNUnityManager", view.getParent().toString());

                int i1 = ((ViewGroup) view.getParent()).indexOfChild(view);
                int i2 = ((ViewGroup) view.getParent()).indexOfChild(player);
                Log.d("RNUnityManager", "" + i1);
                Log.d("RNUnityManager", "" + i2);

                ((ViewGroup) view.getParent()).removeView(view);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("RNUnityManager", e.toString());
                }
            }

            if (view.getParent() != null) {
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("RNUnityManager", "onDropViewInstance2");
            view.setZ(-1f);
        }

        Log.d("RNUnityManager", "onDropViewInstance3");
        Activity activity = ((Activity) view.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(view.getWidth(), view.getHeight());

        try {
            activity.addContentView(view, layoutParams);
        } catch (Exception e) {
            Log.d("RNUnityManager", e.toString());
        }

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
