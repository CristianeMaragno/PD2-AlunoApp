package com.cristianerm.pd2;

class CustomObjectRecados {

    private String date;
    private String textInfo;

    public CustomObjectRecados(String prop1, String prop2) {
        this.date = prop1;
        this.textInfo = prop2;
    }

    public String getDate() {
        return date;
    }

    public String getTextInfo() {
        return textInfo;
    }
}
