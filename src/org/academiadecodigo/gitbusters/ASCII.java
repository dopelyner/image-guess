package org.academiadecodigo.gitbusters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ASCII {

    private static ConcurrentHashMap<String,String> list;

    public static ConcurrentHashMap<String,String> getList(){
        list = new ConcurrentHashMap<>();

        list.put(Image.bottle,"bottle");
        list.put(Image.moustache,"mustache");
        list.put(Image.shoe,"shoe");
        list.put(Image.book,"book");
        list.put(Image.camera,"camera");
        list.put(Image.car,"car");
        list.put(Image.ghost,"ghost");
        list.put(Image.glasses,"glasses");
        list.put(Image.guitar,"guitar");
        list.put(Image.key,"key");

        return list;

    }



}
