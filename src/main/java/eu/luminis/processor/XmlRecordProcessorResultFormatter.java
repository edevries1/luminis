package eu.luminis.processor;

import eu.luminis.model.MT940Record;
import eu.luminis.model.RecordProcessorResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Formats the RecordProcessorResult as an XML document
 *
 * Example output:
 * <?xml version="1.0" encoding="UTF-8"?>
 * <results>
 *    <incorrectBalance>
 *       <transaction reference="112414">
 *          <description>Candy for Rik Bakker</description>
 *       </transaction>
 *       <transaction reference="131317">
 *          <description>Tickets for Vincent Theuß</description>
 *       </transaction>
 *    </incorrectBalance>
 *    <duplicates />
 * </results>
 */
public class XmlRecordProcessorResultFormatter implements RecordProcessorResultFormatter {

    @Override
    public String formatResults(RecordProcessorResult results) throws ParserConfigurationException, TransformerException {
        var factory = DocumentBuilderFactory.newInstance();
        var document = factory.newDocumentBuilder().newDocument();

        var root = document.createElement("results");
        var duplicates = document.createElement("duplicates");
        var incorrectBalance = document.createElement("incorrectBalance");

        for (var record : results.getIncorrectBalancedRecords()) {
            incorrectBalance.appendChild(createRecordElement(document, record));
        }

        for (var recordset : results.getDuplicateRecords()) {
            // it will create 1 <transactionGroup> element per duplicate transaction reference
            // inside the element are all the individual transactions
            var container = document.createElement("transactionGroup");
            container.setAttribute("reference", recordset.getKey());

            for (var record : recordset.getValue()) {
                container.appendChild(createRecordElement(document, record));
            }

            duplicates.appendChild(container);
        }

        root.appendChild(incorrectBalance);
        root.appendChild(duplicates);

        document.appendChild(root);
        return xmlToString(document);
    }

    private Element createRecordElement(Document document, MT940Record record) {
        var element = document.createElement("transaction");
        element.setAttribute("reference", record.getTransactionReference());

        var description = document.createElement("description");
        description.setTextContent(record.getDescription());
        element.appendChild(description);

        return element;
    }

    private String xmlToString(Document document) throws TransformerException {
        var transformer = TransformerFactory.newInstance().newTransformer();
        var writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));

        return writer.getBuffer().toString();
    }
}
