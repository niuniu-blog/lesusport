package com.haichuang.lesusport.service.message;

import com.haichuang.lesusport.service.portal.product.SearchService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;

@Component("customerMessageListener")
public class CustomerMessageListener implements MessageListener {
    @Autowired
    private SearchService searchService;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage tm = (ActiveMQTextMessage) message;
        try {
            String id = tm.getText();
            searchService.saveProductToSolr(Long.parseLong(id));
        } catch (JMSException | IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }
}
