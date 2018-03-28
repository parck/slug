# slug

### 为什么要使用slug？
- 如果你想更方便的使用DataBinding
- 如果你项目的activity逻辑会很复杂
- ...
- 或者你想尝试新的方式

### 如何引入slug？
https://jitpack.io/#parck/slug

### 如何使用slug？
- 使用slug之前请熟悉Databinding的使用方法 https://developer.android.google.cn/topic/libraries/data-binding/index.html
- 确保你的工程已经成功引入slug
- 开始使用   
#####  MainActivity.java
```
@BindLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainModel dataModel = new MainModel();
        dataModel.setName("nameText");
        viewDataBinding.setMain(dataModel);
        viewDataBinding.testController01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestActivity.class, new Protocol(Test01Controller.class));
            }
        });

        viewDataBinding.testController02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestActivity.class, new Protocol(Test02Controller.class));
            }
        });
    }
}
```

#####  activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="main"
            type="cn.edots.slug.example.model.MainModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:id="@+id/test_controller_01"
            android:layout_width="match_parent"
            android:layout_height="46dp"
            android:gravity="center"
            android:text="test_controller_01" />

        <View
            android:layout_width="match_parent"
            android:layout_height="1px"
            android:background="@color/colorAccent" />

        <TextView
            android:id="@+id/test_controller_02"
            android:layout_width="match_parent"
            android:layout_height="46dp"
            android:gravity="center"
            android:text="test_controller_02" />

        <View
            android:layout_width="match_parent"
            android:layout_height="1px"
            android:background="@color/colorAccent" />
    </LinearLayout>
</layout>
```

##### MainModel.java
```
public class MainModel extends DataModel {

    private String name = "name";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

##### TestActivity.java
```
@BindLayout(R.layout.activity_test)
public class TestActivity extends BaseActivity<ActivityTestBinding> {
}
```

##### activity_test.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="test"
            type="cn.edots.slug.example.model.TestModel" />
    </data>

    <TextView
        android:id="@+id/title_text"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:text="@{test.title}" />

</layout>
```

##### TestModel.java
```
public class TestModel extends DataModel {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
```

##### BaseTestController.java
```
public class BaseTestController extends Controller<TestActivity, ActivityTestBinding> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        TestModel test = new TestModel();
        viewDataModel.setTest(test);
        viewDataModel.titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
    }
}
```

##### Test01Controller.java
```
public class Test01Controller extends BaseTestController implements Standardize {

    @Override
    public void setupData(@Nullable Map<String, Object> intentData) {
        viewDataModel.getTest().setTitle("Test01Controller");
        viewDataModel.titleText.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onCreateLast() {

    }
}
```

##### Test02Controller.java
```
public class Test02Controller extends BaseTestController implements Standardize {

    @Override
    public void setupData(@Nullable Map<String, Object> intentData) {
        viewDataModel.getTest().setTitle("Test02Controller");
        viewDataModel.titleText.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onCreateLast() {

    }
}
```

#### 以上是example的全部代码，后续会提供example的实例工程。
