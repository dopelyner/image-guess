package org.academiadecodigo.gitbusters;

import java.util.ArrayList;
import java.util.Collections;

public class ASCII {

    private ArrayList<String> list = new ArrayList();

    public void populateList(){
        list = new ArrayList<>();

        list.add(Image.separator);
        list.add(Image.imageGuess);

        Collections.shuffle(list);

    }


    public ArrayList<String> getList() {
        return list;
    }

}
