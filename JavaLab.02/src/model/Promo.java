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

    protected ArrayList<Promo> allPromo = new ArrayList<>();
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    final private static Logger logger = Logger.getLogger(Promo.class.getName());

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
        boolean promoExist = false;

        retrievePromoData(connection);

        for (int index = 0; index < allPromo.size(); index ++) {
            if(allPromo.get(index).getShortCode().equals(promo.getShortCode())) {
                System.out.println("Promo already exist");
                promoExist = true;
            }
        }

        if(!promoExist){
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
                System.out.println("Promo added");
            }catch(SQLException sqle) {
                System.out.println(sqle);
            }catch(Exception e) {
                System.out.println(e);
            }
        }

    }

    public void retrievePromoData(Connection connection){
        String sqlStatement = "SELECT * FROM promo";
        Statement statement = null;
        ResultSet resultSet = null;
        Promo retrievedPromo;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while(resultSet.next()){
                retrievedPromo = new Promo(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        LocalDateTime.parse(resultSet.getString(4), format),
                        LocalDateTime.parse(resultSet.getString(5), format));

                allPromo.add(retrievedPromo);
            }


        } catch (SQLException e){
            logger.log(Level.SEVERE, "SQLException", e);
        } finally {
            try{
                if (statement != null){
                    statement.close();
                } if (resultSet != null){
                    resultSet.close();
                }
            } catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }
    }

}
