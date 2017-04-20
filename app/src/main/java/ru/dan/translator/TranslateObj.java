package ru.dan.translator;

import android.support.v7.widget.RecyclerView;

/**
 * Created by  DubininA on 20.04.2017.
 */

public class TranslateObj{
    private String dirs;
    private String origText;
    private String translatrText;
    private boolean favorite;

    public TranslateObj() {

    }

    public String getDirs() {
        return dirs;
    }

    public void setDirs(String dirs) {
        this.dirs = dirs;
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
