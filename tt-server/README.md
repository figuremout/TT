# SharedPreferences
 <!-- * key: email#username value(String): email对应的用户名
 * key: email#pwd value(String): email对应的密码 -->
 * key: currentEmail value(String): 当前登录账号的邮箱
 <!-- * key: email#signDate value(String): email对应的最近打卡日期
 * key: email#signTimes value(Int): email对应的连续打卡次数
 * key: email#delAffairNum value(Int): email对应的删除事务数
 * key: email#socialTimes value(Int): email对应的分享次数 -->
 * key: email#settings#isRender value(Boolean): email对应账户的实时渲染设置
 * key: email#settings#isRecycle value(Boolean): email对应账户的自动回收设置
 <!-- * key: email#affairID=(事件创建时间)#title value(String): email对应账户下标识为affairID的事件标题
 * key: email#affairID=(事件创建时间)#content value(String): email对应账户下标识为affairID的事件内容 xxxxx
 * key: email#affairID=(事件创建时间)#date value(String): email对应账户下标识为affairID的事件日期
 * key: email#affairID=(事件创建时间)#status value(Boolean): email对应账户下标识为affairID的事件是否完成(true 已完成，false 未完成) -->
 <!-- * key: email#affairIDList value(String[]): email对应账户下注册的所有事件affairID(事件创建时间,...) -->
 * key: currentAffairDate value(String): 当前编辑事务的日期，用于人工选择日期界面暂时存储
 * key: currentAffairID value(String): 当前点击事务项的ID，在点击事务项按钮时设置，被打开的事务编辑界面获取

# MongoDB
## DB: tt
### Collections: users
#### Docs
- username
- email
- pwd
### Collections: affairs
#### Docs
- email
- affairID
- title
- content
- date
- status
### Collections: records
#### Docs
- email
- signDate
- signTimes
- shareTimes