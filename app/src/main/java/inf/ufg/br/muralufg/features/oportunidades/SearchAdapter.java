package inf.ufg.br.muralufg.features.oportunidades;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import inf.ufg.br.muralufg.R;
import inf.ufg.br.muralufg.model.Oportunidade;

/**
 * Created by Marla Aragão.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.OportunidadeViewHolder> {

    private List<Oportunidade> oportunidades;
    Context context;

    public SearchAdapter(Context context, List<Oportunidade> oportunidades){
        this.oportunidades = oportunidades;
        this.context = context;
    }

    @Override
    public OportunidadeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_oportunidade, viewGroup, false);
        
        return new OportunidadeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OportunidadeViewHolder holder, final int position) {
        holder.oportunidadeName.setText(oportunidades.get(position).getTitulo());
        holder.oportunidadeCidade.setText("Cidade: " + oportunidades.get(position).getCidade());
        holder.oportunidadeTipo.setText("Horas semanais: " + oportunidades.get(position).getHoras());

        if(oportunidades.get(position).getBolsa().equalsIgnoreCase("S")) {
            holder.oportunidadeValor.setText("Salário/Bolsa: " + oportunidades.get(position).getValor());
        } else {
            holder.oportunidadeValor.setText("Salário/Bolsa: Não possui");
        }

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("oportunidade", (Serializable) oportunidades.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (oportunidades != null)
            return oportunidades.size();
        else
            return 0;
    }

    public static class OportunidadeViewHolder extends RecyclerView.ViewHolder {
        TextView oportunidadeCidade;
        TextView oportunidadeTipo;
        CardView cv;
        TextView oportunidadeName;
        TextView oportunidadeValor;

        OportunidadeViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);
            oportunidadeName = (TextView)itemView.findViewById(R.id.nome_oportunidade);
            oportunidadeValor = (TextView)itemView.findViewById(R.id.valor_bolsa);
            oportunidadeTipo = (TextView)itemView.findViewById(R.id.tipo);
            oportunidadeCidade = (TextView)itemView.findViewById(R.id.cidade);
        }
    }

}
