package eu.luminis.processor;

import eu.luminis.model.MT940Record;

import java.util.List;
import java.util.Map;


public interface RecordValidator {

    /**
     * Finds and returns duplicates in a list of records, based on transaction reference
     * @param records
     * @return
     */
    List<Map.Entry<String, List<MT940Record>>> findDuplicateTransactions(List<MT940Record> records);

    /**
     * Finds and returns all records where the start balance plus mutation is not equal to the end balance
     * @param records
     * @return
     */
    List<MT940Record> findIncorrectBalancedTransactions(List<MT940Record> records);
}
