package demo.android.design;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextTextWeb;
    private ImageButton imageButtonPhone;
    private ImageButton imageButtonWeb;
    private ImageButton imageButtonCamara;
    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMARA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextTextWeb = (EditText) findViewById(R.id.editTextTextPersonName);
        imageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imageButtonWeb = (ImageButton) findViewById(R.id.imageButtonName);
        imageButtonCamara = (ImageButton) findViewById(R.id.imageButtonCamara);

        // cuando toque el boton del telefono q pase algo
        // INTENT IMPLICITO

        // botón para llamadas
        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editTextPhone.getText().toString();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    // COMPROBAR VERSION ACTUAL DE ANDROID USADA
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // comprobar si aceptó, no o nunca se le preguntó
                        if (CheckPerrmission((Manifest.permission.CALL_PHONE))) {
                            // ACEPTÓ
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                            startActivity(i);
                        } else {
                            // DENEGÓ O NO ACEPTÓ O PRIMERA VEZ Q SE LE PREGUNTA
                            if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                // no se le ha preguntado aun
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                            } else {
                                // Ha denegado
                                Toast.makeText(ThirdActivity.this, "Please enable permission", Toast.LENGTH_SHORT).show();
                                // en intent podemos ir a ciertas ventanas
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                // no se vuelve a la app anterior sino a la mimma
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }
                        //requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                        // es async x lo q no devuelve result instantaneo.
                        // llama al onRequestPermissionsResult
                    } else {
                        OlderVersions(phoneNumber);
                    }
                } else {
                    Toast.makeText(ThirdActivity.this, "No hay numero de tel", Toast.LENGTH_SHORT).show();
                }

                // else -> ya lo valida la app del telefono
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

        // boton para la web
        imageButtonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editTextTextWeb.getText().toString();
                if(url != null && !url.isEmpty()){
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+url));
                    // intentWeb.setAction (Intent.ACTION_VIEW)
                    // intentWeb.setData (Uri.parse("http://"+url))

                    // CONTACTOS
                    Intent intentContact = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));

                    // EMAIL RAPIDO
                    String email = "castroejulian@gmail.com";
                    Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email));

                    // MAIL COMPLETO
                    Intent iMailCompleto = new Intent(Intent.ACTION_VIEW, Uri.parse(email));
                    iMailCompleto.setType("message/rfc822");
                    // iMailCompleto.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    iMailCompleto.putExtra(Intent.EXTRA_SUBJECT, "Mail's title:");
                    iMailCompleto.putExtra(Intent.EXTRA_TEXT, "HI MY NAME IS JULIAN");
                    iMailCompleto.putExtra(Intent.EXTRA_EMAIL, new String[]{"juli@mail", "hola@mail"});
                    startActivity(Intent.createChooser(iMailCompleto, "Elige cliente del correo"));
                    // TELEFONO 2 sin permiosos
                    Intent iPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:32132654"));
                    //startActivity(intentWeb);
                }
            }
        });

        // boton para la camara
        imageButtonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamara, PICTURE_FROM_CAMARA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case PICTURE_FROM_CAMARA:
                if (requestCode == RESULT_OK) {
                    String result = data.toUri(0);
                    Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "There was an error with the picture. Please try again", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);

        }
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