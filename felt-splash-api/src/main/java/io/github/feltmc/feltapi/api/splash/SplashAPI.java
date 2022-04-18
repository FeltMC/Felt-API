package io.github.feltmc.feltapi.api.splash;

import java.util.ArrayList;
import java.util.List;

public class SplashAPI {
    public static List<String> splashesToAdd = new ArrayList<>();
    public static List<String> splashesToRemove = new ArrayList<>();

    public static void addSplash(String splash) { splashesToAdd.add(splash); }
    public static void removeSplash(String splash) {
        splashesToRemove.add(splash);
    }
}