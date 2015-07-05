package inf.ufg.br.muralufg.features.oportunidades;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import inf.ufg.br.muralufg.R;
import inf.ufg.br.muralufg.model.Oportunidade;

/**
 * Created by Marla Aragão on 02/07/2015.
 */
public class DetailsActivity extends ActionBarActivity {

    Oportunidade oportunidade;
    TextView id;
    TextView horas;
    TextView horario;
    TextView titulo;
    TextView descricao;
    TextView cidade;
    TextView bolsa;
    TextView valor;
    TextView local;
    TextView endereco;
    Button telefone;
    TextView email;



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datails);
        getSupportActionBar().setTitle("Mural UFG");


        //getSupportActionBar().setSubtitle("teste");

        this.oportunidade = (Oportunidade)getIntent().getSerializableExtra("oportunidade");

        horas = (TextView)findViewById(R.id.horas_details);
        titulo = (TextView)findViewById(R.id.titulo_details);
        descricao = (TextView)findViewById(R.id.descricao_details);
        cidade = (TextView)findViewById(R.id.cidade_details);
        bolsa = (TextView)findViewById(R.id.bolsa_details);
        horario = (TextView)findViewById(R.id.horario_details);
        valor = (TextView)findViewById(R.id.valor_details);
        local = (TextView)findViewById(R.id.local_details);
        endereco = (TextView)findViewById(R.id.endereco_details);
        telefone = (Button) findViewById(R.id.telefone_details);
        email = (TextView)findViewById(R.id.email_details);

        // add PhoneStateListener
        PhoneStateListener phoneListener = new PhoneStateListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,
                PhoneStateListener.LISTEN_CALL_STATE);

        telefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + oportunidade.getTelefone()));
                startActivity(callIntent);
            }
        });

        horas.setText("Carga horária: " + oportunidade.getHoras() + " hs semanais");
        horario.setText("Horário: " + oportunidade.getHorario());
        titulo.setText(oportunidade.getTitulo().toUpperCase());
        descricao.setText("Descrição: " + oportunidade.getDescricao());
        cidade.setText("Cidade: " + oportunidade.getCidade().toUpperCase());
        bolsa.setText("Bolsa: " + (oportunidade.getBolsa().equals("S") ? "Possui" : "Não possui"));
        valor.setText("Valor da bolsa: R$ " + oportunidade.getValor());
        local.setText("Empresa/Instituição: " + oportunidade.getLocal());
        endereco.setText("Endereço: " + oportunidade.getEndereco());
        telefone.setText("" + oportunidade.getTelefone());
        email.setText("Email: " + Html.fromHtml("<a href=\"" + oportunidade.getEmail() + "\">" + oportunidade.getEmail() + "</a>"));

    }
 /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_datails_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean estaNaHorizontal() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    */
}
