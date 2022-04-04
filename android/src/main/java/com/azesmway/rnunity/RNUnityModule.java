
package com.azesmway.rnunity;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNUnityModule extends ReactContextBaseJavaModule {
    static RNUnityModule instance;

    // Called by C#
    public static RNUnityModule getInstance() {
        return instance;
    }

    public RNUnityModule(ReactApplicationContext reactContext) {
        super(reactContext);
        instance = this;
    }

    @Override
    public String getName() {
        return "UnityNativeModule";
    }

    @ReactMethod
    public void isReady(Promise promise) {
        promise.resolve(UnityUtils.isUnityReady());
    }

    @ReactMethod
    public void createUnity(final Promise promise) {
        UnityUtils.createPlayer(getCurrentActivity(), new UnityUtils.CreateCallback() {
            @Override
            public void onReady() {
                promise.resolve(true);
            }
        });
    }

    // Called by C#
    public void emitEvent(String name, String data) {
        ReactContext context = getReactApplicationContext();
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(name, data);
    }

    @ReactMethod
    public void sendMessage(String gameObject, String methodName, String message) {
        UnityUtils.sendMessage(gameObject, methodName, message);
    }

    @ReactMethod
    public void pause() {
        UnityUtils.pause();
    }

    @ReactMethod
    public void resume() {
        UnityUtils.resume();
    }

    @ReactMethod
    public void quit() {
        UnityUtils.quit();
    }
}
