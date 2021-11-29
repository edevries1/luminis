package eu.luminis.parser;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlMT940ParserImplTest {

    @Test
    void parseRecords() throws ParserException {
        var input = "<?xml version=\"1.0\" ?>" +
                "<records>" +
                "<record reference=\"178668\">" +
                "<accountNumber>NL69ABNA0433647324</accountNumber>" +
                "<description>Tickets from Willem Bakker</description>" +
                "<startBalance>92.63</startBalance>" +
                "<mutation>-9.92</mutation>" +
                "<endBalance>82.71</endBalance>" +
                "</record>" +
                "<record reference=\"112414\">" +
                "<accountNumber>NL27SNSB0917829871</accountNumber>" +
                "<description>Candy for Rik Bakker</description>" +
                "<startBalance>5429</startBalance>" +
                "<mutation>-939</mutation>" +
                "<endBalance>6368</endBalance>" +
                "</record>" +
                "</records>";

        var records = new XmlMT940ParserImpl().parseRecords(new ByteArrayInputStream(input.getBytes()));

        assertEquals(2, records.size());

        var rec1 = records.get(0);
        assertEquals("178668", rec1.getTransactionReference());
        assertEquals("NL69ABNA0433647324", rec1.getAccountNumber());
        assertEquals("Tickets from Willem Bakker", rec1.getDescription());
        assertEquals(new BigDecimal("92.63"), rec1.getStartingBalance());
        assertEquals(new BigDecimal("-9.92"), rec1.getMutation());
        assertEquals(new BigDecimal("82.71"), rec1.getEndBalance());
    }
}