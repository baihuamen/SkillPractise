package com.github.baihuamen.skillpractise.client.screen;

import java.util.HashMap;
import java.util.Map;

public class Typesetter {

    // Map<屏幕名称,Map<组件名称,组件的编号>>
    public static Map<String, Map<String, Integer>> typeList = new HashMap<>();

    public static Map<String, Integer> screenComponentIndexMap = new HashMap<>();

    public static int getY(String screen, String name) {
        //如果已有键值，我们就不需要在计算新的键值
        if (typeList.containsKey(screen) && typeList.get(screen).containsKey(name)) {
            return typeList.get(screen).get(name) * 50 + 10;
        } else {
            screenComponentIndexMap.putIfAbsent(screen, 0);
            var index = screenComponentIndexMap.get(screen);
            index++;
            screenComponentIndexMap.put(screen, index);
            typeList.putIfAbsent(screen, new HashMap<>());
            typeList.get(screen).putIfAbsent(name, index);
            return index * 50 + 10;
        }
    }
}
