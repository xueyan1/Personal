package com.xueyan.personal.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class HashMapTest {
    public static void main(String[] args) {
        HashMap<Integer, User> hashMap = new HashMap<Integer, User>();
        User user1 = new User();
        user1.setAge(32);
        user1.setName("zhangsan");
        User user2 = new User();
        user2.setAge(23);
        user2.setName("lisi");
        User user3 = new User();
        user3.setAge(54);
        user3.setName("wangwu");
        hashMap.put(1, user1);
        hashMap.put(2, user2);
        hashMap.put(3, user3);
        System.out.println("排序前" + hashMap);
        HashMap<Integer, User> hashMap1 = sortHashMap(hashMap);
        System.out.println("排序前" + hashMap1);
    }

    private static HashMap<Integer, User> sortHashMap(HashMap<Integer, User> hashMap) {


        LinkedHashMap<Integer, User> sortLinkHashMap =  new LinkedHashMap<Integer, User>();


        Set<Map.Entry<Integer, User>> entrySet = hashMap.entrySet();
        ArrayList<Map.Entry<Integer, User>> list =  new ArrayList<>(entrySet);
        Collections.sort(list, new Comparator<Map.Entry<Integer, User>>() {
                    @Override
                    public int compare(Map.Entry<Integer, User> lhs, Map.Entry<Integer, User> rhs) {
                        return rhs.getValue().getAge()-lhs.getValue().getAge();
                    }
                }
        );
        for (int i = 0; i <list.size() ; i++) {
            sortLinkHashMap.put(list.get(i).getKey(),list.get(i).getValue());
        }

        return sortLinkHashMap;

    }


}
