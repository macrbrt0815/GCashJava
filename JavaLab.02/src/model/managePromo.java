package model;

import java.sql.Connection;
import java.util.ArrayList;

public interface managePromo {
    void insertPromo(Connection connection, Promo promo);
    ArrayList retrievePromo(Connection connection);
    String retrievePromoCode(Connection connection, String shortCode);
    String retrieveShortCode(Connection connection, String promoCode);
}
