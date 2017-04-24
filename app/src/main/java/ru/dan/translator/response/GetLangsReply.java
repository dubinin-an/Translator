
package ru.dan.translator.response;

import android.util.ArrayMap;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GetLangsReply {

    @SerializedName("dirs")
    @Expose
    private List<String> dirs = new ArrayList<String>();
    @SerializedName("langs")
    @Expose
    private HashMap<String,String> langs;

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    /**
     * Получить языковой массив
      * @return
     */
    public HashMap<String,String> getLangs() {
        return langs;
    }

    /**
     * Получить Название языка по буквенному коду
     * @param langCode
     * @return
     */
    public String getLangByCode(String langCode){
        return langs.get(langCode);
    }

    /**
     * Получить код языка по строковому значению
     * @param lang Язык
     * @return Буквенный код языка
     */
    public String getCodeByLang(String lang){
        if (langs.containsValue(lang)){
            for (Map.Entry<String, String> entry : this.langs.entrySet()){
                if(entry.getValue().equals(lang)) return entry.getKey();
            }
        }
        return null;
    }

    public int getIdByCode(String langCode){
        if (langs.containsKey(langCode)){
            int i = 0;
            for(String s : langs.keySet()){
                if (s.equals(langCode)){
                    return i;
                }else i++;
            }
        }
        return 0;
    }

    public void setLangs(HashMap<String,String> langs) {
        this.langs = langs;
    }

    /**
     * Получить список языков
      * @return
     */
    public List<String> getLangsValues(){

//        List<String> list = new ArrayList<>();
//        for (Map.Entry<String, String> entry : this.langs.entrySet()){
//            list.add(entry.getValue());
//        }
//        list.addAll(langs.values());
//        return list;

        return new ArrayList<>(langs.values());
    }





    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
