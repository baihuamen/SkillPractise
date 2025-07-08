/*
 * Copyright (C) ${YEAR} github.com/baihuamen/SkillPractise
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * ---
 *
 * 版权所有 (C) ${YEAR} github.com/baihuamen/SkillPractise
 *
 * 本程序是自由软件，您可以自由地重新发布或修改它，但必须遵循
 * GNU通用公共许可证（版本3或更高版本）的条款。
 *
 * 本程序没有任何担保，包括适销性或特定用途适用性的暗示担保。
 * 详情请参阅GNU通用公共许可证。
 *
 * 您应该已经随本程序收到了一份GNU通用公共许可证的副本。如果没有，
 * 请访问 <https://www.gnu.org/licenses/> 查看。
 */

package com.github.baihuamen.skillpractise.client.config;

import com.github.baihuamen.skillpractise.client.config.utils.values.BooleanValue;
import com.github.baihuamen.skillpractise.client.config.utils.values.IntValue;
import com.github.baihuamen.skillpractise.client.config.utils.values.Value;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
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

@Environment(EnvType.CLIENT)
public class ConfigManager {

    private static final Map<String, Map<String, Object>> configMap = new HashMap<>();

    private static final Path configPath = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("mods")
            .resolve("SkillPractise");

    private static long lastSaveTime = 0;

    public static final List<Value> valueTypesList = List.of(
            new BooleanValue(null, null, false),
            new IntValue(null, null, 0)
    );

    static {
        JsonObject jsonObject = FileWriter.readConfig(configPath.resolve("config.json"));
        /*
          配置文件示例
          ```
          {
               "屏幕1":{
                   “键值1": true,
                   "键值2": false,
               },
               "屏幕2": {},
          }
          ```
         */
        // 外层配置，按屏幕分
        jsonObject.entrySet().forEach(entry -> {
            //获取内层配置，按单键值的键值分
            JsonObject configJsonObject = entry.getValue().getAsJsonObject();
            //遍历内层配置，按单键值的键值分
            configJsonObject.entrySet().forEach(configEntry -> {
                if (configEntry.getValue().isJsonPrimitive()) {
                    var valueInFile = configEntry.getValue();
                    Value value = null;
                    for (Value entryType : valueTypesList) {
                        if (entryType.isCurrentValue(valueInFile)) {
                            value = entryType.castToThisValue(entry.getKey(), configEntry.getKey(),valueInFile);
                        }
                    }
                    if (value == null) {
                        Log.error(LogCategory.LOG, "Unknown config type");
                        return;
                    }
                    // 将读取好的键值放入配置表中
                    configMap.putIfAbsent(entry.getKey(), new HashMap<>());
                    var currentConfigMap = configMap.get(entry.getKey());
                    currentConfigMap.put(configEntry.getKey(), value);
                    configMap.put(entry.getKey(), currentConfigMap);
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
            if(!(defaultValue instanceof Value)) return defaultValue;
            boolean isTypeSupported = false;
            for (Value valueType : valueTypesList) {
                for (Object value : object.values()) {
                    if(!(value instanceof Value)) continue;
                    if (valueType.getClass().isInstance(value) && ((Value) value).name().equals(((Value) defaultValue).name())) {
                        isTypeSupported = true;
                        break;
                    }
                }
            }
            if(isTypeSupported){
                return (T) object.get(configName);
            }
            else {
                for (Value valueType : valueTypesList){
                    if(defaultValue.getClass() == valueType.getClass()){
                        configMap.get(screenConfigName).put(configName,defaultValue);
                        return defaultValue;
                    }
                }
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
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> saveConfig());
    }

    public static void saveConfig() {

        JsonObject jsonObject = new JsonObject();
         configMap.forEach((screenConfigName, currentConfigMap) -> {
            JsonObject configJsonObject = new JsonObject();
            currentConfigMap.forEach((configName, configValue) -> {
                valueTypesList.forEach(valueType -> {
                    if (configValue.getClass() == valueType.getClass()) {
                        configJsonObject.add(configName,((Value) configValue).getCurrentValue());
                    }
                });
                jsonObject.add(screenConfigName, configJsonObject);
            });
        });
         FileWriter.writeConfig(configPath.resolve("config.json"), jsonObject);
    }
}
