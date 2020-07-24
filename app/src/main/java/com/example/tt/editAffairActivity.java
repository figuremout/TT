package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.tt.MainActivity;
import com.example.tt.R;
import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.callback.OnTodoClickCallback;
import com.yydcdut.markdown.loader.DefaultLoader;
import com.yydcdut.markdown.syntax.edit.EditFactory;
import com.yydcdut.markdown.theme.ThemeDefault;

import java.io.File;
//import com.yydcdut.rxmarkdown.RxMDConfiguration;

public class editAffairActivity extends AppCompatActivity {
    private MarkdownEditText editText;
    private  MarkdownProcessor markdownProcessor;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_affair);

        // 设置状态栏颜色 有用
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//添加Flag把状态栏设为可绘制模式
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        getSupportActionBar().hide();//隐藏标题栏

        initView();
        initEvent();

        markdown();
    }

    private void initView(){
        editText = findViewById(R.id.editTextTextMultiLine);
        editText.setBackground(null);// 取消下划线

        back = findViewById(R.id.imageButton3);
    }
    private void initEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void markdown() {
        MarkdownConfiguration markdownConfiguration = new MarkdownConfiguration.Builder(this)
                .setDefaultImageSize(50, 50)
                .setBlockQuotesLineColor(getResources().getColor(R.color.colorPrimaryLight)) // 引用块提示线颜色
                // 各级标题大小
                .setHeader1RelativeSize(1.6f)
                .setHeader2RelativeSize(1.5f)
                .setHeader3RelativeSize(1.4f)
                .setHeader4RelativeSize(1.3f)
                .setHeader5RelativeSize(1.2f)
                .setHeader6RelativeSize(1.1f)
                // 水平分割线颜色
                .setHorizontalRulesColor(getResources().getColor(R.color.colorPrimary))
                // 水平分割线高度
                .setHorizontalRulesHeight(10)
                // 行内代码背景颜色
                .setCodeBgColor(getResources().getColor(R.color.colorPrimaryLight))

                // 待办事项颜色
                .setTodoColor(0xffaa66cc)
                // 待办事项完成后颜色
                .setTodoDoneColor(0xffff8800)
                // 无序列表颜色
                .setUnOrderListColor(getResources().getColor(R.color.colorPrimary))
                .build();
        editText.setText("在这个**版本**中我们*增加test*了 `Markdown` 功能。`Markdown` 是~~一种使用纯文本编写的标记~~语言，可以产生格式![test](http://7xs03u.com1.z0.glb.clouddn.com/dex_dexopt_dex2oat.png/320$320)丰富的页面[^排版效果]，比如突出[标题](http://www.baidu.com)、居中、加粗、引用和生成列表。 https://www.google.com \n" +
                "\n" +
                "## **用法与规则：**\n" +
                "\n" +
                "你可以手动输入，也可以点击键盘上方的按钮快速输入 Markdown 符号。\n" +
                "\n" +
                "### **标题**\n" +
                "使用“#”加空格在*行首*来创建标题![test](drawable://" + "/300$300)\n" +
                "***\n" +
                "\n" +
                "```\n" +
                "test1\n" +
                "test2\n" +
                "test3\n" +
                "test4\n" +
                "```\n" +
                "\n" +
                "- [ ] 123\n" +
                "- [ ] 456\n" +
                "- [ ] 789\n" +
                "\n" +
                "- [x] 987\n" +
                "- [x] 654\n" +
                "- [x] 321\n" +
                "\n" +
                "例如：\n" +
                "# 一级标题\n" +
                "## 二级标题\n" +
                "### 三级标题\n" +
                "---\n" +
                "![test](file://"  + "b.jpg/400$400" + ")\n" +
                "### **加粗功能**\n" +
                "使用一组星号“**”来加粗一段文字\n" +
                "\n" +
                "```c\n" +
                "test1\n" +
                "test2\n" +
                "test3\n" +
                "test4\n" +
                "```" +
//            "例如：\n" +
//            "这是**加粗的文字**\n" +
                "\n" +
                "### **居中**\n" +
                "使用一对中括号“[文字]”来居中一段文字，也可![test](assets://bb.jpg/100$100)以和标题叠加使用\n" +
                "\n" +
                "例如：\n" +
                "[### 这是一个居中的标题]\n" +
                "\n" +
                "### **引用**\n" +
                "使用“> ”在段首来引用一段文字\n" +
                "\n" +
                "例如：\n" +
                "> 这是一段引用\n" +
                "> > > 这是一段引用\n" +
                "\n" +
                "### **无序列表**\n" +
                "使用 “-”、“*”或“+”加空格 来创建无序列表\n" +
                "\n" +
                "例如：\n" +
                "- 这是一个无序列表\n" +
                "+ 这是一个无序列表\n" +
                "* 这是一个无序列表\n" +
                "\n" +
                "### **有序列表**\n" +
                "使用 数字圆点加空格 如“1. ”、“2. ”来创建有序列表\n" +
                "\n" +
                "```java\n" +
                "public abstract class MemberIdsSection extends UniformItemSection {\n" +
                "  /** {@inheritDoc} */\n" +
                "    @Override\n" +
                "    protected void orderItems() {\n" +
                "        int idx = 0;\n" +
                "\n" +
                "        if (items().size() > DexFormat.MAX_MEMBER_IDX + 1) {\n" +
                "            throw new DexIndexOverflowException(getTooManyMembersMessage());\n" +
                "        }\n" +
                "\n" +
                "        for (Object i : items()) {\n" +
                "            ((MemberIdItem) i).setIndex(idx);\n" +
                "            idx++;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    private String getTooManyMembersMessage() {\n" +
                "        Map<String, AtomicInteger> membersByPackage = new TreeMap<String, AtomicInteger>();\n" +
                "        for (Object member : items()) {\n" +
                "            String packageName = ((MemberIdItem) member).getDefiningClass().getPackageName();\n" +
                "            AtomicInteger count = membersByPackage.get(packageName);\n" +
                "            if (count == null) {\n" +
                "                count = new AtomicInteger();\n" +
                "                membersByPackage.put(packageName, count);\n" +
                "            }\n" +
                "            count.incrementAndGet();\n" +
                "        }\n" +
                "\n" +
                "        Formatter formatter = new Formatter();\n" +
                "        try {\n" +
                "            String memberType = this instanceof MethodIdsSection ? \"method\" : \"field\";\n" +
                "            formatter.format(\"Too many %s references: %d; max is %d.%n\" +\n" +
                "                    Main.getTooManyIdsErrorMessage() + \"%n\" +\n" +
                "                    \"References by package:\",\n" +
                "                    memberType, items().size(), DexFormat.MAX_MEMBER_IDX + 1);\n" +
                "            for (Map.Entry<String, AtomicInteger> entry : membersByPackage.entrySet()) {\n" +
                "                formatter.format(\"%n%6d %s\", entry.getValue().get(), entry.getKey());\n" +
                "            }\n" +
                "            return formatter.toString();\n" +
                "        } finally {\n" +
                "            formatter.close();\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "例如：\n" +
                "1. 这是一个有序列表\n" +
                "2. 这是一个有序列表\n" +
                "3. 这是一个有序列表");
        markdownProcessor = new MarkdownProcessor(this);
        markdownProcessor.config(markdownConfiguration);
        markdownProcessor.factory(EditFactory.create());
        markdownProcessor.live(editText);
    }
}