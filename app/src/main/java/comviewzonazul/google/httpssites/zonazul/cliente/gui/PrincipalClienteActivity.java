 package comviewzonazul.google.httpssites.zonazul.cliente.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import comviewzonazul.google.httpssites.zonazul.R;
import comviewzonazul.google.httpssites.zonazul.cliente.dominio.Cliente;
import comviewzonazul.google.httpssites.zonazul.cliente.negocio.ClienteNegocio;
import comviewzonazul.google.httpssites.zonazul.usuario.dominio.Usuario;
import comviewzonazul.google.httpssites.zonazul.usuario.gui.EscolhaPerfilActivity;
import comviewzonazul.google.httpssites.zonazul.usuario.negocio.UsuarioNegocio;

 public class PrincipalClienteActivity extends AppCompatActivity {
     private static final String MANTER_CONECTADO = "manter_conectado";
     private static final String PREFERENCE_NAME = "LoginActivityPreferences";
     Usuario usuario = new Usuario();
     Usuario user = new Usuario();
     Cliente cliente = new Cliente();
     TextView nomeUser, saldoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_cliente);
        SharedPreferences preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        usuario.setLogin(preferences.getString("LOGIN", null));
        usuario.setSenha(preferences.getString("SENHA", null));
        encontraCliente();
        encontrarItens();
        editarItens();
    }

    public void encontraCliente(){
        UsuarioNegocio usuarioNegocio = new UsuarioNegocio(getApplicationContext(), usuario);
        user = usuarioNegocio.pegaUsuario(usuario.getLogin(), usuario.getSenha());
        Context context = getApplicationContext();
        Toast.makeText(context,user.getNome(), Toast.LENGTH_LONG).show();
        ClienteNegocio clientenegocio = new ClienteNegocio(getApplicationContext());
        cliente = clientenegocio.retornaCliente(user.get_id());
    }

    public void editarItens(){
        nomeUser.setText(user.getNome().toUpperCase()+"");
        saldoUsuario.setText("R$" +(cliente.getSaldo()) +"");
    }

     public void encontrarItens() {
         nomeUser = (TextView) findViewById(R.id.textViewNome);
         saldoUsuario = (TextView)findViewById(R.id.textViewSaldo);
     }

    public void onBackPressed(){
        Intent intent = new Intent();
        intent.setClass(this, EscolhaPerfilActivity.class);
        startActivity(intent);
        finish();
    }
}



