/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

/**
 *
 * @author yltaurb
 */
public class HardwareScanner {
    private int cnt = 0;
    private FileReaderFactory faf = null;
    private IndexWriter writer = null;
    private static final  String indexDir = "d:\\indexDirectory";
    private Vector<String> ve;
    boolean commonIndex;
    public HardwareScanner(){
        init();
    }
    
    private void init(){
        faf = new FileReaderFactory();
        setIndexDir();
        commonIndex = false;
        ve = new Vector<String>();
        String[] fileKind = new String[]{"txt", "c","h","cpp","java","doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "rtf"
        , "rmvb", "rm","avi", "mkv", "flv", "wma", "mp3", "wav", "jpg", "jpeg", "bmp", "gif", "exe"};
        ve.addAll(Arrays.asList(fileKind));//������������
    }
    
    private void setIndexDir(){
       // indexDir = "d:\\index";
    }
    public String getIndexDir(){
        return indexDir;
    }
    
    private boolean isIndexed(String fileName){
        if (!fileName.contains(".")){
            return false;
        }
        String str = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (ve.contains(str)){
            return true;
        }
        return false;
    }
    
    public void createIndex(File file){
        try {
            Document doc = new Document();
            String fileName = file.getName();
            int ind = fileName.lastIndexOf(".");
            
            if (commonIndex && !isIndexed(fileName)){
                return;
            }
            System.out.println("Indexing " + file.getCanonicalPath());
            cnt++;
            /***************************/
            DtsFileReader dtsFileReader = faf.createFileReader(file.getName());
            
            String path = file.getCanonicalPath();
            doc.add(new Field("filePath", path,Field.Store.YES,Field.Index.UN_TOKENIZED));
            doc.add(new Field("fileDisk", path.substring(0, 1),Field.Store.YES,Field.Index.UN_TOKENIZED));
            doc.add(new Field("fatherPath", path.substring(0, path.lastIndexOf("\\")),Field.Store.YES,Field.Index.UN_TOKENIZED));
            if (fileName.contains(".")){
                doc.add(new Field("fileName", fileName.substring(0, ind),Field.Store.YES,Field.Index.ANALYZED));
                doc.add(new Field("fileType", fileName.substring(ind + 1).toLowerCase(),Field.Store.YES,Field.Index.UN_TOKENIZED));
            }else{
                doc.add(new Field("fileName", fileName,Field.Store.YES,Field.Index.ANALYZED));
            }
         
            /****************************/
            if (dtsFileReader != null) {
                String fileText = dtsFileReader.getTxt(file);
                if (fileText.length() != 0){
                    doc.add(new Field("contents", fileText,Field.Store.YES,Field.Index.ANALYZED));
                }
            }
            /****************************/
            writer.addDocument(doc);
        } catch (Exception ex) {
            Logger.getLogger(HardwareScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void scan(int cho, boolean isHid, boolean isCommon){
        commonIndex = isCommon;
        cnt = 0;
        long startTime = new Date().getTime();
        try {
            writer = new IndexWriter(indexDir, new StandardAnalyzer(), true);
        } catch (IOException ex) {
            Logger.getLogger(HardwareScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        File files[] = File.listRoots();
        switch(cho){
            case 0:
                for (File file : files){
                    this.getDir(file.toString(), isHid);
                }
                break;
            default:
                this.getDir(files[cho - 1].toString(), isHid);
                break;
        }
        try {
            writer.optimize();
        } catch (IOException ex) {
            Logger.getLogger(HardwareScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(HardwareScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        long endTime = new Date().getTime();
        String title = "Message Dialog";
        String message = "������� !\nɨ�赽" + cnt + "���ļ�\n�ܺ�ʱ" + (endTime - startTime) / 1000.0 + "��";	
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void getDir(String path, boolean isHid){
	File files[] = (new File(path)).listFiles();
	if (files == null){
            return;
	}
	for (File file : files){
            if (!isHid && file.isHidden()){
                System.out.println("����");
                continue;
            }
            if (!file.isDirectory()){
                try {
                    System.out.println(file.getCanonicalPath());
                    createIndex(file);
                } catch (Exception ex) {
                    Logger.getLogger(HardwareScanner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                getDir(file.toString(), isHid);
            }
        }
    }
}
