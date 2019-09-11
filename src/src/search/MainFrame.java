
package src.search;


import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;




import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import jeasy.analysis.MMAnalyzer;



/**
 * �ࣺ MainFrame
 * ������
 * ˵��:�����棬��Ҫͨ��������������Ϣ,��Ӽ����¼��������������ϵͳ��������档
 * 
 */ 
public   class   MainFrame   extends   JFrame  implements ActionListener
{   
	//private  JDesktopPane desktopPane = new JDesktopPane(); ;
	private JPanel northPanel,centerPanel,southPanel;
	private JMenuBar   memuBar;  // �洢�˵���
	private JButton searchButton;
	private JTextField keyField,noteField1,noteField2,noteField3,noteField4,noteField5;
	private JLabel keyJLabel,noteJLabel1,noteJLabel2,noteJLabel3,noteJLabel4;
	//private JTextArea outputArea;
	
	private JEditorPane jEditorPane;
	private JRadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,radioButton7;
	private int searchType=1;//����������
	private int docType=1;//�ļ�������
	private int isScan=0;//�ĵ�������
	private Vector<String> ve=new Vector<String>();
	private SystemState state=new SystemState();//���ϵͳ״̬

	public   MainFrame()
	{   
		this.setTitle("��������������������");  
		//�����˵���
        memuBar=new JMenuBar(); 
		buildMainMenu(memuBar); 
		/*************************************/
		
		String totalString="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//����ʱ���ʽ
		long timeOld=state.getFrontTime();
		Date date =new Date(timeOld);
		System.out.println("������ :" + date); 
		String sDateTime = sdf.format(date); //����
		int count=state.getScanCount();//�ļ���ʽ
		long time=state.getScanTime();//ʱ��
		int dirInt=state.getScanDir();//�̷�
		File files[] = File.listRoots();
		String dirString="";
		if(dirInt==0){dirString="ȫ��";}//全部
		else{dirString=files[dirInt - 1].toString();}
		if(state.readTxtFile()==null||state.readTxtFile().equals("")){totalString="           ";}
		//ȡ�������̷�
		else{totalString="ɨ������  "+sDateTime+"  �����̷�  "+dirString+"  �ļ�����  "+Integer.toString(count)+"  ��ʱԼ  "+Long.toString(time)+"  ����";}
		System.out.println(totalString);
		/****************************************/
				
		northPanel=new JPanel();
		northPanel.setLayout(new FlowLayout());
		centerPanel=new JPanel();
		centerPanel.setLayout(new BorderLayout());
		southPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		keyJLabel=new JLabel("����ؼ��֣�");
		keyJLabel.setForeground(Color.blue);
		noteJLabel1=new JLabel("�������ļ�  ");
		noteJLabel4=new JLabel("������  ");
		noteJLabel3=new JLabel("������ʱ  ");
		noteJLabel2=new JLabel("�ϴ�����״̬   ");
		
		
		
		searchButton=new JButton("search");
		keyField=new JTextField(68);
		noteField1=new JTextField(5);
		noteField1.setEditable(false);
		noteField1.setBackground(getBackground());
		noteField2=new JTextField("  "+totalString+"  ");
		noteField2.setEditable(false);
		noteField3=new JTextField(5);
		noteField3.setEditable(false);
		noteField4=new JTextField("  D:\\indexDirectory  ");
		noteField4.setEditable(false);

		
		
		 radioButton1=new JRadioButton("ȫ������", true);//全文搜索
		 radioButton2=new JRadioButton("�ļ�������");//文件名搜索
		 radioButton3=new JRadioButton("ȫ��", true);//全部
		 radioButton4=new JRadioButton("�ı��ĵ�");//文本文档
		 radioButton5=new JRadioButton("ͼƬ");//图片
		 radioButton6=new JRadioButton("��Ƶ����");//视频
		 radioButton7=new JRadioButton("����");//音乐
		 
		 JLabel label1=new JLabel("��ݣ�");
		 JLabel label2=new JLabel("   ���ͣ�");
		 label1.setForeground(Color.blue);
		 label2.setForeground(Color.blue);
		 //outputArea=new JTextArea();
		jEditorPane=new JEditorPane ();
		jEditorPane.setContentType("text/html");
		jEditorPane.setEditable(false); 
		
		ButtonGroup group1 = new ButtonGroup ();
		group1.add(radioButton1);
		group1.add(radioButton2);
		ButtonGroup group2 = new ButtonGroup ();
		group2.add(radioButton3);
		group2.add(radioButton4);
		group2.add(radioButton5);
		group2.add(radioButton6);
		group2.add(radioButton7);
		Box radioBox=Box.createHorizontalBox();	
		radioBox.add(label1);
		radioBox.add(radioButton1);
		radioBox.add(radioButton2);
		radioBox.add(label2);
		radioBox.add(radioButton3);
		radioBox.add(radioButton4);
		radioBox.add(radioButton5);
		radioBox.add(radioButton6);
		radioBox.add(radioButton7);
		//radioBox.add(Box.createVerticalStrut(100));
		
		Box  keyBox=Box.createHorizontalBox();	
		keyBox.add(keyJLabel);
		keyBox.add(keyField);
		keyBox.add(Box.createHorizontalStrut(8));
		keyBox.add(searchButton);
	
		Box  totalBox=Box.createVerticalBox();	
		totalBox.add(keyBox);
		totalBox.add(Box.createVerticalStrut(8));
		totalBox.add(radioBox);
		northPanel.setBackground (Color.white);
		northPanel.add(totalBox);

		/*****************************
		jScrollPane = new JScrollPane();
		jScrollPane .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//���ù�����һֱ��ʾ
		jScrollPane .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setViewportView(outputArea);
		//����������
		centerPanel.add(jScrollPane);
		/****************************************/
		 JScrollPane scrollPane=new JScrollPane();
	     scrollPane.setViewportView(jEditorPane);
	     centerPanel.add( scrollPane);
	    /*************************************/
	    Box  stateBox1=Box.createHorizontalBox();	
	    stateBox1.add(noteJLabel1);
	    stateBox1.add(noteField1);
	    stateBox1.add(Box.createHorizontalStrut(5));
	    stateBox1.add(new JSeparator(JSeparator.VERTICAL));
	  
	    Box  stateBox4=Box.createHorizontalBox();	
	    stateBox4.add(noteJLabel3);
	    stateBox4.add(noteField3);
	    stateBox4.add(Box.createHorizontalStrut(5));
	    stateBox4.add(new JSeparator(JSeparator.VERTICAL));
	    
	    Box  stateBox2=Box.createHorizontalBox(); 
	    stateBox2.add(noteJLabel4);
	    stateBox2.add(noteField4);
	    stateBox2.add(Box.createHorizontalStrut(5));
	    stateBox2.add(new JSeparator(JSeparator.VERTICAL));
	    
	    Box  stateBox3=Box.createHorizontalBox();	
	    stateBox3.add(noteJLabel2);
	    stateBox3.add(noteField2);
	   
	    
	    Box  totalBox2=Box.createHorizontalBox();	
		totalBox2.add(stateBox1);
		totalBox2.add(Box.createHorizontalStrut(5));
		totalBox2.add(stateBox4);
		totalBox2.add(Box.createHorizontalStrut(5));
		totalBox2.add(stateBox2);
		totalBox2.add(Box.createHorizontalStrut(5));
		totalBox2.add(stateBox3);
		totalBox2.add(Box.createHorizontalStrut(5));
		southPanel.add(totalBox2);
	   /***********************
		southPanel.add(noteJLabel1);
		southPanel.add(noteField1);
		southPanel.add(noteJLabel2);
		southPanel.add(noteField2);
		southPanel.add(noteJLabel3);
		southPanel.add(noteField3);
		southPanel.add(noteJLabel4);
		southPanel.add(noteField4);
/*****************************/
		/*****************************/
		//this.add(desktopPane);
		this.add(northPanel,BorderLayout.NORTH);//���ò��֣�ʹ�ù������ڱ���
		this.add(centerPanel,BorderLayout.CENTER); //���ò��֣�ʹ����tabbedPane���м�
		this.add(southPanel,BorderLayout.SOUTH);
		
		/********************************/
		this.getContentPane().add(northPanel,BorderLayout.NORTH);
		this.getContentPane().add(centerPanel,BorderLayout.CENTER);
		this.getContentPane().add(southPanel,BorderLayout.SOUTH);
		this.setVisible(true);  //��������ɼ�
		// ������λ�÷�����Ļ����
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(800, 700);
		Dimension framesize = this.getSize();
		int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth()
				/ 2;
		int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight()
				/ 2;
		this.setLocation(x, y);//����Jframeλ��
		this.setSize(950, 600);//����Jframe��С
		//���á����ڹرա��¼�����
		this.addWindowListener(new WindowCloser());
		
		//���ô������
		try { 
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel") ; 
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel") ; 
			UIManager.setLookAndFeel(" com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel") ;

			SwingUtilities.updateComponentTreeUI(this); 
			} 	catch (Exception ex) {} 
		
		
		
		ActionListener myListener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showResult();
			}
		};
		searchButton.registerKeyboardAction( myListener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW); 
		/******************************/
		searchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				showResult();
			}
		});
		/***************************/
		//searchButton.addActionListener(this);  
		radioButton1.addActionListener(this);
		radioButton2.addActionListener(this);  
		radioButton3.addActionListener(this);  
		radioButton4.addActionListener(this);  
		radioButton5.addActionListener(this);  
		radioButton6.addActionListener(this);  
		radioButton7.addActionListener(this);  
		
		jEditorPane.addHyperlinkListener(new HyperlinkListener() {
		      public void hyperlinkUpdate(HyperlinkEvent e) {
		          if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		            try {
		            	//FileHandler.openFile("D:\\indexS\\�����.txt");
		            	//String command = "explorer.exe "+ e.getURL().toString();//����Ǽ��±���,���� notepad.exe
		            	String command =e.getDescription();//ע�⣺�����ӱ���б������Э��ָ����e.getURL()���ܵõ�������ֻ����e.getDescription()�õ�href�����ݡ�
		            	 System.out.println( command+"��v��v��v��v��v˭˵��vsd  "+"\n");
		            	 FileHandler.openFile(command);
		            	
		            }
		            catch (Exception ex) {
		              ex.printStackTrace();
		            }
		          }
		        }
		      });
			validate();
	}
	/**
	 * ������ buildMainMenu
	 * ����JMenuBar
	 * ˵���� �����˵���
	 */ 
	
	protected   void   buildMainMenu(JMenuBar memuBar)
	{
		//�����˵�ѡ��
		JMenu indexMenu=new JMenu("����"); 
		indexMenu.setMnemonic(KeyEvent.VK_I);//���ÿ�ݼ�
		JMenu helpMenu=new JMenu("˵��"); 
		helpMenu.setMnemonic(KeyEvent.VK_M);//���ÿ�ݼ�
		JMenu outLookMenu=new JMenu("���"); 
		outLookMenu.setMnemonic(KeyEvent.VK_L);//���ÿ�ݼ�
		
		//�����Ӳ˵�ѡ��
		JMenuItem addIndexItem=new JMenuItem("��������"); 
		JMenuItem exitedItem=new JMenuItem("�˳�"); 
		JMenuItem helpItem=new JMenuItem("��������"); 
		JMenuItem aboutItem=new JMenuItem("����ϵͳ"); 	
		
		//�����۲˵���ѡ��
	    JMenuItem outLookItem = null;
	    UIManager.LookAndFeelInfo[] lookAndFeelInfo = UIManager.getInstalledLookAndFeels();
	    LookAndFeelListener myListener = new LookAndFeelListener();

	   try {
		    for (int i = 0; i < lookAndFeelInfo.length; i++) 
		    {
		    	outLookItem= new JMenuItem(lookAndFeelInfo[i].getName() + " ���");
		    	outLookItem.setActionCommand(lookAndFeelInfo[i].getClassName());
		    	outLookItem.addActionListener(myListener);
			    outLookMenu.add(outLookItem);
		    }
	     } catch (Exception e) {e.printStackTrace();}
	
		 //�ڲ˵�������Ӳ˵�
		memuBar.add(indexMenu); 
	    memuBar.add(helpMenu); 
	    memuBar.add(outLookMenu); 
	    
		//indexMenu�˵���Ӳ˵���
	    indexMenu.add(addIndexItem); 
	    indexMenu.addSeparator();//���Ӻ��
		indexMenu.add(exitedItem); 	
		
		//helpMenu�˵���Ӳ˵���
		helpMenu.add(helpItem); 
		helpMenu.addSeparator(); //���Ӻ��
		helpMenu.add(aboutItem); 	
	
		/*��Ӳ˵����¼���Ӧ*/
		
		//��Ӧ�˵�������������¼�
		addIndexItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		addIndexItem.addActionListener(this);  
		addIndexItem.setActionCommand("index");
		
		//��Ӧ�˵����˳��������¼�

		exitedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		exitedItem.addActionListener(this);
		exitedItem.setActionCommand("exited");  
		
		//��Ӧ�˵����������塱�����¼�
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));//��ӿ�ݲ˵�
		helpItem.addActionListener(this);  
		helpItem.setActionCommand("help");
		
		//��Ӧ�˵�������ϵͳ�������¼�
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));//��ӿ�ݲ˵�
		aboutItem.addActionListener(this);
		aboutItem.setActionCommand("about");
		
		setJMenuBar( memuBar);   
	
	}   
	/**
	 * �¼�������
	 */
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand()=="exited"){
			setVisible(false);
			dispose();
			System.exit(0);
			
		}
		if(e.getActionCommand()=="help"){ help();}
		if(e.getActionCommand()=="about"){ about();}
		if(e.getActionCommand()=="index")
		{
			 scanDialog scanDialog=new scanDialog(this);
			/**************************
			 
			//Container icontentPane = this.getContentPane();
			//this.add(new scanDialog());
			 
			desktopPane.add(new scanDialog());
			this.add(desktopPane);
		/****************************************/	
			}
		//if(e.getActionCommand()=="search"){showResult();};
		if(e.getActionCommand()=="ȫ������"){searchType=1;System.out.println( searchType+"\n");}//ȫ������全文搜索
		if(e.getActionCommand()=="�ļ�������"){searchType=2;System.out.println( searchType+"\n");};//�ļ�������文件名搜索
		if(e.getActionCommand()=="ȫ��"){    //ȫ��全部
			ve.removeAllElements();
			docType=1;

			}
		if(e.getActionCommand()=="�ı��ĵ�"){//�ı��ĵ�文本文档
			ve.removeAllElements();
			docType=2;
			String[] fileKind1 = new String[]{"txt","c","h","cpp","java", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "rtf"};
			ve.addAll(Arrays.asList(fileKind1));
			System.out.println( ve+"\n");
			}
		if(e.getActionCommand()=="ͼƬ"){//图片
			ve.removeAllElements();
			docType=3;
			String[] fileKind3 = new String[]{ "jpg", "jpeg", "bmp", "gif","png"};
			ve.addAll(Arrays.asList(fileKind3));
			System.out.println( ve+"\n");
			}
		if(e.getActionCommand()=="��Ƶ����"){//��Ƶ����视频
			ve.removeAllElements();
			docType=4;
			String[] fileKind2 = new String[]{ "rmvb", "rm","avi", "mkv", "flv", "wma", "mp3", "wav"};
			ve.addAll(Arrays.asList(fileKind2));
			System.out.println( ve+"\n");
			}
		if(e.getActionCommand()=="����"){//音乐
			ve.removeAllElements();
			docType=5;
			String[] fileKind4 = new String[]{ "exe","rar","htm"};
			ve.addAll(Arrays.asList(fileKind4));
			System.out.println( ve+"\n");
			}
		
	}
/**
 * ����������ɨ��ʱ��
 */
	public  void warning()
	{   
		//SystemState state=new SystemState();
		String message="";
		if(state.readTxtFile()==null||state.readTxtFile().equals(""))
		{
			message=" ��Ŀǰ��û�������� "+"\n"+"Ϊ����ļ���Ч���뼰ʱ��������";
			int check=JOptionPane.showConfirmDialog(null, message,"ע�⣡",JOptionPane.YES_NO_OPTION);
			if(check==JOptionPane.YES_OPTION){scanDialog scanDialog=new scanDialog(this);}
			//if(check==JOptionPane.YES_OPTION){scanDialog scanDialog=new scanDialog();}
			}
		else
		{
			long timeNow=new  Date().getTime();
			long timeOld=state.getFrontTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long between=(timeNow-timeOld)/(1000*60*60*24);
			Date date =new Date(timeOld);
			System.out.println("������ :" + date); 
			String sDateTime = sdf.format(date); 
			
			
			if(between>=10)
			{
				 message = "���Ѿ���   "+between+ " ��û����������ˣ�"+"\n"+"��һ������ʱ�䣺" + sDateTime + "\n" +"Ϊ����ļ���Ч���뼰ʱ����������" ;	
				int check=JOptionPane.showConfirmDialog(null, message,"ע�⣡",JOptionPane.YES_NO_OPTION);
				if(check==JOptionPane.YES_OPTION){scanDialog scanDialog=new scanDialog(this);}
				//if(check==JOptionPane.YES_OPTION){scanDialog scanDialog=new scanDialog();}
			}
		}
		
	}
	/**
	 * ������ showResult(ActionEvent e)
	 * ����
	 * ˵���� ��ʾ�������
	 */ 
	
	private void showResult()
	
	{		
			long startTime = new Date().getTime();//��¼��ʼʱ��
	
			String keyString=keyField.getText().toString().trim();
			
		    DtsSimpleQuery dtsQuery = new DtsSimpleQuery("d:\\indexDirectory");
	        Hits hits = dtsQuery.printResult(keyString,searchType);//��ѯ���
	        
	      
	   
	       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
	 	        jEditorPane.setText("");
	 	 
	 	  /********************************************/
	 	        Query query1 = null;
	             Query query2 = null;
	             BooleanQuery query = null;
	             QueryParser qp1 = new QueryParser("field", new StandardAnalyzer());
	             QueryParser qp2 = new QueryParser("name", new StandardAnalyzer());
	             try {
	 				query1 = qp1.parse(keyString);
	 				query2 = qp2.parse(keyString);
	 			} catch (ParseException e1) {
	 				// TODO Auto-generated catch block
	 				e1.printStackTrace();
	 			}
	             query = new BooleanQuery();
	             query.add(query1,BooleanClause.Occur.SHOULD);
	             query.add(query2, BooleanClause.Occur.SHOULD);
	 		/*******************************************************/
	 	 
	 	       
	         	Scorer scorer = new QueryScorer(query);
	         	SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>"); 
	         	Highlighter hig = new Highlighter( simpleHTMLFormatter,scorer);
	         	hig.setTextFragmenter(new SimpleFragmenter(60));
	         	
	         	String stringTotal="";
	         	String stringPath=null;
	         	String stringHig1=null;
	         	String stringHig2=null;
	         	int count=0;
	         	//int count2=0;
	         	//������ʾ
	 	        for (int i = 0;i < hits.length();i++){
	 	            try {
	 	            	
	 	            	 stringPath=null;
	 	            	 stringHig1=null;
	 	            	 stringHig2=null;
	 	                Document doc = hits.doc(i);
	 	                String filePath = doc.get("filePath");
	 	                File file = new File(filePath);
	 	                String stringName=file.getName();
	 	                String strType = stringName.substring(stringName.lastIndexOf(".") + 1).toLowerCase();
	 	                
	 	              // if (ve.contains(strType)==false){continue;}//����ɸѡ
	 	              if(docType!=1){if (ve.contains(strType)==false){continue;}}//����ɸѡ
	 	                /********************/
	 	              	count++;
	 	                String content=doc.get("contents");
	 	                String modifiedTime = sdf.format(file.lastModified());
	 	                String fileSize = String.valueOf((file.length() + 1023) / 1024) + "KB";
	 	                stringPath="<font color='green'>"+filePath+"&nbsp;&nbsp;"+fileSize+"&nbsp;&nbsp;"+modifiedTime+" </font>  "+"<br>";
	 	                
	 	                
	 	               /********************************/
	 	               
	 	            	if(content!=null)
	 	            	{
	 	            		TokenStream tokens = new StandardAnalyzer().tokenStream("field", new StringReader(content));
	 		            	//TokenStream tokens1 = new StandardAnalyzer().tokenStream("name", new StringReader(stringName));
	 		            	
	 		            	//stringHig1=hig.getBestFragments(tokens, content,3,"...")+"<br>";
	 	            		stringHig1=hig.getBestFragments(tokens, content,3,"...");
	 	            	}
	 	            	 stringHig2=hig.getBestFragment(new StandardAnalyzer(), "name", stringName);
	 	            	 if(stringHig2==null)stringHig2="<a href='"+filePath+"'> "+"<font color='blue',size='5'>"+"<b>"+"<u>"+stringName+"</b>"+"</u>"+"</font>"+"</a>";
	 	            	 if(stringHig2!=null)stringHig2="<a href='"+filePath+"'> "+"<font color='blue',size='5'>"+"<b>"+"<u>"+stringHig2+"</b>"+"</u>"+"</font>"+"<br>"+"</a>";
	 	            	 stringHig2="<font color='blue',size='5'>"+"<b>��"+strType+"��</b>"+"</font>"+stringHig2;
	 	            	  if(searchType==1&&stringHig1==null){stringTotal=stringTotal+stringHig2+"<br>"+stringPath;}
	 	            	  if(searchType==1&&stringHig1!=null){stringTotal=stringTotal+stringHig2+stringHig1+"<br>"+stringPath;}
	 	            	  if(searchType==2){stringTotal=stringTotal+stringHig2+stringPath;}
	 	            
	 	            } catch (IOException ex) { }
	 	            if(count>=100)break;
	 	           
	 	        }
	 	     
	 	       jEditorPane.setText(stringTotal);
	 	    //  long endTime2 = new Date().getTime();
	 	      long endTime = new Date().getTime();//��¼����ʱ��
	 	      noteField3.setText((endTime - startTime) / 1000.0 + "��");
	 	      noteField1.setText("  "+count+"    ��");
	 	      if(count==0){jEditorPane.setText("û���ҵ��κν�������������ݣ�<br><font color='blue',size='4'>1.�ؼ������룡<br>2.�Ƿ���������</font><br>");}
	   
	         dtsQuery.closeQuery();
	     
	}
	

	/**
	 * "�رմ���"�¼������ڲ���.
	 */
	class WindowCloser extends WindowAdapter {
		/**
		 * let's call our exit() method defined above
		 */
		public void windowClosing(WindowEvent e) {
			System.exit(0);;
		}
	}

	/**
	 * "���"ѡ������ڲ���
	 * 
	 */
	class LookAndFeelListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			String className = event.getActionCommand();
			try {
				UIManager.setLookAndFeel(className);

				SwingUtilities.updateComponentTreeUI(MainFrame.this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * "����˵�"���÷���
	 * 
	 */
	public void help(){
		JOptionPane.showMessageDialog(this, 
			
			" 1.��ִ�в���֮ǰ�����Ƚ�������\n"+
			" 2.���ڼ���Ƿȱ�������ٶ����������ĵȴ�\n" +
			" 3.�绹���������⣬��ٶ�֪����\n" 
			);
		}
	/**
	 * "���ڲ˵�"���÷���
	 * 
	 */
	public void about(){
		JOptionPane.showMessageDialog(this, 
				
				" ���ڼ���Ƿȱ����ϵͳ�����ۡ������ϲ��������⣬�����½⣡\n" 
				);
	}
	
public   static   void   main(String[]   args) 	{ 
		
		MainFrame mainFrame= new   MainFrame();   
		mainFrame.warning();
		//HardwareScanner hhHardwareScanner=new HardwareScanner();
		//hhHardwareScanner.scan(0, false, true);
	}  
}   
	  
