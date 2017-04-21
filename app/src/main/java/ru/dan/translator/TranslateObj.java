package ru.dan.translator;

import android.support.v7.widget.RecyclerView;

/**
 * Created by  DubininA on 20.04.2017.
 */

public class TranslateObj{
    private String origLang;
    private String origText;
    private String translateLang;
    private String translatrText;
    private boolean favorite;

    public TranslateObj() {

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

    public String getTranslatrText() {
        return translatrText;
    }

    public void setTranslatrText(String translatrText) {
        this.translatrText = translatrText;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
