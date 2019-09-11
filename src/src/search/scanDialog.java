package src.search;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

public class scanDialog implements ActionListener{
//public class scanDialog extends JInternalFrame implements ActionListener{

	
	private  JComboBox jComboBox;
	private  JDialog jDialog1=null; //����һ���յĶԻ������
	private  JInternalFrame internalFrame ;
	private  JButton scanJButton,finishButton,exitButton;
	private JCheckBox jCheckBox1,jCheckBox2;
	private JLabel jLabel1,jLabel2;
	private JTextArea jTextArea;
	private  SystemState state=new SystemState();//���ϵͳ״̬
	
	    scanDialog(JFrame jFrame){
	    //	scanDialog(){
	        	
	       /* ��ʼ��jDialog1* ָ���Ի����ӵ����ΪjFrame,����Ϊ"Dialog",���Ի���Ϊ����ʱ,���������* �����û�������(��̬�Ի���) */
	    
	            jDialog1=new JDialog(jFrame,"����������������Ҫ���Ѽ����ӣ��Եȣ�",true);
	    	/***********************
	            internalFrame   = new JInternalFrame(  
	                    "����Ի��� ", true, true, true, true);    
	            /********************/
	         //   jDialog1.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		    	  jComboBox=new JComboBox();
		    	//  jComboBox. setEditable(true);
		            File []files = File.listRoots();
		            String[] diskList = new String[files.length + 1];
		            diskList[0] = "ȫ��";
		            for (int i = 0;i < files.length;i++){
		                try {
		                    diskList[i + 1] = files[i].getCanonicalPath();
		                } catch (IOException ex) { }
		            }
		            
		        jComboBox.setModel(new javax.swing.DefaultComboBoxModel(diskList));
		       // jComboBox.setEditable(true);
		        scanJButton=new JButton("����");
		      //  exitButton=new JButton("��̨");
		        //exitButton.setEnabled(false);
		        finishButton=new JButton("���");
		        finishButton.setEnabled(false);
		        jCheckBox1=new JCheckBox();
                jCheckBox1.setText("ɨ�������ļ�");
                jCheckBox2=new JCheckBox();
                jCheckBox2.setText("ֻɨ�賣���ļ�");
                jCheckBox2.setSelected(true);
	            jLabel1=new JLabel("  ɨ�跶Χ��"); 
	            jLabel2=new JLabel("");

	            
	            JScrollPane jScrollPane = new JScrollPane();
	            jTextArea=new JTextArea(12,52);
	            jTextArea.setEditable(false);
	            jScrollPane.setViewportView(jTextArea);
	            //jScrollPane.

	            JPanel panel=new JPanel();
	            
	            Box labelBox=Box.createHorizontalBox();	
	            labelBox.add(Box.createVerticalStrut(8));
	            
	            Box chekBox1=Box.createHorizontalBox();
	            chekBox1.add(jCheckBox1);
	            chekBox1.add(Box.createHorizontalStrut(8));
	            chekBox1.add(jCheckBox2);
	            chekBox1.add(Box.createHorizontalStrut(8));
	            
	            Box chekBox2=Box.createHorizontalBox();
	          //  chekBox2.add(jCheckBox1);
	            chekBox2.add(jLabel1);
	            chekBox2.add(Box.createHorizontalStrut(8));
	            chekBox2.add(jComboBox);
	            chekBox2.add(Box.createHorizontalStrut(8));
	            chekBox2.add(scanJButton);
	            chekBox2.add(Box.createHorizontalStrut(8));
	            chekBox2.add( finishButton);
	            chekBox2.add(Box.createHorizontalStrut(8));
	           // chekBox2.add( exitButton);
	            
	            Box totalkBox=Box.createVerticalBox();
	            totalkBox.add(Box.createVerticalStrut(8));
	            totalkBox.add(chekBox1);
	            totalkBox.add(chekBox2);
	            totalkBox.add(Box.createVerticalStrut(12));
	            totalkBox.add(jScrollPane);
	            //totalkBox.add(jTextArea);
	            
	            panel.add( totalkBox);
	            /*****************************
	            Container icontentPane = internalFrame.getContentPane();  
	            icontentPane.add( panel);
	            internalFrame.add(panel);
	          
	            
	            scanJButton.addActionListener(this);
	            finishButton.addActionListener(this);
	          
	            internalFrame.setSize(600,350);  // ���öԻ���ĳ�ʼ��С 
	            internalFrame.setLocation(500,200); // ���öԻ����ʼ��ʾ����Ļ���е�λ�� 
	            internalFrame.setVisible(true);  //���öԻ���Ϊ�ɼ�(ǰ���������HelloDialog����) 
	            /*************************************/
	            jDialog1.getContentPane().add( panel);
	            
	            scanJButton.addActionListener(this);
	            finishButton.addActionListener(this);
	          //  exitButton.addActionListener(this);
	            
	            jDialog1.setSize(600,350);  // ���öԻ���ĳ�ʼ��С 
	            jDialog1.setLocation(500,200); // ���öԻ����ʼ��ʾ����Ļ���е�λ�� 
	            jDialog1.setVisible(true);  //���öԻ���Ϊ�ɼ�(ǰ���������HelloDialog����) 
	            
	            /****************************************/

	        }

	        //��Ӧ�Ի����еİ�ť�¼�

	       public void actionPerformed(ActionEvent e){
	    	   HardwareScanner hardwareScanner=  new HardwareScanner();
	            if(e.getActionCommand().equals("����")){//����索引
	               int ind = jComboBox.getSelectedIndex();
	            //   File files[] = File.listRoots();
	             //  System.out.println(ind);
	              // if(ind==0){ state.setScanDir("ȫ��");}//�õ�ɨ���̷�}
	              // if(ind!=0){state.setScanDir(files[ind - 1].toString());}
	               state.setScanDir(ind);
	               System.out.println(state.getScanDir());
	      	     	final boolean isHid;
	      	     	final boolean isComm;
		
	                if (jCheckBox1.isSelected()){
	                    isHid = true;
	                }else{
	                    isHid = false;
	                }
	                if (jCheckBox2.isSelected()){
	                    isComm = true;
	                }else{
	                    isComm = false;
	                }
	           
	                jTextArea.removeAll();
	                hardwareScanner.setHid(isHid);
	                hardwareScanner.setRange(ind);
	                hardwareScanner.setComm(isComm);
	                hardwareScanner.start();
	                
	               // finishButton.setText("ֹͣ");
	              // finishButton.setEnabled(true);
	                scanJButton.setEnabled(false);
	              //  exitButton.setEnabled(true);
	 
	            }

	            if(e.getActionCommand().equals("���")){jDialog1.dispose();}//���完成
	            if(e.getActionCommand().equals("��̨")){jDialog1.dispose();}
	            /*******************
	            if(e.getActionCommand().equals("ֹͣ"))
	            {int option=JOptionPane.showConfirmDialog(jDialog1, "���ڴ����������ֹͣ������Ӱ���������!\nȷ���Ƿ���Ҫֹͣ��","ע�⣡", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
	              if(option==JOptionPane.YES_OPTION){
	            	  
	            	  HardwareScanner.interrupted();
	            	  
	              }
	              if(option==JOptionPane.NO_OPTION){ jDialog1.dispose();}
	             
	              }
	              /***********************/
	          

	        }

	/*******************************/      

    
    /***********************************/
  

    /**
     * ��HardwareScanner
     * ����
     * ˵���������ڲ���
     */ 
    public class HardwareScanner extends Thread {
        private int cnt = 0;
        private boolean isHid = false;
        private boolean commonIndex=false;
        private static final int isScan=0;
        private int range;
        private FileReaderFactory faf = null;
        private IndexWriter writer = null;
        private static final String indexDir="d:\\indexDirectory" ;
        private Vector<String> ve;
       
        
        public HardwareScanner(){
            init();
        }
        
        private void init(){
            faf = new FileReaderFactory();
            setIndexDir();
            ve = new Vector<String>();
            String[] fileKind = new String[]{"txt","doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "rtf"
            , "rmvb", "rm","avi", "mkv", "flv", "wma", "mp3", "wav", "jpg", "jpeg", "bmp","png" ,"gif", "exe","rar","htm"};
            ve.addAll(Arrays.asList(fileKind));
        }
   
        
        private void setHid(boolean hid){
        	isHid=hid;
        }
        public boolean getHid(){
            return isHid;
        }
        private void setComm(boolean comm){
        	commonIndex=comm;
        }
        public boolean getComm(){
            return commonIndex;
        }
        
        private void setRange(int r ){
        	range=r;
         }
         public int getRange(){
             return range;
         }
        
        private void setIndexDir(){
            // indexDir = "d:\\index";
         }
         public String getIndexDir(){
             return indexDir;
         }
         
        
        private void setIsScan(int scan){
        	//isScan = scan;
        }
        public int getIsScan(){
            return isScan;
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
        
        public void createIndex(final File file ){
            try {
                Document doc = new Document();
                String fileName = file.getName();
                int ind = fileName.lastIndexOf(".");
                /*******************///�Ƿ�ֻɨ�賣���ļ�
                if ( getComm()&&!isIndexed(fileName)){
                    return;
                }
                /***********************/
                System.out.println("Indexing " + file.getCanonicalPath());
                cnt++;
                /**************************/
                	SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//jTextArea.append("ɨ���ļ���   "+file.getName()+"\n");
						try {
							jTextArea.append("ɨ���"+cnt+"���ļ���   "+file.getCanonicalPath()+"\n");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
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
        public void scan(int cho,  boolean isHid, boolean isCommon){
            cnt = 0;
            setComm(isCommon);
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
            /*************��¼�ϴ�ϵͳ״̬*******/
            
            long time =(endTime - startTime) / (1000*60);
            if(time<1){time=1;}
            state.setFrontTime(endTime);
            state.setScanCount(cnt);
            state.setScanTime(time);
            System.out.println(state.getFrontTime());
            //����+�̷�+�ļ�����+��ʱ
            String stateString=Long.toString(state.getFrontTime())+"@"+state.getScanDir()+"@"+Integer.toString(state.getScanCount())+"@"+Long.toString(state.getScanTime());
            System.out.println(stateString); 
            /*********************************************************/
            try {
            	SystemState.cleanText();
            	stateString = (new String(stateString.getBytes("ISO-8859-1"), "UTF-8")).trim();
				SystemState.writeTxtFile(stateString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            /*********************************************************/
        }
        public void getDir(String path, boolean isHid){
    	File files[] = (new File(path)).listFiles();
    	if (files == null){
                return;
    	}
    	for (File file : files){
                if (!isHid && file.isHidden()){
                    System.out.println("���");
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
        
        //end

        /******************/
        public void run ()
        {
        	int r=getRange();
        	boolean hid=getHid();
        	boolean comm=getComm();
        	System.out.println( hid); 
        	System.out.println( comm);
        	//scan("D:\\indexSource", hid);
        	scan(r, hid,comm);
        	 finishButton.setEnabled(true);
        	 scanJButton.setEnabled(true);
        	// exitButton.setEnabled(false);
        }
     
    	/*******************/
    }

    /**************************************/
	       
	       /////

	    }