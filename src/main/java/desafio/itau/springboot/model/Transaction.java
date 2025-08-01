package desafio.itau.springboot.model;

import java.time.OffsetDateTime;

public class Transaction {

    private double valor;
    private OffsetDateTime dataHora;

    public Transaction(double valor, OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public double GetValor() {
        return valor;
    }

    public OffsetDateTime GetDataHora() {
        return dataHora;
    }
}
