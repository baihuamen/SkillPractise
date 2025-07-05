package com.github.baihuamen.skillpractise.client.config;

import com.github.baihuamen.skillpractise.client.config.utils.BooleanValue;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置系统
 * <p>
 * 保存你的屏幕配置
 * <p>
 * 保存格式样例：
 * ```json
 * {
 * "TestScreen":{
 * "boolean":true
 * }
 * }
 * ```
 */
public class ConfigManager {

    private static final Map<String, Map<String, Object>> configMap = new HashMap<>();

    private static final Path configPath = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("mods")
            .resolve("SkillPractise");

    private static long lastSaveTime = 0;

    static {
        JsonObject jsonObject = FileWriter.readConfig(configPath.resolve("config.json"));

        // 外层配置，按屏幕分
        jsonObject.entrySet().forEach(entry -> {
            //获取内层配置，按单键值的键值分
            JsonObject configJsonObject = entry.getValue().getAsJsonObject();
            //遍历内层配置，按单键值的键值分
            configJsonObject.entrySet().forEach(configEntry -> {
                if (configEntry.getValue().isJsonPrimitive()) {
                    BooleanValue booleanValue = new BooleanValue(entry.getKey(), configEntry.getKey(), configEntry.getValue()
                            .getAsBoolean());

                    var currentConfigMap = configMap.get(entry.getKey());
                    if (currentConfigMap != null) {
                        currentConfigMap.put(configEntry.getKey(), booleanValue);
                    }
                    else {
                        currentConfigMap = new HashMap<>();
                        currentConfigMap.put(configEntry.getKey(), booleanValue);
                    }
                    configMap.put(entry.getKey(),currentConfigMap);
                }
            });
        });
    }

    public static <T> T config(String screenConfigName, String configName, T defaultValue) {
        if (!configMap.containsKey(screenConfigName)) {
            HashMap<String, Object> anotherConfigMap = new HashMap<>();
            anotherConfigMap.put(configName, defaultValue);
            configMap.put(screenConfigName, anotherConfigMap);
            return defaultValue;
        } else {
            var object = configMap.get(screenConfigName);
            if (object.get(configName) instanceof BooleanValue) {
                return (T) object.get(configName);
            } else if (defaultValue instanceof BooleanValue) {
                object.put(configName, defaultValue);
                return defaultValue;
            }
            return null;
        }
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (System.currentTimeMillis() - lastSaveTime >= 30000) {
                saveConfig();
                lastSaveTime = System.currentTimeMillis();
            }
        });
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            saveConfig();
        });
    }

    public static void saveConfig() {
        JsonObject jsonObject = new JsonObject();
        configMap.forEach((screenConfigName, currentConfigMap) -> {
            JsonObject configJsonObject = new JsonObject();
            currentConfigMap.forEach((configName, configValue) -> {

                if (configValue instanceof BooleanValue) {
                    configJsonObject.addProperty(configName, ((BooleanValue) configValue).value);
                }
                jsonObject.add(screenConfigName, configJsonObject);
            });
        });
        FileWriter.writeConfig(configPath.resolve("config.json"), jsonObject);
    }
}
