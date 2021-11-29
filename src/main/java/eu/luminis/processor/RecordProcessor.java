package eu.luminis.processor;

import eu.luminis.model.RecordProcessorResult;
import eu.luminis.parser.ParserException;

import java.io.InputStream;

public interface RecordProcessor {

    /**
     * Processes all records and returns an object that contains
     * the records, the duplicates and the incorrectly balanced
     * @param fileName
     * @param inputStream
     * @return
     * @throws ParserException
     */
    RecordProcessorResult process(String fileName, InputStream inputStream) throws ParserException;
}
