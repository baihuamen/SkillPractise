package com.github.baihuamen.skillpractise.client.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileWriter {

    private static final Gson GSON = new Gson();  // 使用静态常量避免重复创建

    /**
     * 读取JSON配置文件
     *
     * @param path 文件路径
     * @return 解析后的JsonObject，如果文件不存在或解析失败返回空JsonObject
     */
    public static JsonObject readConfig(Path path) {
        if (!Files.exists(path)) {
            return new JsonObject();
        }
        try (FileReader reader = new FileReader(path.toFile())) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonObject()) {
                return jsonElement.getAsJsonObject();
            }
            return new JsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonObject();
        }
    }

    /**
     * 写入JSON配置文件
     *
     * @param path       文件路径
     * @param jsonObject 要写入的JSON对象
     */
    public static void writeConfig(Path path, JsonObject jsonObject) {
        try {
            String jsonString = GSON.toJson(jsonObject);
            if (path.getParent() != null && !Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.write(
                    path,
                    jsonString.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}