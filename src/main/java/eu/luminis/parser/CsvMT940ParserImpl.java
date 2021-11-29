package eu.luminis.parser;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import eu.luminis.model.MT940Record;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvMT940ParserImpl implements MT940Parser {

    @Override
    public List<MT940Record> parseRecords(InputStream inputStream) throws ParserException {
        // the encoding is based on the provided file encoding, it should not really be hardcoded
        var reader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);

        try {
            var buffer = new CSVReaderHeaderAware(reader);
            var result = new ArrayList<MT940Record>();

            Map<String, String> row;

            while ((row = buffer.readMap()) != null) {
                var record = new MT940Record();
                record.setTransactionReference(row.get("Reference"));
                record.setAccountNumber(row.get("Account Number"));
                record.setStartingBalance(new BigDecimal(row.get("Start Balance")));
                record.setMutation(new BigDecimal(row.get("Mutation")));
                record.setDescription(row.get("Description"));
                record.setEndBalance(new BigDecimal(row.get("End Balance")));

                result.add(record);
            }

            return result;

        } catch (CsvValidationException | IOException e) {
            throw new ParserException("unable to parse CSV file", e);
        }
    }
}
