package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PromoTransactions implements ManagePromo {
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    final private static Logger logger = Logger.getLogger(PromoTransactions.class.getName());

    //collection of all the retrieved Promos
    protected ArrayList<Promo> allPromo = new ArrayList<>();

    String sqlStatement = "";
    Statement statement = null;
    ResultSet resultSet = null;
    boolean isPromoEmpty;
    Promo retrievedPromo;

    @Override //insert a promo into database
    public  void insertPromo(Connection connection, Promo promo) {
        boolean promoExist = false;

        //retrieve all promo in the database
        retrievePromo(connection);

        //loop through all promos, verify if promo is already in the database
        //verify using promoCode and shortCode
        for (Promo prm : allPromo) {
            if (prm.getPromoCode().equalsIgnoreCase(promo.getPromoCode()) ||
                    prm.getShortCode().equalsIgnoreCase(promo.getShortCode())) {
                promoExist = true;
                logger.log(Level.INFO, "Promo already exist");

                break;
            }
        }

        //if promo not yet in the database, insert promo
        if(!promoExist){
            sqlStatement = "INSERT INTO "
                    + "promo(promoCode, details, shortCode, startDate, endDate)"
                    + "values (?,?,?,?,?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

                preparedStatement.setString(1, promo.getPromoCode());
                preparedStatement.setString(2, promo.getDetails());
                preparedStatement.setString(3, promo.getShortCode());
                preparedStatement.setString(4, promo.getStartDate().toString());
                preparedStatement.setString(5, promo.getEndDate().toString());

                preparedStatement.executeUpdate();
                logger.log(Level.INFO, "Promo added");

            } catch (SQLException sqle){
                logger.log(Level.SEVERE, "SQLException", sqle);
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

    @Override //retrieve ALL promos from database
    public ArrayList retrievePromo(Connection connection) {
        isPromoEmpty = false;

        //retrieve all promo
        sqlStatement = "SELECT * FROM promo";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            if(!resultSet.next()){
                isPromoEmpty = true;
            } else {
                while (resultSet.next()) {
                    retrievedPromo = new Promo(resultSet.getString("promoCode"),
                            resultSet.getString("details"),
                            resultSet.getString("shortCode"),
                            LocalDateTime.parse(resultSet.getString("startDate"), format),
                            LocalDateTime.parse(resultSet.getString("endDate"), format));

                    allPromo.add(retrievedPromo);
                }
            }

        } catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);
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
        if (isPromoEmpty){
            logger.log(Level.INFO, "Promo database empty.");
            return null;
        } else {
            return allPromo;
        }
    }

    @Override //retrieve a promoCode from database using
    public String retrievePromoCode(Connection connection, String shortCode) {
        String promoCode = "";
        isPromoEmpty = false;
        sqlStatement = "SELECT promoCode FROM promo WHERE shortCode = \"" + shortCode + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            if(!resultSet.next()){
                isPromoEmpty = true;
            } else {
                promoCode = resultSet.getString("promoCode");
            }

        } catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);
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
        if (isPromoEmpty){
            logger.log(Level.INFO, "No promo with " + shortCode + " shotCode.");
            return null;
        } else {
            return promoCode;
        }
    }

    @Override
    public String retrieveShortCode(Connection connection, String promoCode) {
        String shortCode = "";
        isPromoEmpty = false;
        sqlStatement = "SELECT shortCode FROM promo WHERE promoCode = \"" + promoCode + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            if(!resultSet.next()){
                isPromoEmpty = true;
            } else {
                shortCode = resultSet.getString("shortCode");
            }

        } catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);
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
        if (isPromoEmpty){
            logger.log(Level.INFO, "No promo with " + promoCode + " promoCode.");
            return null;
        } else {
            return shortCode;
        }
    }
}
