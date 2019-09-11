/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;

import java.io.File;
import java.util.Scanner;

public class TxtReader extends DtsFileReaderImpl{
    @Override
    public String getTxt(File file) throws Exception{
        Scanner scanner = new Scanner(file,"GBK");
	scanner.useDelimiter("\\z");
	StringBuilder buffer = new StringBuilder();
	while (scanner.hasNext()){
            buffer.append(scanner.next());
        }
        scanner.close();
        return buffer.toString();
    }
}
