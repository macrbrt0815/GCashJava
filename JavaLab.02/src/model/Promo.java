package model;


import java.time.LocalDateTime;

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

    @Override
    public String toString() {
        return "PROMO" +
                "\nPromo Code: " + promoCode +
                "\nDetails: " + details +
                "\nShort Code: " + shortCode +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate;
    }
}
