package eu.luminis.parser;

import eu.luminis.model.MT940Record;

import java.io.InputStream;
import java.util.List;

public interface MT940Parser {

    /**
     * Converts some input into a list of MT940Record objects
     * @param inputStream
     * @return
     * @throws ParserException
     */
    List<MT940Record> parseRecords(InputStream inputStream) throws ParserException;
}
