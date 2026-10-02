package br.com.fiap.petfiap.model;

public class GeradorProtocolo {

    private static GeradorProtocolo instancia;

    private int contador;

    private GeradorProtocolo() {
        contador = 0;
    }

    public static GeradorProtocolo getInstancia() {
        if (instancia == null) {
            instancia = new GeradorProtocolo();
        }
        return instancia;
    }

    public int proximo() {
        contador++;
        return contador;
    }
}
