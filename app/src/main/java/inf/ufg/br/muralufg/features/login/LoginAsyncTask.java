package inf.ufg.br.muralufg.features.login;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import inf.ufg.br.muralufg.utils.WebInterface;
import inf.ufg.br.muralufg.model.User;


/**
 * Created by marceloquinta on 24/04/15.
 */
public class LoginAsyncTask extends AsyncTask<User, Void,String>{

    public static final int TIMEOUT = 10000;
    private final int SUCCESS = 200;
    private User user;
    private final String CONNECTION_URL = "http://www.inf.ufg.br";
    private WebInterface handler;
    private int responseCode;
    private String returnToken;

    public LoginAsyncTask(WebInterface handler){
        this.handler = handler;
    }

    @Override
    protected String doInBackground(User... params) {

        user = params[0];
        InputStream is = null;
        int len = 500;

        try {

            HttpPost post = new HttpPost(CONNECTION_URL);
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = TIMEOUT;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpClient client = new DefaultHttpClient(httpParameters);

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("user", user.getName()));
            pairs.add(new BasicNameValuePair("password", user.getPassword()));

            HttpResponse response = client.execute(post);

            responseCode = response.getStatusLine().getStatusCode();

            is = response.getEntity().getContent();
            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);

            return contentAsString;

        }catch (IOException e){
            return e.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    @Override
    protected void onPostExecute(String response) {
        if(responseCode == SUCCESS){
            handler.handleResponse(response);
        }else{
            handler.handleError(response);
        }
    }
}
