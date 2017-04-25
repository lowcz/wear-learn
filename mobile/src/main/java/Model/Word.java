package Model;

/**
 * Created by pawel on 2017-04-23.
 */

public class Word {
    private String value;
    private String translation;

    public Word(String value, String translation) {
        this.translation = translation;
        this.value = value;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }




}
