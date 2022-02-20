package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

public interface ManagePromo {
    void insertPromo(Connection connection, Promo promo);
    ArrayList retrievePromo(Connection connection);
    String retrievePromoCodeByShortCode(Connection connection, String shortCode);
    String retrieveShortCodeByPromoCode(Connection connection, String promoCode);
    Map retrievePromoStartEndDates (Connection connection, String shortCode);
}
