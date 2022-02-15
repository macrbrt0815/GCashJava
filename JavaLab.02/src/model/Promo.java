package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Promo {
    protected String promoCode = "";
    protected String details = "";
    protected String shortCode = "";
    protected LocalDate startDate;
    protected LocalDate endDate;

    public Promo(String promoCode, String details, String shortCode, LocalDate startDate, LocalDate endDate){
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean addPromo(Connection connection){
        String sqlStatement = "insert into "
                + "promo(promoCode, details, shortCode, startDate, endDate)"
                + "values (?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, promoCode);
            preparedStatement.setString(2, details);
            preparedStatement.setString(3, shortCode);
            preparedStatement.setString(4, startDate.toString());
            preparedStatement.setString(5, endDate.toString());

            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException sqle) {
            System.out.println(sqle);
            return false;
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }

    }

}
