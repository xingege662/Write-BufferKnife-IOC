package com.example;

import javax.lang.model.type.TypeMirror;

/**
 * Created by xinchang on 2017/3/5.
 */

public class FiledViewBinding {
    private String name;//textview
    private TypeMirror typeMirror;//TextView
    private int resID;//R.id.

    public FiledViewBinding(String name, TypeMirror typeMirror, int resID) {
        this.name = name;
        this.typeMirror = typeMirror;
        this.resID = resID;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public int getResID() {
        return resID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypeMirror(TypeMirror typeMirror) {
        this.typeMirror = typeMirror;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}
