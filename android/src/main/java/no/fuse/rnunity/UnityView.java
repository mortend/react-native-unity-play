package no.fuse.rnunity;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayer;

public class UnityView extends FrameLayout {
    private UnityPlayer player;

    public UnityView(Context context) {
        super(context);
    }

    public void setUnityPlayer(UnityPlayer player) {
        this.player = player;
        UnityUtils.addUnityViewToGroup(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (player != null) {
            player.windowFocusChanged(hasWindowFocus);
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.configurationChanged(newConfig);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        UnityUtils.addUnityViewToBackground();
        super.onDetachedFromWindow();
    }
}
