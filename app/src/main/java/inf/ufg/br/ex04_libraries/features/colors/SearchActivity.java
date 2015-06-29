package inf.ufg.br.ex04_libraries.features.colors;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import inf.ufg.br.ex04_libraries.R;
import inf.ufg.br.ex04_libraries.model.Color;


public class SearchActivity extends ActionBarActivity {

    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        String nome = getIntent().getStringExtra("test");
        getSupportActionBar().setTitle(nome);

        List<Color> colors = new ArrayList();
        colors.add(new Color("Red", "#FF0000"));
        colors.add(new Color("Green", "#00FF00"));
        colors.add(new Color("Blue", "#0000FF"));
        colors.add(new Color("Gray", "#EEEEEE"));

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapter = new SearchAdapter(colors);
        rv.setAdapter(adapter);

    }

    public void addColor(View v){
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        int randomColor = android.graphics.Color.argb(255,r, g, b);
        String hexColor = String.format("#%06X", (0xFFFFFF & randomColor));

        Color cor = new Color("???", hexColor);
        adapter.add(0,cor);

    }
}
