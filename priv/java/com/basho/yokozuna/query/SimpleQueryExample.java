/*
 * This is a simple query example to show that querying Yokozuna with
 * a standard Solr client works.
 *
 * Usage:
 *
 * java -cp priv/java_lib/yokozuna.jar:priv/solr-jars/WEB-INF/lib/* com.basho.yokozuna.query.SimpleQueryExample BASE_URL INDEX FIELD TERM
 *
 * Example:
 *
 * java -cp priv/java_lib/yokozuna.jar:priv/solr-jars/WEB-INF/lib/* com.basho.yokozuna.query.SimpleQueryExample http://localhost:8098/search fruit text apple
 */

package com.basho.yokozuna.query;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.params.ModifiableSolrParams;

public class SimpleQueryExample {

    public static void main(String[] args) throws SolrServerException {
        String baseURL = args[0];
        String index = args[1];
        String field = args[2];
        String term = args[3];

        SolrServer solr = new HttpSolrServer(baseURL);
        ModifiableSolrParams params = new ModifiableSolrParams();

        // I'm not sure it using 'qt' is cheating but I couldn't get
        // 'req.setPath' to work.
        params.set("qt", "/" + index);
        params.set("q", field + ":" + term);
        SolrRequest req = new QueryRequest(params);

        // Currently Yokozuna doesn't support the canonical Solr url,
        // i.e. 'solr/<core>/select?...' but instead uses
        // 'search/<index>/?...'
        //
        // TODO: this didn't work
        // req.setPath("/" + index);

        QueryResponse resp = solr.query(params);
        System.out.println("resp: " + resp);
    }
}
