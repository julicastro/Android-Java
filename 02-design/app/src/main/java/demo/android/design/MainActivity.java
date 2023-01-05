package demo.android.design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private final String GREETTER = "Mandando texto de un activity a otro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.buttonMain);
        /* implements View.OnClickListener -> btn.setOnClickListener(this); // this -> el q se implementa en la clase (solo en caseo de tener 1 listener) */
        /* R: clase donde se guardan todas las referencias de todo */

        // INTENT EXPLICITO

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "crear nueva vista", Toast.LENGTH_LONG).show();
                // acceder a nuevo activity y mandarle un string
                Intent intent = new Intent(MainActivity.this, SecondActivity.class); // DESDE DONDE ESTAMOS A DONDE VAMOS
                intent.putExtra("greetter", GREETTER); // clave - valor
                startActivity(intent);
            }
        });
    }
}