# TT (TickTock)
> My first android app.
> > An android time-manager for minimalist.

This App is **For Learn & Communication Purposes Only**.

## Open Source [#](#)
The source code is free to modify / use, but with no liability & warranty (according to MIT License).
- Android Studio Project: [GitHub Repo]()

## Download APK [#](#)
- Android 5.0 (Lollipop) +
    > minSdkVersion: 21

|Type|Version|Time|Description|
|--|--|--|--|
|Debug|[v1.2.1 (Alpha)]()|*2020.07.12*|*|
|||

## TODO List [#](#)
1. 

## Intro [#](#)
### Language
- 中文
- English
### Function
>f
![](./../img/android_Activity生命周期.png)

## Note [#](#)
1. Add click event on a clickable thing
```java
    Button login_button = findViewById(R.id.button3);
    login_button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            
        }
    });
```
2. Open a new page
```java
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    startActivity(intent);
```
3. Finish an activity (to close a page)
```java
    finish();
```
4. Add ripple effect on a button: [Blog](https://www.cnblogs.com/weimore/p/7725256.html)
5. Create splash page: [Blog](https://blog.csdn.net/qq_39732867/article/details/86512712)
6. Customize Switch: [Blog](https://www.jianshu.com/p/4e436300f328)
7. Turn SVG into VectorDrawable XML: [Blog](https://blog.csdn.net/lupengfei1009/article/details/51079123)
8. Open a web page in browser (also work for QQ customer service link): [Blog](https://blog.csdn.net/CC1991_/article/details/98625451)
9. Simple animation
```java
    // username in
    EditText register_username = findViewById(R.id.editTextTextPersonName);
    register_username.setVisibility(View.VISIBLE);
    register_username.setAnimation(AnimationUtils.makeInAnimation(this,false));
```
10. Generate APK: [Blog](https://www.cnblogs.com/lsdb/p/9337342.html)