/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yltaurb
 */
public class FileHandler {
    private FileHandler(){}
    public static void HandleCmd(String path){
        try {
            String cmdLine = "cmd /c start " + path;//����CMD��START������ļ�
            Runtime.getRuntime().exec(cmdLine);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void openFile(String filePath){
        String path = filePath;
        if (filePath.contains(" ")){
            String strname = "";
            String strp = path;
            String sp = "";
            do{
                path = strp;
                strname = path.substring(path.lastIndexOf("\\") + 1);
                if (strname.contains(" ")){
                    strname = "\"" + strname + "\"";
                }
                sp = strname + "\\" + sp;
                strp = path.substring(0, path.lastIndexOf("\\"));
            }while(strp.contains(" "));
            if (strp.contains("\\")){
                path = strp + "\\" + sp;
            }else{
                path = strp + sp;
            }
            path = path.substring(0, path.length() - 1);
        }
        System.out.println(path);
        HandleCmd(path);
    }
}
