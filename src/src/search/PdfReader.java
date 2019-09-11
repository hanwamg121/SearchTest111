/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;

import java.io.File;
import java.io.FileInputStream;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

/**
 *
 * @author yltaurb
 */
															//文档解析
public class PdfReader extends DtsFileReaderImpl{
    @Override
    public String getTxt(File file) throws Exception{
        String fileText = "";
        FileInputStream instream = null;
        PDDocument pdfdocument = null;
        try{
            instream = new FileInputStream(file);
            PDFParser parser = new PDFParser(instream);
            parser.parse();
            pdfdocument = parser.getPDDocument();
            PDFTextStripper pdfstripper = new PDFTextStripper();
            fileText = pdfstripper.getText(pdfdocument);
        }catch(Exception e){
            System.out.println("Indexing " + file.getCanonicalPath() + " error!");  
            e.printStackTrace();
        }
        finally {
            try {
                if (instream != null) {
                    instream.close();
                }
                if (pdfdocument != null) {
                    pdfdocument.close();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
	return fileText;
    }
}
