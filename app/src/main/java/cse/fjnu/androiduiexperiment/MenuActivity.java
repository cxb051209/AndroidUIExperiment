package cse.fjnu.androiduiexperiment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tvTest = findViewById(R.id.tvMenuTest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exp3_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.font_small) {
            tvTest.setTextSize(10);
        } else if (id == R.id.font_medium) {
            tvTest.setTextSize(16);
        } else if (id == R.id.font_large) {
            tvTest.setTextSize(20);
        } else if (id == R.id.normal_item) {
            Toast.makeText(this, "普通菜单项被点击", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.color_red) {
            tvTest.setTextColor(Color.RED);
        } else if (id == R.id.color_black) {
            tvTest.setTextColor(Color.BLACK);
        }
        return super.onOptionsItemSelected(item);
    }
}