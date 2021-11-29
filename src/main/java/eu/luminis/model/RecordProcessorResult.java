package eu.luminis.model;

import java.util.List;
import java.util.Map;

public class RecordProcessorResult {
    private List<MT940Record> records;
    private List<MT940Record> incorrectBalancedRecords;
    private List<Map.Entry<String, List<MT940Record>>> duplicateRecords;

    public List<MT940Record> getRecords() {
        return records;
    }

    public void setRecords(List<MT940Record> records) {
        this.records = records;
    }

    public List<MT940Record> getIncorrectBalancedRecords() {
        return incorrectBalancedRecords;
    }

    public void setIncorrectBalancedRecords(List<MT940Record> incorrectBalancedRecords) {
        this.incorrectBalancedRecords = incorrectBalancedRecords;
    }

    public List<Map.Entry<String, List<MT940Record>>> getDuplicateRecords() {
        return duplicateRecords;
    }

    public void setDuplicateRecords(List<Map.Entry<String, List<MT940Record>>> duplicateRecords) {
        this.duplicateRecords = duplicateRecords;
    }
}
