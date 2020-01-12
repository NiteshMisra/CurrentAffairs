package com.currentaffairs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CaResponse {
    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<CaModel> data;

    @SerializedName("total")
    private String total;

    public CaResponse(Integer sta, String msg, List<CaModel> list, String totl){
        this.status = sta;
        this.message = msg;
        this.data = list;
        this.total = totl;
    }

    public Integer getError() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    List<CaModel> getData() {
        return data;
    }
}
