package cse.fjnu.androiduiexperiment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private AnimalAdapter adapter;
    private final List<Animal> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data.add(new Animal("Lion", R.drawable.lion));
        data.add(new Animal("Tiger", R.drawable.tiger));
        data.add(new Animal("Monkey", R.drawable.monkey));
        data.add(new Animal("Dog", R.drawable.dog));
        data.add(new Animal("Cat", R.drawable.cat));
        data.add(new Animal("Elephant", R.drawable.elephant));

        adapter = new AnimalAdapter(data, position -> {
            Animal clicked = data.get(position);
            Toast.makeText(this, "点击: " + clicked.getName(),
                    Toast.LENGTH_SHORT).show();
            data.add(new Animal(clicked.getName() + " Copy", clicked.getIconRes()));
            adapter.notifyItemInserted(data.size() - 1);
        });

        recyclerView.setAdapter(adapter);
    }
}