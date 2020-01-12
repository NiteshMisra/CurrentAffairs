package com.currentaffairs;

import com.google.gson.annotations.SerializedName;

public class CaModel {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("date_and_time")
    private String dateTime;
    @SerializedName("pdf_link")
    private String pdfLink;
    @SerializedName("type")
    private String type;


    public CaModel(String id, String title,String dateTime, String pdfLink, String typ){
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.pdfLink = pdfLink;
        this.type = typ;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public String getType() {
        return type;
    }
}
