package demo.android.cardandrecicleview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> names;
    private int layout;
    private OnItemClickListener itemClickListener;

    public MyAdapter(List<String> names, int layout, OnItemClickListener onItemClickListener) {
        this.names = names;
        this.layout = layout;
        this.itemClickListener = onItemClickListener;
    }

    @NonNull
    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @androidx.annotation.NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
        // nos obliga a devolver vh. a q usemos este patron de diseño vh
    }

    @Override
    public void onBindViewHolder(@NonNull @androidx.annotation.NonNull ViewHolder holder, int i) {
        // cambia los datos.
        holder.bind(names.get(i), itemClickListener);

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;

        public ViewHolder(@NonNull @androidx.annotation.NonNull View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textView);
        }

        public void bind(final String name, final OnItemClickListener listener){
            this.textViewName.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(name, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick (String name, int position);
    }


}
