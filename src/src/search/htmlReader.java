package src.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.lucene.document.Field;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.tidy.Tidy;

public class htmlReader extends DtsFileReaderImpl
{   
	/**
	 * ����������string
	 */
    public String getTxt(File file) throws Exception{
        String fileText = "";
        FileInputStream inputStream = null;
        Tidy tidy=null;
        String title="";
        String body="";
        try{
            inputStream= new FileInputStream(file);
            tidy=new Tidy();
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			tidy.setInputEncoding("UTF-8");//���ñ����ʽ
			
			//�������HTML�ĵ���InputStream����
			org.w3c.dom.Document root=tidy.parseDOM(inputStream,null);
			Element rawDoc=root.getDocumentElement();
			
			 title=getTitle(rawDoc);//��ñ���
			 body=getBody(rawDoc);//���<body>��</body>֮������Ԫ��
			 fileText=title+body;
            
        }catch(Exception e){
            System.out.println("Indexing " + file.getCanonicalPath() + " error!");  
            e.printStackTrace();
        }
        finally {
            try {
                if (inputStream != null) {inputStream.close();}
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
	return fileText;
    }
	/**
	 * 
	 * @param is
	 * @return
	 */
    /***************************************
	public org.apache.lucene.document.Document getDocument(InputStream is)//throws  DocumentHandlerException
	//����һ�����HTML�ĵ���InputStream����
		{
			String fileText = "";
			FileInputStream instream = null;//�ļ���
			Tidy tidy=new Tidy();
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			//�������HTML�ĵ���InputStream����
			org.w3c.dom.Document root=tidy.parseDOM(is,null);
			Element rawDoc=root.getDocumentElement();
		 
			org.apache.lucene.document.Document doc=new org.apache.lucene.document.Document();
			String title=getTitle(rawDoc);//��ñ���
			String body=getBody(rawDoc);//���<body>��</body>֮������Ԫ��
			if((title!=null)&&(!title.equals(""))){
				doc.add(Field.Text("title",title));
		}
		if((body!=null)&&(!body.equals(""))){
		doc.add(Field.Text("body",body));
		}
			return doc;
}
/**************************************************/
    /**
     * �������ܣ��õ�����
     * @param rawDoc
     * @return
     */
	protected String getTitle(Element  rawDoc)
	{
		if(rawDoc==null){return null;}
			
		String title="";
		NodeList children=rawDoc.getElementsByTagName("title");
		
		if(children.getLength()>0)
		{//��õ�һ��<title>��־���ı�
			Element titleElement=((Element) children.item(0));
			Text text=(Text) titleElement.getFirstChild();
			if (text!=null){title=text.getData();}
			
		}
		return title;
}
	/**
	 * 
	 * @param rawDoc
	 * @return
	 */
	protected String getBody(Element rawDoc)
	{
		if (rawDoc==null){return null;}
		String body="";
		NodeList children=rawDoc.getElementsByTagName("body");//���<body>��־������
		if (children.getLength()>0)
		{
			body=getText(children.item(0));//��ȡ<body>��</body>֮��������ı�
		}
		return body;
  }
	/**
	 * 
	 * @param node
	 * @return
	 */
	protected String getText(Node node){
		NodeList children=node.getChildNodes();
		StringBuffer sb=new StringBuffer();
		//for (int i=0;i<children.getLength();i++)//��ȡ���ض�Node������Ԫ���е��ı�
		//{
			//Node child=node.getChildNodes();
			//StringBuffer sb=new StringBuffer();
			for (int i=0;i<children.getLength();i++)
			{
					Node child=children.item(i);
					switch (child.getNodeType())
					{
					case Node.ELEMENT_NODE:
						sb.append(getText(child));
						sb.append(" ");
						break;
					case Node.TEXT_NODE:
						sb.append(((Text) child).getData());
						break;
					}
			}
			return sb.toString();
		//}
	}
	/*******************************
	public   static   void   main(String[]   args)  throws Exception{
		htmlReader handler=new htmlReader();
		File ffFile=new File("F:\\indexSource\\Readme.htm");
		String ddString=handler.getTxt(ffFile);
		System.out.println(ddString);
}
	/**********************************/
}

