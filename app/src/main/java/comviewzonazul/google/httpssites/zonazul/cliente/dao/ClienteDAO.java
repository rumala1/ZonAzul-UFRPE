package comviewzonazul.google.httpssites.zonazul.cliente.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import comviewzonazul.google.httpssites.zonazul.cliente.dominio.Cliente;
import comviewzonazul.google.httpssites.zonazul.cliente.dominio.Endereco;
import comviewzonazul.google.httpssites.zonazul.infraestrutura.DatabaseHelper;
import comviewzonazul.google.httpssites.zonazul.usuario.dao.UsuarioDAO;

public class ClienteDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    Cliente cliente;
    UsuarioDAO usuarioDAO;
    String login;
    Context context_;
    String PREFERENCE_NAME = "LoginActivityPreferences";

    public ClienteDAO(Context context){
        context_ = context;
        databaseHelper = new DatabaseHelper(context);
        usuarioDAO = new UsuarioDAO(context);
    }

    public ClienteDAO(Context context, Cliente cliente_){
        context_ = context;
        databaseHelper = new DatabaseHelper(context);
        cliente = cliente_;
    }

    private SQLiteDatabase getDatabase(){

        if (database == null){
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    public void retornoLogin(){
        SharedPreferences prefs = context_.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        login= prefs.getString("LOGIN", null);
    }


    public boolean buscarClienteEmail(){
        String email = cliente.getEmail();
        Cursor cursor = getDatabase().query("clientes", new String[]{"*"}, "email=?", new String[]{email}, null, null, null, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        }
        return true;
    }

    public void salvarCliente(){
        retornoLogin();
        ContentValues valores = new ContentValues();
        int id_usuario = retornarId(login);
        Toast.makeText(context_,Integer.toString(id_usuario), Toast.LENGTH_LONG).show();
        valores.put(DatabaseHelper.Clientes.SALDO, "0");
        valores.put(DatabaseHelper.Clientes.EMAIL, cliente.getEmail());
        valores.put(DatabaseHelper.Clientes.CEP, cliente.getEndereco().getCep());
        valores.put(DatabaseHelper.Clientes.COMPLEMENTO, cliente.getEndereco().getComplemento());
        valores.put(DatabaseHelper.Clientes.NUMERO, cliente.getEndereco().getNumero());
        valores.put(DatabaseHelper.Clientes.CIDADE, cliente.getEndereco().getCidade());
        valores.put(DatabaseHelper.Clientes.USUARIO, id_usuario);
        long id_cliente = getDatabase().insert(DatabaseHelper.Clientes.TABELA_CLIENTES, null, valores);
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.Perfis.ID_USUARIO,id_usuario);
        cv.put(DatabaseHelper.Perfis.ID_PERFIL,"1"); ////nao precisa saber qual é o cliente??? pode pegar o cliente vendo na tabela cliente na coluna usuario se é igual
        getDatabase().update(DatabaseHelper.Perfis.TABELA_PERFIS, cv, "usuario="+id_usuario, null);//id o usuario aqui
        getDatabase().close();
    }

    public Cliente BuscarClientePorUsuario(int id){
        Cursor cursor = getDatabase().query(DatabaseHelper.Clientes.TABELA_CLIENTES, DatabaseHelper.Clientes.COLUNAS_CLIENTES, DatabaseHelper.Clientes.USUARIO + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
            Endereco endereco = new Endereco(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.NUMERO)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.COMPLEMENTO)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.CEP)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.CIDADE))
            );
            Cliente cliente = new Cliente(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Clientes._ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.EMAIL)),
                    endereco

            );

            return cliente;
        }
        Toast.makeText(context_,"eae", Toast.LENGTH_LONG).show();
        return null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public  int retornarId(String login_) {
            Cursor cursor = getDatabase().query(DatabaseHelper.Usuarios.TABELA, DatabaseHelper.Usuarios.COLUNAS, DatabaseHelper.Usuarios.LOGIN + "=?", new String[]{ login }, null, null, null);

            if(cursor != null){
                cursor.moveToFirst();
            }
            int id = Integer.parseInt(cursor.getString(0));
            return id;
    }
}

