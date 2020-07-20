<script>
    function is_qq(){
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/QQ/i) == "qq") {
        return true;
        }
        return false;
    }
    window.onload = function(){
        let apk_link = document.querySelector("td a");
        apk_link.onclick = function(){
            if(is_qq()){
                alert("Alert: 检测到当前使用的是QQ内置浏览器。\n为避免被重定向，APK下载链接已失活处理，请通过其他手机浏览器打开！");
                apk_link.href = "#";
                apk_link.innerText = "Inactive Link";
            }
        }
    }
</script>

# TT (TickTock)
> My first android app.
> > An android time-manager for minimalist.

This App is **For Learn & Communication Purposes Only**.

## Open Source [#](#)
Under MIT License.
- Android Studio Project: [GitHub Repo](https://github.com/githubzjm/TT)

## Download APK [#](#)
- Android 5.0 (Lollipop) +
    > minSdkVersion: 21

|Type|Version|Time|Description|
|--|--|--|--|
|Debug|[v1.1.0 (Alpha)](./TT_debug1.1.0.apk)|*2020.07.16*|*|
|||

## TODO List [#](#)
- [ ] Login & Register

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
11. Share app with QQ friends (based on QQ opensdk): [myBlog](https://blog.csdn.net/qq_43536897/article/details/107344431) 