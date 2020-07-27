package com.example.tt.util;

public interface Const {
    String contentExample =
            "[# **TT(TickTock)**]"+
                    "\n"+
                    "> **欢迎使用我们的APP**"+
                    "\n"+
                    "> ~~我们的口号是：别人的app，不行；我们的app，行！~~"+
                    "\n"+
                    "> > 于2020.07.14 - 2020.07.30完成"+
                    "\n"+
                    "\n"+
                    "\n"+
                    "## 提示"+
                    "\n"+
                    "- 长按事务进行删除"+
                    "\n"+
                    "- 新建、删除事务后要刷新页面才会出现改变"+
                    "\n"+
                    "- 新建事务不选择日期默认为当天日期"+
                    "\n"+
                    "- 事务标题别太长，否则会影响主页面加载速度"+
                    "\n"+
                    "- 预览界面的宽度由最长的一行内容决定，"+
                    "\n"+
                    "一行内容过长会影响观感"+
                    "\n"+
                    "- 按一次帮助按钮显示帮助文档，" +
                    "\n"+
                    "再按一次可以返回之前用户编辑的内容"+
                    "\n"+
                    "\n"+
                    "## TODO"+
                    "\n"+
                    "- [ ] 完成删除事务"+
                    "\n"+
                    "- [ ] 提供快速键入Markdown符号功能"+
                    "\n"+
                    "- [ ] 完成雷达图逻辑"+
                    "\n"+
                    "- [ ] 完成设置"+
                    "\n"+
                    "  1. 设置是否打开实时渲染md"+
                    "\n"+
                    "  2. 清空事务缓存"+
                    "\n"+
                    "- [ ] 完成事务状态存储（已完成，未完成）"+
                    "\n"+
                    "- [ ] 完成刷新界面功能"+
                    "\n"+
                    "- [ ] 子页面可实现已完成、未完成事务分类"+
                    "\n"+
                    "- [ ] 事务的设置时间、提醒、循环"+
                    "\n"+
                    "- [ ] 桌面控件"+
                    "\n"+
                    "- [ ] 本地化"+
                    "\n"+
                    "***"+
                    "\n"+
                    "## 数据存储"+
                    "\n"+
                    "- 数据统一采用SharedPreferences存储于"+
                    "\n"+
                    "`data/data/com.example.tt/shared_prefs/shared.xml`"+
                    "\n"+
                    "文件中"+
                    "\n"+
                    "```java"+
                    "\n"+
                    "private SharedPreferences preferences;"+
                    "\n"+
                    "private SharedPreferences.Editor editor;"+
                    "\n"+
                    "\n"+
                    "// 数据存"+
                    "\n"+
                    "editor.putString(\"key\", \"value\");"+
                    "\n"+
                    "editor.apply();"+
                    "\n"+
                    "// 数据取"+
                    "\n"+
                    "String value = preferences.getString(\"key\", \"defaultValue\");"+
                    "\n"+
                    "```"+
                    "\n"+
                    "\n"+
                    "## 对项目感兴趣？"+
                    "\n"+
                    "- [详细了解](http://www.zjmpage.com/TT/README.html)";

}
