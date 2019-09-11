package src.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class SystemState
{
	 private  long  frontTime = 0;//�ϴ�ɨ������
	 private  long  scanTime = 0;//�ϴ�ɨ����ʱ
	 private int scanDir =0;//ɨ���̷�
	 private int scanCount =0;//ɨ���ļ�����
	
	 private int isHid =0;//�Ƿ�����
	 private int isComm =0;//�Ƿ񳣹�
	 private static final String indexDir="d:\\indexDirectory" ;//������·��
     private static  File file  = new File("d:\\indexDirectory\\state.txt"); 
     private static  String readStr =""; 
    public SystemState()
    {	File dirFile=new  File(indexDir);
    	if(dirFile.exists()==false)
    	{
    			dirFile.mkdirs();
    	}
    	if(file.exists()==false)
    	{ 
    		try {
			file.createNewFile();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  System.err.println(file + "�Ѵ����� ");
		}
    	else {System.err.println(file + "�Ѵ����� "); }
    	
    	String  state=readTxtFile();
    	if(state!=null&&state!="")
    	{
    		int indFirst = state.indexOf("@");//@��һ�γ���λ��
        	int indLast1 = state.lastIndexOf("@");//@���һ�γ���λ��
        	
        	long timeOld = Long.parseLong(state.substring(0, indFirst));//����
        	setFrontTime(timeOld);//����
        	System.err.println(state+"mmm"); 
        	//System.err.println(timeOld+"mmm"); 
        	setScanTime(Long.parseLong(state.substring( indLast1+1,state.length())));//��ʱ
        	//System.err.println(state.substring( indLast1+1,state.length())+"rrr"); 
        	
        	String midString=state.substring(indFirst+1, indLast1);
        	System.err.println(midString+"iiii"); 
        	int indLast2=midString.lastIndexOf("@");
        	
        	setScanDir(Integer.parseInt(midString.substring(0, indLast2)));//�̷�
        //	System.err.println(midString.substring(0, indLast2)+"kkk");
        	setScanCount(Integer.parseInt(midString.substring(indLast2+1)));
        	//System.err.println(midString.substring(indLast2+1)+"ppp");
    	}
    
    }
    public static String readTxtFile(){ 
        String read; 
        FileReader fileread; 
        readStr ="";
        try { 
            fileread = new FileReader(file); 
            BufferedReader bufread = new BufferedReader(fileread); 
            try { 
                while ((read = bufread.readLine()) != null) { 
                    readStr = readStr + read; 
                } 
            } catch (IOException e) { 
                // TODO Auto-generated catch block 
                e.printStackTrace(); 
            } 
        } catch (FileNotFoundException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
        return readStr; 
    } 
     
    public static void writeTxtFile(String newStr) throws IOException{ 
        //�ȶ�ȡԭ���ļ����ݣ�Ȼ�����д����� 
        String filein = newStr  ; 
        RandomAccessFile mm = null; 
        try { 
            mm = new RandomAccessFile(file, "rw"); 
            mm.writeBytes(filein); 
        } catch (IOException e1) { 
            // TODO �Զ����  catch  �� 
            e1.printStackTrace(); 
        } finally { 
            if (mm != null) { 
                try { 
                    mm.close(); 
                } catch (IOException e2) { 
                    // TODO �Զ����  catch  �� 
                    e2.printStackTrace(); 
                } 
            } 
        } 
    } 
    
    public static void cleanText() throws IOException
    { 
    	FileOutputStream testfile = new FileOutputStream("d:\\indexDirectory\\state.txt");
    	testfile.write(new String("").getBytes());
    }
    
     public  long getFrontTime(){
         return frontTime;
     }
     
     public void setFrontTime(long frontTime ){
     	this.frontTime=frontTime;
      }
     
     public void setIndexDir(){
         // indexDir = "d:\\index";
      }
      public String getIndexDir(){
          return indexDir;
          }
          
     public void setScanDir(int scanDir){
    	  this.scanDir=scanDir;
        }
     public int getScanDir(){
          return scanDir;
      }
     public void setScanTime(long time){
   	  this.scanTime=time;
       }
    public long getScanTime(){
         return scanTime;
     }
    public void setScanCount(int count){
  	  this.scanCount=count;
      }
   public int getScanCount(){
        return scanCount;
    }
  public void setHid(int hid){
	  	  this.isHid=hid;
	      }
  public int getHid(){
	        return isHid;
	    }
	   
  public void setComm(int comm){
		  	  this.isComm=comm;
		      }
  public int getcomm(){
		        return isComm;
		    }

}
