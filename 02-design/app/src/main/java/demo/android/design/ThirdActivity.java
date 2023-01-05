package demo.android.design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextTextPersonName;
    private ImageButton imageButtonPhone;
    private ImageButton imageButtonName;
    private ImageButton imageButtonCamara;
    private final int PHONE_CALL_CODE = 100;

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
                if (phoneNumber != null) {
                    // COMPROBAR VERSION ACTUAL DE ANDROID USADA
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                    } else {
                        OlderVersions(phoneNumber);
                    }
                }
            }

            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber));
                if (CheckPerrmission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "You decline the access", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    // sobreescribimos metodo para respuesta de permiso

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // llega codigo mandado al 2do parametro del array del if(CheckPerrmission(Manifest.permission.CALL_PHONE)){
        switch (requestCode) {
            case PHONE_CALL_CODE:
                // estamos en el caso del telefono (para eso es el phone_code)
                String permission = permissions[0];
                int result = grantResults[0];
                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    // comprobar si ha sido aceptado o no la peticion de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        // concedio permiso
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intentCall);
                    } else {
                        // no lo hizo
                        Toast.makeText(ThirdActivity.this, "No lo concedio el permiso", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        }


    }

    private boolean CheckPerrmission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
        // compureba si hay permiso. hay q agregarlo a manifest
    }

}