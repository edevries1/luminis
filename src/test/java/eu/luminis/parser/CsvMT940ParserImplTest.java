package eu.luminis.parser;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvMT940ParserImplTest {

    @Test
    void parseRecords() throws ParserException {
        var input = String.join("\n",
                "Reference,Account Number,Description,Start Balance,Mutation,End Balance",
                "112806,NL91RABO0315273637,Candy for Vincent de Vries,32.54,-2.21,30.33",
                "119619,NL93ABNA0585619023,Candy for Richard Bakker,10.9,+26.62,37.52"
        );

        var parser = new CsvMT940ParserImpl();
        var records = parser.parseRecords(new ByteArrayInputStream(input.getBytes()));

        assertEquals(2, records.size());

        var rec1 = records.get(0);

        assertEquals("112806", rec1.getTransactionReference());
        assertEquals("NL91RABO0315273637", rec1.getAccountNumber());
        assertEquals("Candy for Vincent de Vries", rec1.getDescription());
        assertEquals(new BigDecimal("32.54"), rec1.getStartingBalance());
        assertEquals(new BigDecimal("-2.21"), rec1.getMutation());
        assertEquals(new BigDecimal("30.33"), rec1.getEndBalance());
    }
}