package comviewzonazul.google.httpssites.zonazul.cliente.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import comviewzonazul.google.httpssites.zonazul.R;

public class CompraActivity extends AppCompatActivity {
    EditText creditotxt;
    int credito;
    Context context = getApplicationContext();
    String PREFERENCE_NAME = "CompraActivityPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        creditotxt = (EditText)findViewById(R.id.editText);
        int credito =  Integer.parseInt(creditotxt.getText().toString());
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor     = sharedPreferences.edit();
        editor.putInt("CREDITO", credito);
    }

    public void cartao(View view){
        startActivity(new Intent(this, CompraCartaoActivity.class));
        finish();
    }






}
