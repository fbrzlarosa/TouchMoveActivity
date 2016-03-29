# Touch Move Activity

With Touch Move Activity you can open Activity with anything layout and close with a simple Swipe Down/Up,
like Facebook App for Android

![Alt Text](https://github.com/fbrzlarosa/TouchMoveActivity/blob/master/example.gif)


##Usage
####Declare TouchMoveActivity in your Manifest, with its Theme.
```xml
<activity android:name="com.touchmoveactivity.touchmoveactivity.TouchMoveActivity"
            android:theme="@style/ThemeTouchMoveActivity.Transparent"/> //IMPORTANT!!!!
```
####Init TouchMoveActivity
```java
TouchMoveActivity.init(View);
```
or
```java
TouchMoveActivity.init(View,new onMoveActivity(){
@Override
    public void onMoveActivity(float offSetMove) {
        
    }
});
```


##Example
```java
final View frame= LayoutInflater.from(MainActivity.this).inflate(R.layout.touch_move_image,null);
TouchMoveActivity.init(frame, new onMoveActivity() {
    @Override
    public void onMoveActivity(float offSetMove) {
        frame.findViewById(R.id.background_for_image).setAlpha(1 - offSetMove);
    }
});
startActivity(new Intent(MainActivity.this,TouchMoveActivity.class));
```
