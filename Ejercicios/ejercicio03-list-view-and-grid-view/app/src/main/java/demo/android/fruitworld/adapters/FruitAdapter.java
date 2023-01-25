package demo.android.fruitworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import demo.android.fruitworld.R;
import demo.android.fruitworld.models.Fruit;

import java.util.List;

public class FruitAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List <Fruit> list;

    public FruitAdapter(Context context, int layout, List<Fruit> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    } // cuantas veces iterar sobre coleccion. el numero son los items q van a ser dibujados en el list_view

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
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
            holder.name = (TextView) convertView.findViewById(R.id.editTextTextPersonName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Fruit currentFruit = (Fruit) getItem(position);
        holder.name.setText(currentFruit.getName());
        return convertView;
    }

    static class ViewHolder{
        private TextView name;
        private TextView origin;
        private TextView icon;
    }



}
