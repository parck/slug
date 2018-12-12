# slug
我叫鼻涕虫，一个让逻辑更简单的工具

### 为什么要使用slug？
- 如果你想更方便的使用DataBinding
- 如果你项目的activity逻辑会比较复杂
- 也许它可以让你把更多时间花费在业务逻辑上
- ...
- 或者你想尝试新的方式

### 如何引入slug？
https://jitpack.io/#parck/slug
> 推荐直接引入lib

### 如何使用slug？
- 使用slug之前请熟悉Databinding的使用方法 https://developer.android.google.cn/topic/libraries/data-binding/index.html
- 确保你的工程已经成功引入slug
#### 开始使用
1. Activity
- MainActivity.java
```
 ...
 @BindLayout(R.layout.activity_main)
 public class MainActivity extends BaseActivity<ActivityMainBinding> {

     private MainVO vo;

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         vo = new MainVO();
         viewDataBinding.setVo(vo);
     }

    ...

 }
```

- activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <variable
            name="vo"
            type="**.**.MainVO" />
    </data>

    ...
</layout>
```

2. 多类型条目RecyclerView的Adapter
- Model - Header.java
```
public class Header implements  Classable<HeaderBinding> {

    private int type;

    ...

    void setType(int type){
        this.type = type;
    }

    int getType(){
        return this.type;
    }

    void holding(RecyclerViewAdapter.ViewHolder<HeaderBinding> holder){
        holder.getViewDataBinding().setHeader(this);
    }

}
```

- header.xml

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="header"
            type="**.**.Header" />
    </data>

    ...
</layout>
```

- Model - Item.java
```
public class Item implements  Classable<ItemBinding> {

    private int type;

    ...

    void setType(int type){
        this.type = type;
    }

    int getType(){
        return this.type;
    }

    void holding(RecyclerViewAdapter.ViewHolder<ItemBinding> holder){
        holder.getViewDataBinding().setItem(this);
    }

}
```

- item.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <variable
            name="item"
            type="**.**.Item" />
    </data>

    ...
</layout>
```

- Activity
```
...

     private List<Classable> data = new ArrayList<>();

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         Header header = new Header();
         header.setType(0);// new int[]{R.layout.header, R.layout.item};Mode对应布局在数组中的下标0
         data.add(header);
         Item item = new Item();
         item.setType(1);// new int[]{R.layout.header, R.layout.item};Mode对应布局在数组中的下标1
         data.add(item);

         viewDataBinding.recyclerView.setAdapter(new ClassedAdapter(THIS, new int[]{R.layout.header, R.layout.item}, data));
     }

...
```
> 整个过程就是这么简单。

3. 更简洁的Retrofit + RXJava
- Application.java
```
...

    @Override
    public void onCreate() {
        super.onCreate();

        new NetHelper.Builder()
                .setBaseUrl(BuildConfig.DEBUG ? BASE_URL_DEBUG : BASE_URL)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {

                        ...

                        return null;
                    }
                })
                .addService(UserService.class)
                .addService(***.class)
                ...
                .build().init();
    }

...

```

- UserService.java
```
    ...
    public class UserService extends NetService<UserAPI> {

        public Observable<Res<SellerDTO>> login(String username, String password) {

            return api.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .doOnNext(...)
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
```

- UserAPI.java
```
    public interface UserAPI {

        @FormUrlEncoded
        @POST("login")
        Observable<...> login(@Field("username") String cellphone, @Field("password") String password);

        ...
    }
```

- Activity
```
     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         NetHelper.get(UserService.class).login(...);
     }
```
> 不再需要手动实例化API对象，程序自动帮你实例化并注入到Service，使用接口时只需要调用NetHelper.get(**Service.class）即可获得Service对象（个人不推荐直接使用API发起请求）。

##### 至此，主要的功能就介绍完了（Protocol、TitleBarActivity等用法请看 [示例工程](https://github.com/parck/slug-example) 了解吧）。

> 更多疑问请联系：parcklee@163.com
