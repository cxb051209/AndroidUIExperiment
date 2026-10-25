package cse.fjnu.androiduiexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String[] items = {
            "实验1：ListView + SimpleAdapter",
            "实验2：自定义布局 AlertDialog",
            "实验3：XML 定义菜单",
            "实验4：ActionMode 上下文菜单",
            "实验5：RecyclerView 列表"
    };

    private final Class<?>[] activities = {
            ListViewActivity.class,
            AlertDialogActivity.class,
            MenuActivity.class,
            ActionModeActivity.class,
            RecyclerViewActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.mainListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startActivity(new Intent(MainActivity.this, activities[position]));
            }
        });
    }
}