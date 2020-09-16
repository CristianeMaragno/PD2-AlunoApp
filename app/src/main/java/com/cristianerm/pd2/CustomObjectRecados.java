package com.cristianerm.pd2;

class CustomObjectRecados {

    private String date;
    private String textInfo;
    private String lidoStatus;

    public CustomObjectRecados(String prop1, String prop2, String prop3) {
        this.date = prop1;
        this.textInfo = prop2;
        this.lidoStatus = prop3;
    }

    public String getDate() {
        return date;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public String getLidoStatus() {
        return lidoStatus;
    }
}
