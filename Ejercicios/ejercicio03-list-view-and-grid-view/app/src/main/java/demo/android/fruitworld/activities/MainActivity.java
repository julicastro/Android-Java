package demo.android.fruitworld.activities;

import demo.android.fruitworld.models.*;

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
import demo.android.fruitworld.adapters.FruitAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private GridView gridView;
    private FruitAdapter listfruitAdapter;
    private FruitAdapter gridfruitAdapter;

    private List<Fruit> fruits;

    private MenuItem listMenu;
    private MenuItem gridMenu;

    private final static Integer TIPO_LAYOUT_LIST = 0;
    private final static Integer TIPO_LAYOUT_GRID = 1;
    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        this.enforceIconBar();

        this.fruits = getAllFruits();

        this.listView = (ListView) findViewById(R.id.listView);
        this.gridView = (GridView) findViewById(R.id.gridView);

        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        this.listfruitAdapter = new FruitAdapter(this, R.layout.list_items, fruits);
        this.gridfruitAdapter = new FruitAdapter(this, R.layout.list_items, fruits);

        this.listView.setAdapter(this.listfruitAdapter);
        this.gridView.setAdapter(this.gridfruitAdapter);

    }

    private void enforceIconBar() {
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
        this.clicFruit(fruits.get(position));
    }

    private void clicFruit(Fruit fruit){
        if(fruit.getOrigen().equals("Desconocido")){
            Toast.makeText(this, "No se conoce el origen", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ta re piola esa fruta man", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        this.listMenu = menu.findItem(R.id.listViewLayout);
        this.gridMenu = menu.findItem(R.id.gridViewLayout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem:
                this.addFruit(new Fruit("Fruit Nro: " + (++this.counter), R.mipmap.ic_launcher, "Desconocido"));
                return true;
            case R.id.listViewLayout:
                this.switchListGridView(TIPO_LAYOUT_LIST);
                return true;
            case R.id.gridViewLayout:
                this.switchListGridView(TIPO_LAYOUT_GRID);
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
        menu.setHeaderTitle(this.fruits.get(info.position).getName());
        inflater.inflate(R.menu.context_manu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.deleteItem:
                this.deleteFruit(info.position);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchListGridView(int option) {
        if (option == TIPO_LAYOUT_LIST) {
            if (this.listView.getVisibility() == View.INVISIBLE) {
                this.gridView.setVisibility(View.INVISIBLE);
                this.gridMenu.setVisible(true);
                this.listView.setVisibility(View.VISIBLE);
                this.listMenu.setVisible(false);
            }
        } else if (option == TIPO_LAYOUT_GRID) {
            if (this.gridView.getVisibility() == View.INVISIBLE) {
                this.listView.setVisibility(View.INVISIBLE);
                this.listMenu.setVisible(true);
                this.gridView.setVisibility(View.VISIBLE);
                this.gridMenu.setVisible(false);
            }
        }
    }

    // CRUD Actions

    private List<Fruit> getAllFruits() {
        List<Fruit> list = new ArrayList<Fruit>() {{
            add(new Fruit("Banana", R.mipmap.ic_launcher_round, "Ecuador"));
            add(new Fruit("Manzana", R.mipmap.ic_launcher_round, "Escocia"));
            add(new Fruit("Pera", R.mipmap.ic_launcher_round, "Escocia"));
            add(new Fruit("Durazno", R.mipmap.ic_launcher_round, "Argentina"));
            add(new Fruit("Kiwi", R.mipmap.ic_launcher_round, "Brasil"));
            add(new Fruit("Ciruela", R.mipmap.ic_launcher_round, "Argentina"));
            add(new Fruit("Uva", R.mipmap.ic_launcher_round, "Peru"));
            add(new Fruit("Naranja", R.mipmap.ic_launcher_round, "Argentina"));
        }};
        return list;
    }

    private void addFruit(Fruit nueva){
        this.fruits.add(nueva);
        this.gridfruitAdapter.notifyDataSetChanged();
        this.listfruitAdapter.notifyDataSetChanged();
    }

    private void deleteFruit(int position){
        this.fruits.remove(position);
        this.gridfruitAdapter.notifyDataSetChanged();
        this.listfruitAdapter.notifyDataSetChanged();
    }







}