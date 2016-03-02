package inf.ufg.br.muralufg.features.oportunidades;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import inf.ufg.br.muralufg.R;
import inf.ufg.br.muralufg.features.cursos.ConsultCurso;
import inf.ufg.br.muralufg.features.cursos.CursoAdapter;
import inf.ufg.br.muralufg.model.Oportunidade;
import inf.ufg.br.muralufg.model.Curso;
import inf.ufg.br.muralufg.utils.WebInterface;


public class SearchActivity extends ActionBarActivity
        implements ConsultCurso.ConsultCursoSituation, ConsultOportunidades.ConsultOportunidadeSituation, WebInterface {

    private AsyncTask taskCurso;
    private ProgressDialog ringProgressDialog;
    private SearchAdapter adapter;
    private Spinner spinner_cursos;
    private RecyclerView rv;
    private RadioGroup opcoes;
    private RadioButton estagio, pesquisa, emprego;
    private SharedPreferences sharedPreferences;
    private String id_curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oportunidades);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String nome = getIntent().getStringExtra("titulo");
        getSupportActionBar().setTitle(nome);

        id_curso = getIntent().getStringExtra("curso");


        final Spinner s = (Spinner) findViewById(R.id.cursos);

        opcoes = (RadioGroup) findViewById(R.id.opoes);
        estagio = (RadioButton) findViewById(R.id.estagio);
        emprego = (RadioButton) findViewById(R.id.emprego);
        pesquisa = (RadioButton) findViewById(R.id.pesquisa);

        opcoes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                List<Object> objetos = new ArrayList<Object>();
                objetos.add(s.getSelectedItem());

                if (checkedId == R.id.estagio) {
                    objetos.add("estagios");
                } else if (checkedId == R.id.emprego) {
                    objetos.add("empregos");
                } else if (checkedId == R.id.pesquisa) {
                    objetos.add("pesquisas");
                }

                ringProgressDialog = ProgressDialog.show(SearchActivity.this, getResources().getString(R.string.warning_aguarde),
                        getResources().getString(R.string.warning_procurando_oportunidades), true);
                ringProgressDialog.show();
                new ConsultOportunidades(SearchActivity.this).execute(objetos);

            }
        });

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        ringProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.warning_aguarde),
                getResources().getString(R.string.warning_procurando_cursos), true);
        ringProgressDialog.show();
        taskCurso = new ConsultCurso(this).execute();

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner(List<Curso> cursos) {

        spinner_cursos = (Spinner) findViewById(R.id.cursos);

        final CursoAdapter dataAdapter = new CursoAdapter(this, android.R.layout.simple_spinner_dropdown_item, cursos);
        // dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        spinner_cursos.setAdapter(dataAdapter);


        if (id_curso != null && id_curso != "") {

            int id = 0;

            try {
                id = Integer.parseInt(id_curso);
            } catch (Exception e) {

            }

            int position_curso = dataAdapter.getPosition(dataAdapter.getItemById(id));
            spinner_cursos.setSelection(position_curso);
        }

        sharedPreferences = getSharedPreferences("curso", Context.MODE_PRIVATE);

        spinner_cursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                final Curso curso = (Curso) dataAdapter.getItem(position);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("curso", String.valueOf(curso.getId()));
                editor.commit();

                int teste = opcoes.getCheckedRadioButtonId();

                if (opcoes.getCheckedRadioButtonId() == -1) {
                    estagio.setChecked(true);
                }

                List<Object> objetos = new ArrayList<>();
                objetos.add(curso);

                if (opcoes.getCheckedRadioButtonId() == estagio.getId()) {
                    objetos.add("estagios");
                } else if (opcoes.getCheckedRadioButtonId() == emprego.getId()) {
                    objetos.add("empregos");
                } else if (opcoes.getCheckedRadioButtonId() == pesquisa.getId()) {
                    objetos.add("pesquisas");
                }

                ringProgressDialog = ProgressDialog.show(SearchActivity.this, getResources().getString(R.string.warning_aguarde),
                        getResources().getString(R.string.warning_procurando_oportunidades), true);
                ringProgressDialog.show();
                new ConsultOportunidades(SearchActivity.this).execute(objetos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                Toast.makeText(SearchActivity.this, "Escolha um curso", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onConcludeConsultCurso(List<Curso> cursos) {
        ringProgressDialog.dismiss();
        addItemsOnSpinner(cursos);
    }

    @Override
    public void onConcludeConsultOportunidade(List<Oportunidade> oportunidades) {
        ringProgressDialog.dismiss();

        adapter = new SearchAdapter(this, oportunidades);
        rv.setAdapter(adapter);

        if (oportunidades == null || oportunidades.isEmpty()) {
            Toast.makeText(this, "Nenhuma oportunidade encontrada", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void handleError(String error) {
        ringProgressDialog.dismiss();
        showError(error);
    }

    public void showError(String error) {
        Toast.makeText(this, error,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleResponse(Object object) {
        ringProgressDialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (ringProgressDialog.isShowing()) {
            taskCurso.cancel(true);
        }
        finish();
    }
}
