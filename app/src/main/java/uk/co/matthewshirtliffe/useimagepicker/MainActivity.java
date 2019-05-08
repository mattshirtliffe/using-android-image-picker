package uk.co.matthewshirtliffe.useimagepicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    
    FloatingActionButton floatingActionButton;
    ArrayList<String> imagePaths = new ArrayList<>();
    RecyclerView recyclerView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpComponents();
    }

    private void setUpComponents() {
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);
        setOnClick(floatingActionButton);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(imagePaths, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void setOnClick(FloatingActionButton floatingActionButton){
        floatingActionButton.setOnClickListener((View v) -> {
            ImagePicker.create(MainActivity.this).start();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            List<Image> images = ImagePicker.getImages(data);
            for (Image image: images){
                imagePaths.add(image.getPath());
            }
            recyclerView.getAdapter().notifyDataSetChanged();

        }
    }
}
