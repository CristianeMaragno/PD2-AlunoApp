package com.cristianerm.pd2;

public class FinanceiroInformation {
    String mes;
    Long ano;
    String boleto_url;

    public FinanceiroInformation(){

    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Long getAno() {
        return ano;
    }

    public void setAno(Long ano) {
        this.ano = ano;
    }

    public String getBoleto_url() {
        return boleto_url;
    }

    public void setBoleto_url(String boleto_url) {
        this.boleto_url = boleto_url;
    }
}
