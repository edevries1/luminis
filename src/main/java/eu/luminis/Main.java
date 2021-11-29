package eu.luminis;

import eu.luminis.model.RecordProcessorResult;
import eu.luminis.parser.ParserException;
import eu.luminis.processor.RecordProcessorImpl;
import eu.luminis.processor.XmlRecordProcessorResultFormatter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    // for testing purposes
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Filename not provided");
            System.exit(1);
            return;
        }

        var fileName = args[0];
        var processor = new RecordProcessorImpl();
        var formatter = new XmlRecordProcessorResultFormatter();

        try (var input = new FileInputStream(fileName)) {
            RecordProcessorResult result = processor.process(fileName, input);
            var output = formatter.formatResults(result);
            System.out.println(output);
        } catch (ParserException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
