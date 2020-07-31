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
|Debug|[v1.1.0 (Alpha)](http://www.zjmpage.com/TT/TT_debug1.1.0.apk)|*2020.07.16*|*|
|||

## TODO List [#](#)
- [ ] Alarm

## Note [#](#)
> All data stored in `data/data/com.example.tt/shared_prefs/shared.xml` with SharedPreferences.
> Here is the naming rules.
 * key: (email)#username value(String): email对应的用户名
 * key: (email)#pwd value(String): email对应的密码
 * key: (email)#signDate value(String): email对应的最近打卡日期
 * key: (email)#signTimes value(Int): email对应的连续打卡次数
 * key: (email)#delAffairNum value(Int): email对应的删除事务数
 * key: (email)#socialTimes value(Int): email对应的分享次数
 * key: (email)#settings#isRender value(Boolean): email对应账户的实时渲染设置
 * key: (email)#settings#isRecycle value(Boolean): email对应账户的自动回收设置
 * key: (email)#affairID=(事件创建时间)#title value(String): email对应账户下标识为affairID的事件标题
 * key: (email)#affairID=(事件创建时间)#content value(String): email对应账户下标识为affairID的事件内容 xxxxx
 * key: (email)#affairID=(事件创建时间)#date value(String): email对应账户下标识为affairID的事件日期
 * key: (email)#affairID=(事件创建时间)#status value(Boolean): email对应账户下标识为affairID的事件是否完成(true 已完成，false 未完成)
 * key: (email)#affairIDList value(String[]): email对应账户下注册的所有事件affairID(事件创建时间,...)
 * key: currentEmail value(String): 当前登录账号的邮箱
 * key: currentAffairDate value(String): 当前编辑事务的日期，用于人工选择日期界面暂时存储
 * key: currentAffairID value(String): 当前点击事务项的ID，在点击事务项按钮时设置，被打开的事务编辑界面获取