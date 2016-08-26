package com.example.ashostak89.studiofrench;

/**
 * Created by ashostak89 on 8/26/2016.
 */
public class Prices {
    private String  procedureName;
    private String  procedurePrice;

    public Prices(String procedureName, String procedurePrice) {
        this.procedureName = procedureName;
        this.procedurePrice = procedurePrice;
    }

    public void setProcedurePrice(String procedurePrice) {
        this.procedurePrice = procedurePrice;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public String getProcedurePrice() {
        return procedurePrice;
    }
}
