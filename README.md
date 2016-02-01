# TouchMoveActivity

With TouchMoveActivity you can open Activity with anything layout and close with a simple Swipe Down/Up,
like Facebook App for Android

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
##MIT License

    The MIT License (MIT)

    Copyright (c) 2016 Fabrizio La Rosa

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

