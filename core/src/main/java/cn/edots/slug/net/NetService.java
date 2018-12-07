package cn.edots.slug.net;

public class NetService<API> {

    protected API api;

    public void setApi(API api) {
        this.api = api;
    }

    public API getApi() {
        return api;
    }
}
