package eu.luminis.processor;

import eu.luminis.model.MT940Record;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordValidatorImplTest {

    @Test
    void findDuplicateTransactions() {
        var records = new ArrayList<MT940Record>();
        records.add(new MT940Record(
                "ref1",
                "acc1",
                new BigDecimal("10"),
                new BigDecimal("10"),
                "",
                new BigDecimal("10")
        ));

        records.add(new MT940Record(
                "ref1",
                "acc2",
                new BigDecimal("10"),
                new BigDecimal("10"),
                "",
                new BigDecimal("10")
        ));

        records.add(new MT940Record(
                "ref2",
                "acc3",
                new BigDecimal("10"),
                new BigDecimal("10"),
                "",
                new BigDecimal("10")
        ));
        var validator = new RecordValidatorImpl();
        var result = validator.findDuplicateTransactions(records);

        assertEquals(1, result.size(), "More than 1 entry found, expected 1");
        assertEquals("ref1", result.get(0).getKey(), "Key should be ref1");
        assertEquals("acc1", result.get(0).getValue().get(0).getAccountNumber(),
                "Invalid transaction reference");
        assertEquals("acc2", result.get(0).getValue().get(1).getAccountNumber(),
                "Invalid transaction reference");
    }

    @Test
    void testNoDuplicateTransactions() {
        var records = new ArrayList<MT940Record>();
        records.add(new MT940Record(
                "ref1",
                "acc1",
                new BigDecimal("10"),
                new BigDecimal("10"),
                "",
                new BigDecimal("10")
        ));

        records.add(new MT940Record(
                "ref2",
                "acc2",
                new BigDecimal("10"),
                new BigDecimal("10"),
                "",
                new BigDecimal("10")
        ));

        records.add(new MT940Record(
                "ref3",
                "acc3",
                new BigDecimal("10"),
                new BigDecimal("10"),
                "",
                new BigDecimal("10")
        ));
        var validator = new RecordValidatorImpl();
        var result = validator.findDuplicateTransactions(records);

        assertEquals(0, result.size(), "Should have no duplicates");
    }

    @Test
    void findIncorrectBalancedTransactions() {
        var records = new ArrayList<MT940Record>();
        records.add(new MT940Record(
                "ref1",
                "acc1",
                new BigDecimal("10"),
                new BigDecimal("+10"),
                "",
                new BigDecimal("20")
        ));

        records.add(new MT940Record(
                "ref2",
                "acc2",
                new BigDecimal("10"),
                new BigDecimal("-5"),
                "",
                new BigDecimal("6")
        ));

        records.add(new MT940Record(
                "ref3",
                "acc3",
                new BigDecimal("10"),
                new BigDecimal("99999"),
                "",
                new BigDecimal("4")
        ));

        records.add(new MT940Record(
                "ref4",
                "acc4",
                new BigDecimal("-10"),
                new BigDecimal("-20"),
                "",
                new BigDecimal("-30")
        ));

        var validator = new RecordValidatorImpl();
        var result = validator.findIncorrectBalancedTransactions(records);

        assertEquals(2, result.size());
        assertEquals("ref2", result.get(0).getTransactionReference());
        assertEquals("ref3", result.get(1).getTransactionReference());
    }
}