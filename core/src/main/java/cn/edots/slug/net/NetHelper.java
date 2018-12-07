package cn.edots.slug.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.edots.slug.core.cache.AppCachePool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetHelper {

    private static Retrofit retrofit;
    private static AppCachePool apiPool;
    private Builder builder;

    private NetHelper() {
    }

    private NetHelper(Builder builder) {
        this.builder = builder;
        apiPool = AppCachePool.getInstance().newContainer(NetHelper.class.getSimpleName());
    }

    public void init() {
        if (retrofit == null) {
            OkHttpClient client = getClient();
            JacksonConverterFactory factory = JacksonConverterFactory.create();
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(factory)
                    .client(client)
                    .baseUrl(builder.baseUrl)
                    .build();
        }
        try {
            if (builder.services != null)
                for (Class<? extends NetService> service : builder.services) {
                    Type superclass = service.getGenericSuperclass();
                    if (superclass instanceof ParameterizedType) {
                        Type[] typeArguments = ((ParameterizedType) superclass).getActualTypeArguments();
                        if (typeArguments.length >= 1) {
                            Class api = (Class) typeArguments[0];
                            NetService instance = service.newInstance();
                            instance.setApi(retrofit.create(api));
                            apiPool.put(service.getSimpleName(), instance);
                        }
                    }
                }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        if (this.builder.interceptors != null)
            for (Interceptor interceptor : this.builder.interceptors)
                builder.addInterceptor(interceptor);
        return builder.build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static <T extends NetService> T get(Class<T> o) {
        return (T) apiPool.get(o.getSimpleName());
    }

    public static void put(NetService service) {
        apiPool.put(service.getClass().getSimpleName(), service);
    }

    // =============================================================================================
    // inner class
    // =============================================================================================
    public static class Builder {

        private static NetHelper helper;

        private String baseUrl;
        private List<Interceptor> interceptors;
        private List<Class<? extends NetService>> services;

        public Builder() {
            this.interceptors = new ArrayList<>();
            this.services = new ArrayList<>();
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder addService(Class<? extends NetService> service) {
            this.services.add(service);
            return this;
        }

        public NetHelper build() {
            if (helper == null) helper = new NetHelper(this);
            return helper;
        }

    }
}
