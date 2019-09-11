/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;

import java.io.File;

/**
 *
 * @author yltaurb
 */
public class FileReaderFactory {
    public DtsFileReader createFileReader(String fileName){
        DtsFileReader fileReader = null;
        //
        if (fileName.charAt(0) == '~'){
            return null;
        }
        try{
            String p[] = fileName.split("\\.");
            int pLen = p.length;
            if (pLen == 1){
                return null;
            }else if ("txt".equals(p[pLen - 1])
                    || "cpp".equals(p[pLen - 1])
                    || "java".equals(p[pLen - 1])
                    || "h".equals(p[pLen - 1])){
                fileReader = new TxtReader();
            }else if ("pdf".equals(p[pLen - 1])){
                fileReader = new PdfReader();
            }else if ("doc".equals(p[pLen - 1])
                    || "docx".equals(p[pLen - 1])
                    || "xls".equals(p[pLen - 1])
                    || "xlsx".equals(p[pLen - 1]) 
                    || "ppt".equals(p[pLen - 1]) 
                    || "pptx".equals(p[pLen - 1])){
                fileReader = new OfficeReader();
            }else if ("rtf".equals(p[pLen - 1])){
                fileReader = new RtfReader();
            }else if("htm".equals(p[pLen - 1])){
            	 fileReader = new htmlReader();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return fileReader;
    }
    /***************************************
    public static void main(String args[]) throws Exception{
        FileReaderFactory faf = new FileReaderFactory();
        //File file = new File("F:\\indexSource\\Readme.htm");
        File file = new File("C:\\Documents and Settings\\Administrator\\����\\HardwareScanner.java");
        DtsFileReader x = faf.createFileReader(file.getName());
        System.out.println(x.getTxt(file));
    }
    /*******************************************/
}
