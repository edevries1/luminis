package eu.luminis.processor;

import eu.luminis.model.RecordProcessorResult;
import eu.luminis.parser.CsvMT940ParserImpl;
import eu.luminis.parser.MT940Parser;
import eu.luminis.parser.ParserException;
import eu.luminis.parser.XmlMT940ParserImpl;

import java.io.File;
import java.io.InputStream;

public class RecordProcessorImpl implements RecordProcessor {
    private static String getFileExtension(String filename) {
        var actualName = new File(filename).getName();
        var parts = actualName.split("\\.");

        if (parts.length > 1) {
            return parts[parts.length - 1];
        }

        return "";
    }

    @Override
    public RecordProcessorResult process(String fileName, InputStream inputStream) throws ParserException {
        var fileExtension = getFileExtension(fileName);

        MT940Parser parser;

        switch (fileExtension) {
            case "xml":
                parser = new XmlMT940ParserImpl();
                break;

            case "csv":
                parser = new CsvMT940ParserImpl();
                break;

            default:
                throw new IllegalStateException("Unknown file extension: '" + fileExtension + "'");
        }

        // get records from parser
        var records = parser.parseRecords(inputStream);
        var validator = new RecordValidatorImpl();

        var incorrectBalance = validator.findIncorrectBalancedTransactions(records);
        var duplicateRecords = validator.findDuplicateTransactions(records);

        var result = new RecordProcessorResult();
        result.setRecords(records);
        result.setIncorrectBalancedRecords(incorrectBalance);
        result.setDuplicateRecords(duplicateRecords);
        return result;
    }
}
