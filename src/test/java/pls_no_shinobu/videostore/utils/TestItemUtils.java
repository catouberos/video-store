/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pls_no_shinobu.videostore.model.Item;

public class TestItemUtils {

    @Test
    @DisplayName("Parse a valid string")
    void parseValidString() {
        final String validString = "I001-2001, Medal of Honour, Game, 1-week,3,3.99";

        Item testItem = ItemUtils.parse(validString);

        assertEquals("I001-2001", testItem.getId());
        assertEquals("Medal of Honour", testItem.getTitle());
        assertEquals(Item.RentalType.GAME, testItem.getRentalType());
        assertEquals(Item.LoanType.ONE_WEEK, testItem.getLoanType());
        assertEquals(3, testItem.getStock());
        assertEquals(3.99f, testItem.getRentalFee());
    }

    @Test
    @DisplayName("Parse a valid string with genre")
    void parseValidStringWithGenre() {
        final String validStringWithGenre =
                "I001-2001,    Medal of Honour    , Game, 1-week, 3, 3.99, Action";

        Item testItem = ItemUtils.parse(validStringWithGenre);

        assertEquals("I001-2001", testItem.getId());
        assertEquals("Medal of Honour", testItem.getTitle());
        assertEquals(Item.RentalType.GAME, testItem.getRentalType());
        assertEquals(Item.LoanType.ONE_WEEK, testItem.getLoanType());
        assertEquals(3, testItem.getStock());
        assertEquals(3.99f, testItem.getRentalFee());
        assertEquals("Action", testItem.getGenre());
    }

    @Test
    @DisplayName("Parse RentalType enum")
    void parseEnumRentalType() {
        final String enumRentalTypeRecord =
                "I001-2001,    Medal of Honour    , reCorD, 1-week, 3, 3.99, Action";
        final String enumRentalTypeDVD =
                "I001-2001,    Medal of Honour    , DVD, 1-week, 3, 3.99, Action";
        final String enumRentalTypeGame =
                "I001-2001,    Medal of Honour    , game, 1-week, 3, 3.99, Action";

        Item testRecord = ItemUtils.parse(enumRentalTypeRecord);
        Item testDVD = ItemUtils.parse(enumRentalTypeDVD);
        Item testGame = ItemUtils.parse(enumRentalTypeGame);

        assertEquals(Item.RentalType.RECORD, testRecord.getRentalType());
        assertEquals(Item.RentalType.DVD, testDVD.getRentalType());
        assertEquals(Item.RentalType.GAME, testGame.getRentalType());
    }

    @Test
    @DisplayName("Parse LoanType enum")
    void parseEnumLoanType() {
        final String enumLoanTypeOneWeek =
                "I001-2001,    Medal of Honour    , Record, 1-WEEK, 3, 3.99, Action";
        final String enumLoanTypeTwoDay =
                "I001-2001,    Medal of Honour    , Record, 2-DaY, 3, 3.99, Action";

        Item testOneWeek = ItemUtils.parse(enumLoanTypeOneWeek);
        Item testTwoDay = ItemUtils.parse(enumLoanTypeTwoDay);

        assertEquals(Item.LoanType.ONE_WEEK, testOneWeek.getLoanType());
        assertEquals(Item.LoanType.TWO_DAY, testTwoDay.getLoanType());
    }

    @Test
    @DisplayName("Parse an invalid string (ID)")
    void parseInvalidIdString() {
        final String invalidIdString = "whatisthisID,Medal of Honour,Game,1-week,3,3.99";

        Item testItem = ItemUtils.parse(invalidIdString);

        assertNull(testItem);
    }

    @Test
    @DisplayName("Parse an invalid string (LoanType)")
    void parseInvalidLoanTypeString() {
        final String invalidLoanTypeString = "whatisthisID,Medal of Honour,Game,100-week,3,3.99";

        Item testItem = ItemUtils.parse(invalidLoanTypeString);

        assertNull(testItem);
    }

    @Test
    @DisplayName("Parse an invalid string (RentalType)")
    void parseInvalidRentalTypeString() {
        final String invalidRentalTypeString = "whatisthisID,Medal of Honour,Gamers,1-week,3,3.99";

        Item testItem = ItemUtils.parse(invalidRentalTypeString);

        assertNull(testItem);
    }

    @Test
    @DisplayName("Parse an invalid string (negative stock)")
    void parseInvalidNegativeStockString() {
        final String invalidNegativeStockString =
                "whatisthisID,Medal of Honour,Game,1-week,-1,3.99";

        Item testItem = ItemUtils.parse(invalidNegativeStockString);

        assertNull(testItem);
    }

    @Test
    @DisplayName("Serialize an item")
    void serializeItem() {
        Item item =
                new Item(
                        "I001-2001",
                        "Medal of Honour",
                        null,
                        Item.RentalType.GAME,
                        Item.LoanType.ONE_WEEK,
                        3,
                        3.99f);

        String serializedString = ItemUtils.serialize(item);

        String expectedString = "I001-2001,Medal of Honour,GAME,1-week,3,3.99";

        assertTrue(expectedString.equals(serializedString));
    }
}
