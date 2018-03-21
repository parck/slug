package cn.edots.slug.model;

import java.io.Serializable;

import cn.edots.slug.Controller;


/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */

public abstract class Protocol implements Serializable {

    private Class<Controller> controller;

    public Class<Controller> getController() {
        return controller;
    }

    public void setController(Class controller) {
        this.controller = controller;
    }
}
