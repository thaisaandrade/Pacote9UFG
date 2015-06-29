package inf.ufg.br.ex04_libraries.features.colors;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import inf.ufg.br.ex04_libraries.R;
import inf.ufg.br.ex04_libraries.model.Color;

/**
 * Created by marceloquinta on 24/04/15.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private List<Color> colors;

    public ColorAdapter(List<Color> colors){
        this.colors = colors;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_color, viewGroup, false);
        ColorViewHolder pvh = new ColorViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, final int position) {
        holder.colorName.setText(colors.get(position).getNome());
        holder.colorCode.setText(colors.get(position).getHex());
        holder.colorImage.setBackgroundColor(android.graphics.Color.parseColor(
                colors.get(position).getHex()));
        holder.colorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView colorName;
        TextView colorCode;
        View colorImage;

        ColorViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);
            colorName = (TextView)itemView.findViewById(R.id.colorName);
            colorCode = (TextView)itemView.findViewById(R.id.colorValue);
            colorImage = itemView.findViewById(R.id.icon);
        }
    }

    public void remove(int position) {
        colors.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    public void add(int position, Color color) {
        colors.add(position, color);
        notifyDataSetChanged();
    }

}
