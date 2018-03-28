package cn.edots.slug.model;

import java.io.Serializable;

import cn.edots.slug.Controller;


/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */

public class Protocol implements Serializable {

    private Class<? extends Controller> controller;

    public Protocol() {
    }

    public Protocol(Class<? extends Controller> clazz) {
        this.controller = clazz;
    }

    public Class<? extends Controller> getController() {
        return controller;
    }

    public void setController(Class controller) {
        this.controller = controller;
    }
}
