/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.search.IndexSearcher;

/**
 *
 * @author yltaurb
 */
public class DtsQuery {
    public IndexSearcher indexSearcher = null;
    public DtsQuery(String indexDir){
        try {
            indexSearcher = new IndexSearcher(indexDir);//���캯��
            System.out.println(indexDir);
        } catch (IOException ex) {
            Logger.getLogger(DtsQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void closeQuery(){
        try {
            indexSearcher.close();
        } catch (IOException ex) {
            Logger.getLogger(DtsQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
