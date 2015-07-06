package inf.ufg.br.ex04_libraries.features.oportunidades;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import inf.ufg.br.ex04_libraries.model.Oportunidade;
import inf.ufg.br.muralufg.R;

/**
 * Created by Marla Aragão on 02/07/2015.
 */
public class DetailsActivity extends FragmentActivity {

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

        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_datails);

        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        Bitmap image = takeScreenShot(this);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("fitness:distance:units6", "Cidade: " + oportunidade.getCidade().toUpperCase())
                .putString("og:description", "Vaga: " + oportunidade.getTitulo().toUpperCase())
                .putString("fitness:duration:units", "Descrição: " + oportunidade.getDescricao())
                .putString("og:type", "Carga horária: " + oportunidade.getHoras() + " hs semanais")
                .putString("og:title", "Horário: " + oportunidade.getHorario())
                .putString("fitness:speed:units", "Bolsa: " + (oportunidade.getBolsa().equals("S") ? "Possui" : "Não possui"))
                .putString("fitness:speed:units2", "Valor da bolsa: R$ " + oportunidade.getValor())
                .putString("fitness:speed:units3", "Empresa/Instituição: " + oportunidade.getLocal())
                .putString("fitness:speed:units8", "Endereço: " + oportunidade.getEndereco())
                .putString("fitness:speed:units9", "Telefone: " + oportunidade.getTelefone())
                .putString("fitness:speed:units7", "Email: " + Html.fromHtml("<a href=\"" + oportunidade.getEmail() + "\">" + oportunidade.getEmail() + "</a>"))
                .build();
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("fitness.runs")
                .putObject("fitness:course", object)
                .build();
        ShareOpenGraphContent content1 = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("fitness:course")
                .setAction(action)
                .build();

        shareButton.setShareContent(content1);

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
        titulo.setText("Vaga: " + oportunidade.getTitulo().toUpperCase());
        descricao.setText("Descrição: " + oportunidade.getDescricao());
        cidade.setText("Cidade: " + oportunidade.getCidade().toUpperCase());
        bolsa.setText("Bolsa: " + (oportunidade.getBolsa().equals("S") ? "Possui" : "Não possui"));
        valor.setText("Valor da bolsa: R$ " + oportunidade.getValor());
        local.setText("Empresa/Instituição: " + oportunidade.getLocal());
        endereco.setText("Endereço: " + oportunidade.getEndereco());
        telefone.setText("Telefone: " + oportunidade.getTelefone());
        email.setText("Email: " + Html.fromHtml("<a href=\"" + oportunidade.getEmail() + "\">" + oportunidade.getEmail() + "</a>"));

    }

    public boolean estaNaHorizontal() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private static Bitmap takeScreenShot(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
}
