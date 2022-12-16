package demo.android.firstapp;

import android.content.Context;
import android.widget.Toast;

public class Test {

    public static void myToast(Context context){
        Toast.makeText(context, "Probando context", Toast.LENGTH_LONG).show();
    }

}
