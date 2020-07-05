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
        list.put(Image.apple, "apple");
        list.put(Image.batman, "batman");
        list.put(Image.dog, "dog");
        list.put(Image.facebook, "facebook");
        list.put(Image.git, "git");
        list.put(Image.gitbusters, "gitbusters");
        list.put(Image.gitlab, "gitlab");
        list.put(Image.html, "html");
        list.put(Image.java, "java");
        list.put(Image.pikachu, "pikachu");
        list.put(Image.pistol, "pistol");
        list.put(Image.pringles, "pringles");
        list.put(Image.trophy, "trophy");
        list.put(Image.turtle, "turtle");
        list.put(Image.windows, "windows");


        return list;

    }



}
