package inf.ufg.br.muralufg.features.oportunidades;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import inf.ufg.br.muralufg.model.Curso;
import inf.ufg.br.muralufg.model.Oportunidade;


/**
 * Created by Marla Aragão.
 *
 * Classe responsavel por realizar
 * a consulta de oportunidades no web service
 *
 */

public class ConsultOportunidades extends AsyncTask<Object, Void, List<Oportunidade>> {

    private ConsultOportunidadeSituation listenerSituation;
    private static final String URL_CONNECTION = "https://dl.dropboxusercontent.com/s/mologtlfcosag0n/oportunidades.json?dl=0";
    private List<Oportunidade> oportunidades = new ArrayList<>();

    public ConsultOportunidades(ConsultOportunidadeSituation listenerSituation) {
        this.listenerSituation = listenerSituation;
    }

    @Override
    protected List<Oportunidade> doInBackground(Object... params) {

        if (params != null) {

            Curso c = (Curso) ((List) params[0]).get(0);
            String tipo = (String) ((List) params[0]).get(1);

            this.oportunidades = consultServer(c, tipo);
            return this.oportunidades;
        }

        return this.oportunidades;
    }

    /**
     * metodo resposavel pela conexão via http com o webservice
     *
     */
    private List<Oportunidade> consultServer(Curso curso, String opcao) {

        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        List<Oportunidade> oportunidadesList = new ArrayList<>();

        try {

            URL url = new URL(URL_CONNECTION);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            InputStream stream = conn.getInputStream();

            java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";

            try {


                JSONObject reader = new JSONObject(data);

                JSONArray cursosArray = reader.getJSONArray("cursos");

                for (int i = 0; i < cursosArray.length(); i++) {

                    if (curso != null && (curso.getId() != ((JSONObject) cursosArray.get(i)).getInt("id"))) {
                        continue;
                    }

                    JSONArray oportunityInformation = ((JSONObject) cursosArray.get(i)).getJSONObject("oportunidades").getJSONArray(opcao);

                    for (int j = 0; j < oportunityInformation.length(); j++) {

                        Oportunidade w = new Oportunidade();
                        w.setBolsa(((JSONObject) oportunityInformation.get(j)).getString("bolsa"));
                        w.setCidade(((JSONObject) oportunityInformation.get(j)).getString("cidade"));
                        w.setDescricao(((JSONObject) oportunityInformation.get(j)).getString("descricao"));
                        w.setEmail(((JSONObject) oportunityInformation.get(j)).getString("email"));
                        w.setEndereco(((JSONObject) oportunityInformation.get(j)).getString("endereco"));
                        w.setId(((JSONObject) oportunityInformation.get(j)).getInt("id"));
                        w.setLocal(((JSONObject) oportunityInformation.get(j)).getString("local"));
                        w.setTelefone(((JSONObject) oportunityInformation.get(j)).getString("telefone"));
                        w.setHoras(((JSONObject) oportunityInformation.get(j)).getString("horas"));
                        w.setTitulo(((JSONObject) oportunityInformation.get(j)).getString("titulo"));
                        w.setValor(((JSONObject) oportunityInformation.get(j)).getString("valor"));
                        w.setHorario(((JSONObject) oportunityInformation.get(j)).getString("horario"));

                        oportunidadesList.add(w);
                    }
                }

                return oportunidadesList;

            } catch (JSONException e) {
                Log.d("", "", e);
            }


            stream.close();
        } catch (Exception e) {
            Log.d("", "", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d("", "", e);
                }
            }
        }

        return oportunidadesList;
    }


    @Override
    protected void onPostExecute(List<Oportunidade> oportunidades) {
        listenerSituation.onConcludeConsultOportunidade(oportunidades);
    }


    public interface ConsultOportunidadeSituation {
        void onConcludeConsultOportunidade(List<Oportunidade> oportunidades);
    }
}
