package demo.android.fruitworld.activities;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import demo.android.fruitworld.R;
import demo.android.fruitworld.adapters.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private GridView gridView;
    List<String> names;
    MyAdapter myAdapter;
    private int counter = 0;
    private final static Integer TIPO_LAYOUT_LIST = 0;
    private final static Integer TIPO_LAYOUT_GRID= 1;
    private Integer type_of_view = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        if(type_of_view == TIPO_LAYOUT_LIST){
            listView = (ListView) findViewById(R.id.listView);
        } else {
            gridView = (GridView) findViewById(R.id.gridView);
        }
        names = new ArrayList<>();
        names.add("Julian");
        names.add("Emanuel");
        names.add("Castro");
        names.add("Messi");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Clicked: " + names.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        myAdapter = new MyAdapter(this, R.layout.list_items, names);
        listView.setAdapter(myAdapter);

        registerForContextMenu(gridView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addItem:
                this.names.add("Added Nro: " + ++this.counter);
                // decimos al adapter q aumenta el array en 1. ya q este tiene get view donde llegan las positions
                myAdapter.notifyDataSetChanged(); // notifica y refresca
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // delete

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(names.get(info.position));
        inflater.inflate(R.menu.context_manu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.deleteItem:
                this.names.remove(info.position); // da posicion de item clickeado
                myAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}