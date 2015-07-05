package inf.ufg.br.muralufg.utils;

/**
 * Created by marceloquinta on 24/04/15.
 */
public interface WebInterface {

    public void handleError(String error);
    public void handleResponse(Object object);
}
