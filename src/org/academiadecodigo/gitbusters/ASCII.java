package org.academiadecodigo.gitbusters;


import java.util.concurrent.ConcurrentHashMap;

public class ASCII {

    private static ConcurrentHashMap<String, String> list;

    public static ConcurrentHashMap<String, String> getList() {
        list = new ConcurrentHashMap<>();

        list.put("bottle", Image.bottle);
        list.put("moustache", Image.moustache);
        list.put("shoe", Image.shoe);
        list.put("book", Image.book);
        list.put("camera", Image.camera);
        list.put("car", Image.car);
        list.put("ghost", Image.ghost);
        list.put("glasses", Image.glasses);
        list.put("guitar", Image.guitar);
        list.put("key", Image.key);

        return list;

    }


}
