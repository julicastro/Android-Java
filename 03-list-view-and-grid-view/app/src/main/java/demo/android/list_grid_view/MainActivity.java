package demo.android.list_grid_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        List<String> names = new ArrayList<>();
        names.add("Julian");
        names.add("Emanuel");
        names.add("Castro");
        names.add("Messi");
        names.add("Julian");
        names.add("Emanuel");
        names.add("Castro");
        names.add("Messi");
        names.add("Julian");
        names.add("Emanuel");
        names.add("Castro");
        names.add("Messi");

        // le decimos como queremos q muestre esos datos. se le pasa un layout
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Clicked: " + names.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        // enlazamos con nuestro adaptador
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_items, names);
        listView.setAdapter(myAdapter);

    }
    /*
        ListView. Lista de vistas q puede ser un boton, boton y textview, etc.
        Todos los twits x ejemplo son un ListView q tiene texto y botones de like, etc.
    */



}

