一、 实验目的
掌握 ListView 与 SimpleAdapter 的使用，以及列表项点击事件的处理。

掌握自定义布局 AlertDialog 的创建。

掌握使用 XML 定义选项菜单（Options Menu）及子菜单。

掌握上下文操作模式（ActionMode）及多选列表的实现。

掌握 RecyclerView、自定义 Adapter 与 ViewHolder 的使用。

二、 实验环境
开发工具：Android Studio

开发语言：Java

运行环境：Android 模拟器 (API 33+)

最低兼容版本：API 21 (Android 5.0)

三、 项目结构
text
AndroidUIExperiment/
└── app/src/main/
    ├── java/cse/fjnu/androiduiexperiment/
    │   ├── MainActivity.java              // 主页面（实验入口）
    │   ├── ListViewActivity.java          // 实验1：ListView
    │   ├── AlertDialogActivity.java       // 实验2：自定义对话框
    │   ├── MenuActivity.java              // 实验3：XML菜单
    │   ├── ActionModeActivity.java        // 实验4：ActionMode
    │   ├── RecyclerViewActivity.java      // 实验5：RecyclerView
    │   ├── Animal.java                    // 实验5数据实体类
    │   └── AnimalAdapter.java             // 实验5适配器
    ├── res/
    │   ├── layout/                        // 各页面布局及列表项布局
    │   ├── menu/                          // exp3_menu.xml, exp4_action_mode.xml
    │   ├── drawable/                      // 动物图片资源(lion, tiger等)
    │   └── values/                        // 主题、颜色等
    └── AndroidManifest.xml
四、 实验内容与核心代码
实验 1：Android ListView 的用法
实现效果：利用 SimpleAdapter 展示图文列表。点击列表项时，该项背景变红，弹出 Toast 提示，并发送一条通知（图标为应用图标，标题为列表项内容）。

核心代码 (ListViewActivity.java)：

java
// 初始化数据
List<Map<String, Object>> data = new ArrayList<>();
for (int i = 0; i < names.length; i++) {
    Map<String, Object> map = new HashMap<>();
    map.put("name", names[i]);
    map.put("icon", icons[i]);
    data.add(map);
}

// 使用 SimpleAdapter
SimpleAdapter adapter = new SimpleAdapter(
        this, data, R.layout.list_item,
        new String[]{"name", "icon"},
        new int[]{R.id.tvName, R.id.ivIcon}
);
listView.setAdapter(adapter);

// 点击事件处理
listView.setOnItemClickListener((parent, view, position, id) -> {
    String name = names[position];
    // 恢复上一个选中的背景
    if (lastSelectedView != null) lastSelectedView.setBackgroundColor(Color.TRANSPARENT);
    // 当前项变红
    view.setBackgroundColor(Color.RED);
    lastSelectedView = view;

    Toast.makeText(this, "选中: " + name, Toast.LENGTH_SHORT).show();
    sendNotification(name); // 发送通知
});
实验 2：创建自定义布局的 AlertDialog
实现效果：点击按钮后，弹出一个带有橙色标题栏（ANDROID APP）和自定义输入框（Username、Password）的登录对话框。

核心代码 (AlertDialogActivity.java)：

java
private void showCustomDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // 引入自定义布局
    View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
    builder.setView(dialogView);

    // 设置底部按钮
    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
    builder.setPositiveButton("Sign in", (dialog, which) -> {
        EditText etUser = dialogView.findViewById(R.id.etUsername);
        EditText etPass = dialogView.findViewById(R.id.etPassword);
        Toast.makeText(this, "用户名: " + etUser.getText() + "\n密码: " + etPass.getText(), Toast.LENGTH_SHORT).show();
    });

    builder.create().show();
}
实验 3：使用 XML 定义菜单
实现效果：在 ActionBar 中显示选项菜单，包含“字体大小”（小、中、大对应10、16、20号字）、“普通菜单项”（点击弹Toast）和“字体颜色”（红色、黑色）。

核心代码 (exp3_menu.xml)：

xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:title="字体大小">
        <menu>
            <item android:id="@+id/font_small" android:title="小" />
            <item android:id="@+id/font_medium" android:title="中" />
            <item android:id="@+id/font_large" android:title="大" />
        </menu>
    </item>
    <item android:id="@+id/normal_item" android:title="普通菜单项" />
    <item android:title="字体颜色">
        <menu>
            <item android:id="@+id/color_red" android:title="红色" />
            <item android:id="@+id/color_black" android:title="黑色" />
        </menu>
    </item>
</menu>
逻辑处理 (MenuActivity.java)：

java
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.font_small) tvTest.setTextSize(10);
    else if (id == R.id.font_medium) tvTest.setTextSize(16);
    else if (id == R.id.font_large) tvTest.setTextSize(20);
    else if (id == R.id.normal_item) Toast.makeText(this, "普通菜单项被点击", Toast.LENGTH_SHORT).show();
    else if (id == R.id.color_red) tvTest.setTextColor(Color.RED);
    else if (id == R.id.color_black) tvTest.setTextColor(Color.BLACK);
    return super.onOptionsItemSelected(item);
}
实验 4：创建上下文操作模式 (ActionMode) 的上下文菜单
实现效果：长按 ListView 中的列表项，进入多选模式。顶部 ActionBar 显示选中的数量（如 "1 selected"），并出现删除图标。支持多选并批量删除。

核心代码 (ActionModeActivity.java)：

java
// 设置为多选模态模式
listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        // 更新标题，显示选中数量
        int count = listView.getCheckedItemCount();
        mode.setTitle(count + " selected");
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.exp4_action_mode, menu);
        mode.setTitle("1 selected");
        return true;
    }
    
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            for (int i = listView.getCount() - 1; i >= 0; i--) {
                if (listView.isItemChecked(i)) data.remove(i);
            }
            adapter.notifyDataSetChanged();
            mode.finish();
            return true;
        }
        return false;
    }
    // ... 其他重写方法
});
实验 5：使用 RecyclerView 实现列表视图效果
实现效果：使用 RecyclerView 展示动物图文列表。点击某一项后，弹出 Toast，并在列表末尾立即追加一条相同的数据（名字后加 " Copy"），验证复用机制不报错、不窜位。

核心代码 (AnimalAdapter.java)：

java
public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
    // ... 构造函数等省略
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animal animal = data.get(position);
        holder.tvName.setText(animal.getName());
        holder.ivIcon.setImageResource(animal.getIconRes());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                // 使用 getAdapterPosition() 确保获取正确的当前坐标
                listener.onClick(holder.getAdapterPosition()); 
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivAnimal);
            tvName = itemView.findViewById(R.id.tvAnimal);
        }
    }
}
Activity 中的点击追加逻辑 (RecyclerViewActivity.java)：

java
adapter = new AnimalAdapter(data, position -> {
    Animal clicked = data.get(position);
    Toast.makeText(this, "点击: " + clicked.getName(), Toast.LENGTH_SHORT).show();
    // 增加记录并刷新
    data.add(new Animal(clicked.getName() + " Copy", clicked.getIconRes()));
    adapter.notifyItemInserted(data.size() - 1); 
});
五、 实验总结
通过本次实验，成功在一个项目中整合了 Android 开发中最常用的五大 UI 组件。理解了：

数据与视图的绑定：从 SimpleAdapter 的简单键值对映射，到 RecyclerView.Adapter 的高效复用机制。

交互设计：普通点击与长按（ActionMode）的触发场景与实现差异。

弹窗与菜单：掌握了如何将自定义 View 嵌入 Dialog，以及通过 XML 配置多级菜单。

通知机制：掌握了 Android 8.0+ 的 NotificationChannel 创建及 Android 13+ 的 POST_NOTIFICATIONS 运行时权限申请。
