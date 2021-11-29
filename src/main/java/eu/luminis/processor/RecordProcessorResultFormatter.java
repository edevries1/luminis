package eu.luminis.processor;

import eu.luminis.model.RecordProcessorResult;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Formats the RecordProcessorResult into some String
 */
public interface RecordProcessorResultFormatter {

    /**
     * Formats the result into some kind of String, which can be anything
     * @param results
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    String formatResults(RecordProcessorResult results) throws ParserConfigurationException, TransformerException;
}
