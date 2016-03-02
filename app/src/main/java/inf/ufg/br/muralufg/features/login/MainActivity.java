package inf.ufg.br.muralufg.features.login;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import inf.ufg.br.muralufg.features.oportunidades.SearchActivity;
import inf.ufg.br.muralufg.R;
import inf.ufg.br.muralufg.features.push.QuickstartPreferences;
import inf.ufg.br.muralufg.features.push.RegistrationIntentService;
import inf.ufg.br.muralufg.utils.WebInterface;
import inf.ufg.br.muralufg.model.User;


public class MainActivity extends ActionBarActivity implements WebInterface, LoginAsyncTask.ConsultLogin {

    private ProgressDialog ringProgressDialog;
    private AsyncTask webConnection;
    private ProgressBar mRegistrationProgressBar;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView mInformationTextView;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private static final String CURSO = "curso";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

    }

    public void login(View v){
        checkFields();
        if(!isNetworkAvailable()){
            showError(getResources().getString(R.string.warning_internet));
        }else{
            LoginAsyncTask servico = new LoginAsyncTask(this);
            EditText entradaUsuario = (EditText)
                    findViewById(R.id.entrada_nome);
            EditText entradaSenha = (EditText)
                    findViewById(R.id.entrada_password);
            User usuario = new User(
                    entradaUsuario.getText().toString());
            usuario.setPassword(
                    entradaSenha.getText().toString());

            ringProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.warning_aguarde),
                    getResources().getString(R.string.warning_fazendo_login), true);
            ringProgressDialog.show();
            webConnection = servico;
            servico.execute(usuario);

        }
    }

    public void checkFields(){
        EditText entradaUsuario = (EditText)
                findViewById(R.id.entrada_nome);
        if(entradaUsuario.getText() == null || "".equals(entradaUsuario.getText().toString())){
            entradaUsuario.setError(getResources().getString(R.string.warning_empty_username));
            return;
        }
        EditText entradaSenha = (EditText) findViewById(R.id.entrada_password);
        if(entradaSenha.getText() == null || "".equals(entradaSenha.getText().toString())){
            entradaSenha.setError(getResources().getString(R.string.warning_empty_password));
            return;
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void showError(String error){
        Toast.makeText(this, error,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleError(String error) {
        ringProgressDialog.dismiss();
        showError(error);
    }
    
    @Override
    public void handleResponse(Object object) {
        //overrides abstract method handleResponse(Object) in WebInterface
    }

    public void searchVisitante(View v){
        int id = v.getId();
        if(id == R.id.button2){
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("titulo", (String) "Mural UFG");

            String lastSearch = getSharedPreferences(CURSO, Context.MODE_PRIVATE).getString(CURSO, "");

            intent.putExtra("curso", lastSearch);

            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(ringProgressDialog != null && ringProgressDialog.isShowing()){
            webConnection.cancel(true);
        }
    }

    @Override
    public void onConcludeConsultLogin(String curso) {
        ringProgressDialog.dismiss();

        if (curso == null || "".equals(curso.trim())) {
            showError("Usuario e/ou senha incorreto(s)");
        } else {

            Log.d("RESPONSE", curso);

            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("titulo", (String) "Mural UFG");
            //String lastSearch = getSharedPreferences("lastSearch", Context.MODE_PRIVATE).getString("curso", "");
            intent.putExtra("curso", curso);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
