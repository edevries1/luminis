package eu.luminis;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import eu.luminis.parser.ParserException;
import eu.luminis.processor.RecordProcessorImpl;
import eu.luminis.processor.XmlRecordProcessorResultFormatter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Handler implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(S3Event event, Context context) {
        var client = AmazonS3ClientBuilder.defaultClient();
        var processor = new RecordProcessorImpl();
        var formatter = new XmlRecordProcessorResultFormatter();

        for (var record : event.getRecords()) {
            // fileName is including folder, for example incoming/filename.csv
            var fileName = record.getS3().getObject().getUrlDecodedKey();
            var bucket = record.getS3().getBucket().getName();

            try {
                var object = client.getObject(bucket, fileName);
                String output;

                try (var inputStream = object.getObjectContent()) {
                    var result = processor.process(fileName, inputStream);
                    output = formatter.formatResults(result);
                }

                // change the incoming/ part to outbound/ and always make it an XML file
                var newFileName = transformFilename(fileName);

                client.putObject(bucket, newFileName, output);
            } catch (IOException | ParserConfigurationException | TransformerException | ParserException e) {
                e.printStackTrace();
            }
        }

        return "OK";
    }

    private String transformFilename(String name) {
        name = name.replaceFirst("incoming/", "outbound/");
        name = name.replaceAll("\\.", "_");

        return name + ".xml";
    }
}
