package inf.ufg.br.ex04_libraries.features.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import inf.ufg.br.ex04_libraries.SecondActivity;
import inf.ufg.br.ex04_libraries.features.colors.ColorsActivity;
import inf.ufg.br.ex04_libraries.R;
import inf.ufg.br.ex04_libraries.utils.WebInterface;
import inf.ufg.br.ex04_libraries.model.User;


public class MainActivity extends AppCompatActivity implements WebInterface {

    private ProgressDialog ringProgressDialog;
    private AsyncTask webConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }


    public void set_visitante(View v) {
         int id = v.getId();
        if(id == R.id.modo_visitante){
            Intent i = new Intent(this, SecondActivity.class);
            startActivity(i);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_second_activity) {
            startActivity(new Intent(this, SecondActivity.class));
        }

        return super.onOptionsItemSelected(item);
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
        ringProgressDialog.dismiss();
        Log.d("RESPONSE", (String) object);

        Intent intent = new Intent(this, ColorsActivity.class);
        intent.putExtra("test", "UFG");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(ringProgressDialog.isShowing()){
            webConnection.cancel(true);
        }
    }
}
