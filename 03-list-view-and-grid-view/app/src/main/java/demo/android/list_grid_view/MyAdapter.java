package demo.android.list_grid_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List <String> names;

    public MyAdapter(Context context, int layout, List<String> names) {
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    @Override
    public int getCount() {
        return this.names.size();
    } // cuantas veces iterar sobre coleccion. el numero son los items q van a ser dibujados en el list_view

    @Override
    public Object getItem(int position) {
        return this.names.get(position);
    } // devuele un item de la posicion

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // viewHolderPattern nos permite aumentar productividad

        ViewHolder holder;
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(this.layout, null);
            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.editTextTextPersonName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = names.get(position);
        holder.nameTextView.setText(name);
        return convertView;
    }

    static class ViewHolder{
        private TextView nameTextView;
    }



}
