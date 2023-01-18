package demo.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.CheckableGroup;

public class SecondActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private Button button;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private TextView textView;
    private Integer edad = 0;
    private String mensajeAEnviar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Tomar los datos del Intent del otro activity. Vienen en forma de Bundle (es una caja q contiene eso)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString("greetter") != null) {
            String greetter = bundle.getString("greetter");
            Toast.makeText(SecondActivity.this, greetter, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SecondActivity.this, "El parametro vino null desde la otra vista xd", Toast.LENGTH_SHORT).show();
        }

        seekBar = (SeekBar) findViewById(R.id.seekBarSecActivity);
        button = (Button) findViewById(R.id.buttonSecActivity);
        radioButton = (RadioButton) findViewById(R.id.radioButtonSecActivity);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2SecActivity);
        textView = (TextView) findViewById(R.id.textViewSecActivity);

        seekBar.setMax(100);
        seekBar.setProgress(0); // Set initial progress value

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                edad = i;
                textView.setText(String.valueOf(edad));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (edad < 18 || edad >= 60) {
                    Toast.makeText(SecondActivity.this, "Debe tener entre 18 y 60 años", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SecondActivity.this, "Edad Correcta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edad >= 18 && edad <= 60 && edad != null) {
                    if (radioButton.isChecked() || radioButton2.isChecked()) {
                        if (radioButton.isChecked()) {
                            mensajeAEnviar = "Hola";
                        } else if (radioButton2.isChecked()) {
                            mensajeAEnviar = "Chau";
                        }
                        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class); // DESDE DONDE ESTAMOS A DONDE VAMOS
                        intent.putExtra("edad", mensajeAEnviar + ". Tu edad es: " + edad); // clave - valor
                        startActivity(intent);
                    } else {
                        Toast.makeText(SecondActivity.this, "Elija un radiobuton", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SecondActivity.this, "Edad Incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}