package eu.luminis.processor;

import eu.luminis.model.MT940Record;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecordValidatorImpl implements RecordValidator {

    @Override
    public List<Map.Entry<String, List<MT940Record>>> findDuplicateTransactions(List<MT940Record> records) {
        // group records by transaction reference, filter out ones that only have 1 record and
        // return the list of items that are faulty
        return records.stream()
                .collect(Collectors.groupingBy(MT940Record::getTransactionReference))
                .entrySet()
                .stream().filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toList());
    }

    @Override
    public List<MT940Record> findIncorrectBalancedTransactions(List<MT940Record> records) {
        // get incorrect balances by comparing starting balance, mutation and the end balance
        return records.stream().filter(record ->
                        !record.getStartingBalance().add(record.getMutation()).equals(record.getEndBalance()))
                .collect(Collectors.toList());
    }
}
