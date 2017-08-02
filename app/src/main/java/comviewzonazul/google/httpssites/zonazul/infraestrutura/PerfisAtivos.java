package comviewzonazul.google.httpssites.zonazul.infraestrutura;

public enum PerfisAtivos {
    CLIENTE(1),VENDEDOR(2),AGENTE(3);
    private int perfil;

    PerfisAtivos(int valor) {
        this.setPerfil(valor);
    }

    public int getPerfil(){
        return perfil;
    }

    public void setPerfil(int valor){
        this.perfil = valor;
    }
}
