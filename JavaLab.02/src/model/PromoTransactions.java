package model;

import utility.SingletonDBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PromoTransactions implements ManagePromo {
    final private static Logger logger = Logger.getLogger(PromoTransactions.class.getName());
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Connection connection = SingletonDBConnection.getConnection();

    //collection of all the retrieved Promos
    protected ArrayList<Promo> allPromo = new ArrayList<>();

    String sqlStatement = "";
    Statement statement = null;
    ResultSet resultSet = null;

    boolean isEmpty;

    Promo retrievedPromo;

    @Override //insert a promo into database
    public  void insertPromo(Promo promo) {
        boolean promoExist = false;

        //retrieve all promo in the database
        retrievePromo();

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
                logger.log(Level.INFO, "Promo [" + promo.getPromoCode() + "] added.");

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
    public ArrayList retrievePromo() {

        //empty allPromo ArrayList
        allPromo.clear();

        //retrieve all promo
        sqlStatement = "SELECT * FROM promo";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                retrievedPromo = new Promo(resultSet.getString("promoCode"),
                        resultSet.getString("details"),
                        resultSet.getString("shortCode"),
                        LocalDateTime.parse(resultSet.getString("startDate"), format),
                        LocalDateTime.parse(resultSet.getString("endDate"), format));

                allPromo.add(retrievedPromo);
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
        if (allPromo.isEmpty()){
            logger.log(Level.INFO, "Promo database empty.");
        }
        return allPromo;
    }

    @Override //retrieve a promoCode from database using
    public String retrievePromoCodeByShortCode(String shortCode) {
        String promoCode = "";
        isEmpty = true;

        sqlStatement = "SELECT promoCode FROM promo WHERE shortCode = \"" + shortCode + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isEmpty = false;
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
        if (isEmpty){
            logger.log(Level.INFO, "No promo with " + shortCode + " shortCode.");
            return null;
        }
        return promoCode;
    }

    @Override
    public String retrieveShortCodeByPromoCode(String promoCode) {
        String shortCode = "";
        isEmpty = true;

        sqlStatement = "SELECT shortCode FROM promo WHERE promoCode = \"" + promoCode + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isEmpty = false;
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
        if (isEmpty){
            logger.log(Level.INFO, "No promo with " + promoCode + " promoCode.");
            return null;
        }
        return shortCode;
    }

    @Override
    public Map retrievePromoStartEndDates(String shortCode) {
        Map dates = new HashMap();

        //retrieve all promo
        sqlStatement = "SELECT startDate, endDate FROM promo WHERE shortCode = \"" + shortCode +"\"";


        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                dates.put("startDate", resultSet.getString("startDate"));
                dates.put("endDate", resultSet.getString("endDate"));
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
        return dates;
    }
}
