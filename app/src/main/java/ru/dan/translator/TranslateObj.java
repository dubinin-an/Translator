package ru.dan.translator;

/**
 * Created by  DubininA on 20.04.2017.
 */

public class TranslateObj{
    private long id;
    private String origLang;
    private String origText;
    private String translateLang;
    private String translateText;
    private boolean favorite;

    public TranslateObj() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrigLang() {
        return origLang;
    }

    public void setOrigLang(String origLang) {
        this.origLang = origLang;
    }

    public String getTranslateLang() {
        return translateLang;
    }

    public void setTranslateLang(String translateLang) {
        this.translateLang = translateLang;
    }

    public String getOrigText() {
        return origText;
    }

    public void setOrigText(String origText) {
        this.origText = origText;
    }

    public String getTranslateText() {
        return translateText;
    }

    public void setTranslateText(String translateText) {
        this.translateText = translateText;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
