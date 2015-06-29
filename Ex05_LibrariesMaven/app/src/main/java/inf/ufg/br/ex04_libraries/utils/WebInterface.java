package inf.ufg.br.ex04_libraries.utils;

/**
 * Created by marceloquinta on 24/04/15.
 */
public interface WebInterface {

    public void handleError(String error);
    public void handleResponse(Object object);
}
