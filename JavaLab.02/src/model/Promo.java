package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Promo {
    protected String promoCode = "";
    protected String details = "";
    protected String shortCode = "";
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;

    public Promo(){

    }

    public Promo(String promoCode, String details, String shortCode, LocalDateTime startDate, LocalDateTime endDate){
        this.promoCode = promoCode;
        this.details = details;
        this.shortCode = shortCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void addPromo(Connection connection, Promo promo){

    }

    public void retrievePromoData(Connection connection){
        String sqlStatement = "SELECT * FROM promo";
        Statement statement = null;
        ResultSet resultSet = null;
        Promo retrievedPromo;
    }

}
