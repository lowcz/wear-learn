package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel on 2017-04-23.
 */

public class Tag {
    private List<Word> list;
    private String name;

    public Tag(String name) {
        this.name = name;
        list = new ArrayList<>();
    }

    public void addWord(Word word){
        list.add(word);
    }

    public List<Word> getList() {
        return list;
    }

    public void setList(List<Word> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "" + name + " (" + list.get(0) + ", " + list.get(1) + ", etc.)";
    }

}
