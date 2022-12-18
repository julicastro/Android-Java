package demo.android.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextTextPersonName;
    private ImageButton imageButtonPhone;
    private ImageButton imageButtonName;
    private ImageButton imageButtonCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        imageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imageButtonName = (ImageButton) findViewById(R.id.imageButtonName);
        imageButtonCamara = (ImageButton) findViewById(R.id.imageButtonCamara);

        // cuando toque el boton del telefono q pase algo
        // INTENT IMPLICITO

        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editTextPhone.getText().toString();
                if(phoneNumber != null){
                    Intent intentCal = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber));
                    startActivity(intentCal);
                }
            }
        });

    }
}