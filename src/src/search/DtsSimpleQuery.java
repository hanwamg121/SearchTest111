/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.search;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import jeasy.analysis.MMAnalyzer;
/****************
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
/*******************/
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author yltaurb
 */
public class DtsSimpleQuery extends DtsQuery{
    public DtsSimpleQuery(String indexDir){
        super(indexDir);
    }
    public Hits printResult(String req,int searchType){
     try {
        	Hits hits = null;
        	TopDocs topDocs=null;
        	if(searchType==1)//��ȫ����������
        	{	
        		Query query1 = null;
        		Query query2 = null;
        		BooleanQuery query = null;
        		QueryParser qp1 = new QueryParser("contents", new StandardAnalyzer());//�����û���ѯ���ʽ
        		QueryParser qp2 = new QueryParser("fileName", new StandardAnalyzer());
        		query1 = qp1.parse(req);
        		query2 = qp2.parse(req);
            
        		query = new BooleanQuery();
        		query.add(query1,BooleanClause.Occur.SHOULD);
        		query.add(query2, BooleanClause.Occur.SHOULD);
        		hits = indexSearcher.search(query);
        		
        	}
        	if(searchType==2)//���ļ�������
        	{
        		QueryParser qp3 = new QueryParser("fileName", new StandardAnalyzer());
        		Query query3 = null;
        		query3= qp3.parse(req);
        		hits = indexSearcher.search(query3);//��ѯ
        		
        	}
        		
            return hits;
        } catch (ParseException ex) {
            Logger.getLogger(DtsSimpleQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DtsSimpleQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
