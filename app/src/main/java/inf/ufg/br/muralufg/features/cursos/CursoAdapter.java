package inf.ufg.br.muralufg.features.cursos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import inf.ufg.br.muralufg.model.Curso;

/**
 * Created by Marla Aragão on 01/07/2015.
 */
public class CursoAdapter extends ArrayAdapter<Curso> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Curso)
    private List<Curso> values;

    public CursoAdapter(Context context, int textViewResourceId,
                        List<Curso> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public Curso getItem(int position) {
        return values.get(position);
    }

    public Curso getItemById(int id) {

        for (Curso c : values) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public long getItemId(int position) {
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(android.graphics.Color.BLACK);
        // Then you can get the current item using the values array (Cursos array) and the current position
        // You can NOW reference each method you has created in your bean object (Curso class)
        label.setText(values.get(position).getNome().toUpperCase());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(android.graphics.Color.BLACK);
        label.setText(values.get(position).getNome().toUpperCase());

        return label;
    }

}
