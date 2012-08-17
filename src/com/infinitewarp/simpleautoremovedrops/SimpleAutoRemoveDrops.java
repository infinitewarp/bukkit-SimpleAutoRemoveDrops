package com.infinitewarp.simpleautoremovedrops;

import org.bukkit.plugin.java.JavaPlugin;

public class SimpleAutoRemoveDrops extends JavaPlugin {

    private int warningCount;
    private int clearCount;
    private int millisecondsBetweenCheck;

    @Override
    public void onEnable() {
        initialize();
        new ItemSpawnEventListener(this);
    }

    private void initialize() {
        warningCount = getConfig().getInt("warningCount");
        clearCount = getConfig().getInt("clearCount");
        millisecondsBetweenCheck = getConfig().getInt("millisecondsBetweenCheck");


        getLogger().info("warningCount is " + warningCount);
        getLogger().info("clearCount is " + clearCount);
        getLogger().info("millisecondsBetweenCheck is " + millisecondsBetweenCheck);

        if (warningCount >= clearCount) {
            getLogger().warning("clearCount is not greater than warningCount; no warning will be given before removal");
        }
    }

    public int getWarningCount() {
        return warningCount;
    }

    public int getClearCount() {
        return clearCount;
    }

    public int getMillisecondsBetweenCheck() {
        return millisecondsBetweenCheck;
    }
}
