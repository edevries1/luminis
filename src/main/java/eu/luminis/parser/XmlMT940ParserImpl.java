package eu.luminis.parser;

import eu.luminis.model.MT940Record;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlMT940ParserImpl implements MT940Parser {
    @Override
    public List<MT940Record> parseRecords(InputStream inputStream) throws ParserException {
        var factory = DocumentBuilderFactory.newInstance();

        try {
            var result = new ArrayList<MT940Record>();
            var doc = factory.newDocumentBuilder().parse(inputStream);
            var records = doc.getDocumentElement().getChildNodes();

            // this assumes a certain document structure, so it will most likely fail
            // if the document is not exactly as expected
            for (var i = 0; i < records.getLength(); ++i) {
                var item = records.item(i);
                var ref = item.getAttributes().getNamedItem("reference").getNodeValue();
                var values = new HashMap<String, String>();

                // convert the list of xml childnodes into a map of nodename:textcontent
                // so we do not have to do a for/if loop
                for (var j = 0; j < item.getChildNodes().getLength(); ++j) {
                    var childItem = item.getChildNodes().item(j);
                    values.put(childItem.getNodeName(), childItem.getTextContent());
                }

                var record = new MT940Record();
                record.setTransactionReference(ref);
                record.setAccountNumber(values.get("accountNumber"));
                record.setStartingBalance(new BigDecimal(values.get("startBalance")));
                record.setMutation(new BigDecimal(values.get("mutation")));
                record.setDescription(values.get("description"));
                record.setEndBalance(new BigDecimal(values.get("endBalance")));
                result.add(record);
            }

            return result;
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new ParserException("unable to parse XML file", e);
        }
    }
}
