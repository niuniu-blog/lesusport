import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrTest {
    @Test
    public void addTest() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://solr.lesusport.com/solr");
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "1");
        doc.addField("name", "高富帅");
        solrServer.add(doc);
        solrServer.commit();
    }
}
