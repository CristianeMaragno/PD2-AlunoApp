package com.cristianerm.pd2;

public class DiarioTurmaInformation {

    private String mDate;
    private String mMensagem;
    private String mImageUrl;

    public DiarioTurmaInformation() {
        //empty constructor needed
    }

    public String getDate() {
        return mDate;
    }
    public void setDate(String date) {
        mDate = date;
    }
    public String getMensagem() {
        return mMensagem;
    }
    public void setMensagem(String mensagem) {
        mMensagem = mensagem;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

}

