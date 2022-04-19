package io.github.feltmc.feltapi.api.splash;

import io.github.feltmc.feltapi.impl.splash.SplashImpl;

public class SplashAPI {
    public static void addSplash(String splash) { SplashImpl.splashesToAdd.add(splash); }
    public static void removeSplash(String splash) {
        SplashImpl.splashesToRemove.add(splash);
    }
}