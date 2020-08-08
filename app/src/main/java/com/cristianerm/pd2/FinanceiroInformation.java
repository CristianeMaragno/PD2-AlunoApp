package com.cristianerm.pd2;

public class FinanceiroInformation {
    String mes;
    String ano;
    String boleto_url;

    public FinanceiroInformation(){

    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getBoleto_url() {
        return boleto_url;
    }

    public void setBoleto_url(String boleto_url) {
        this.boleto_url = boleto_url;
    }
}
