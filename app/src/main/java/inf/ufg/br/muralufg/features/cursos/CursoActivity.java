package inf.ufg.br.muralufg.features.cursos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import inf.ufg.br.muralufg.R;
import inf.ufg.br.muralufg.model.Curso;

import java.util.List;

/**
 * Created by Marla Aragão on 01/07/2015.
 */
public class CursoActivity extends FragmentActivity implements ConsultCurso.ConsultCursoSituation {

    private Spinner spinnerCursos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ConsultCurso(this).execute();
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner(List<Curso> cursos) {

        spinnerCursos = (Spinner) findViewById(R.id.cursos);

        ArrayAdapter<Curso> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cursos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCursos.setAdapter(dataAdapter);
    }


    @Override
    public void onConcludeConsultCurso(List<Curso> cursos) {

        addItemsOnSpinner(cursos);
    }
}

