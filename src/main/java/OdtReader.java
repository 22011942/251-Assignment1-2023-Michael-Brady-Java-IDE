import com.aspose.words.*;

public class OdtReader extends OpenFile {
    String parseText = "";
    protected OdtReader(String filename) {
        try {
            //Loads the ODT file
            Document odt = new Document(filename);
            // Gets the collection of paragraphs, separated by line breaks
            NodeCollection paragraphs = odt.getChildNodes(NodeType.PARAGRAPH, true);
            // Loops through the paragraphs and adds them together in a single string
            for (Paragraph paragraph : (Iterable<Paragraph>) paragraphs) {
                parseText += paragraph.getText() + "\n";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getText() {
        return parseText;
    }
}
