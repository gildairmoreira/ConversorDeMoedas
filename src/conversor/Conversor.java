package conversor;

import modelo.TabelaMoedas;

public class Conversor {

    ConsultaExchangerateAPI exchangerateAPI;
    TabelaMoedas tabelaMoedas;

    public Conversor() {
        this.exchangerateAPI = new ConsultaExchangerateAPI();
        this.tabelaMoedas = exchangerateAPI.consultar("usd");
    }

    public double dolarPraReal(double dolar) {
        return dolar * tabelaMoedas.brl();
    }

    public double realPraDolar(double real){
        return real / tabelaMoedas.brl();
    }

    public double dolarPraPesoArgentino(double dolar){
        return dolar * tabelaMoedas.ars();
    }

    public double pesoArgentinoPraDolar(double peso){
        return peso / tabelaMoedas.ars();
    }

    public double dolarPraPesoColombiano(double dolar){
        return dolar * tabelaMoedas.cop();
    }

    public double pesoColombianoPraDolar(double peso){
        return peso / tabelaMoedas.cop();
    }
}
