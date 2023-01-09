package demo.android.design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textViewMain);

        // Tomar los datos del Intent del otro activity. Vienen en forma de Bundle (es una caja q contiene eso)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString("greetter") != null) {
            String greetter = bundle.getString("greetter");
            //Toast.makeText(SecondActivity.this, greetter, Toast.LENGTH_SHORT).show();
            textView.setText(greetter);
        } else {
            //Toast.makeText(SecondActivity.this, "El parametro vino null desde la otra vista xd", Toast.LENGTH_SHORT).show();
        }

        btn = (Button) findViewById(R.id.buttonSecondActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //.makeText(SecondActivity.this, "crear tercer activity", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class); // DESDE DONDE ESTAMOS A DONDE VAMOS
                startActivity(intent);
            }
        });
    }
}