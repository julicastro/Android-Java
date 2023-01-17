package demo.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText editText;
    private final String INGRESO = "Ha ingresado correctamente.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.buttonMainActivity);
        editText = (EditText) findViewById(R.id.editTextTextPersonNameMainActivity);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTextString = editText.getText().toString();
                if (!editTextString.isEmpty()){
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class); // DESDE DONDE ESTAMOS A DONDE VAMOS
                    intent.putExtra("greetter", INGRESO); // clave - valor
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Por favor escriba su nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}