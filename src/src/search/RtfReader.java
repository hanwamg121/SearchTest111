/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

/**
 *
 * @author yltaurb
 */
public class RtfReader extends DtsFileReaderImpl{
    @Override
    public String getTxt(File file) throws IOException{
        FileInputStream fis = null;
        String fileText = "";
        try {
            fis = new FileInputStream(file);
            DefaultStyledDocument styledDoc = new DefaultStyledDocument();
            try {
                new RTFEditorKit().read(fis, styledDoc, 0);
                fileText = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("ISO8859_1"),"GB2312");
            } catch (IOException ex) {
                System.out.println("Indexing " + file.getCanonicalPath() + " error!");
                Logger.getLogger(RtfReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException ex) {
                System.out.println("Indexing " + file.getCanonicalPath() + " error!");
                Logger.getLogger(RtfReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Indexing " + file.getCanonicalPath() + " error!");
            Logger.getLogger(RtfReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                System.out.println("Indexing " + file.getCanonicalPath() + " error!");
                Logger.getLogger(RtfReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fileText;
    }
}
