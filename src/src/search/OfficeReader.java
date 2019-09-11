/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;

/**
 *
 * @author yltaurb
 */
public class OfficeReader extends DtsFileReaderImpl{
    @Override
    public String getTxt(File file) throws Exception{
        String fileText = "";
	try{
            POITextExtractor extractor = ExtractorFactory.createExtractor(file);
            if (extractor == null)
                return "";
            fileText = extractor.getText();
        }catch(FileNotFoundException e){
            System.out.println("Indexing " + file.getCanonicalPath() + " error!");
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("Indexing " + file.getCanonicalPath() + " error!");
            e.printStackTrace();
        }
        return fileText;
    }
}
