package cn.edots.slug.core;


import android.support.v4.util.ArraySet;

import java.io.Serializable;
import java.util.Collection;

public class FinishParameter implements Serializable {

    private static final long serialVersionUID = 2124180411032825937L;

    private final boolean exit;
    private final Collection<String> pages;

    public FinishParameter() {
        this.exit = true;
        this.pages = null;
    }

    public FinishParameter(Collection<String> pages) {
        this.exit = false;
        if (pages == null) pages = new ArraySet<>();
        this.pages = pages;
    }

    public boolean isExit() {
        return exit;
    }

    public Collection<String> getPages() {
        return pages;
    }

    public FinishParameter add(Class clazz) {
        if (pages == null) return null;
        pages.add(clazz.getSimpleName());
        return this;
    }
}