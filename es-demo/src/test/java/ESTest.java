import com.domain.Phone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soda.learn.ESApplication;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author soda
 * @date 2021/5/9
 *
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class ESTest {

    @Autowired
    TransportClient transportClient;

    @Test
    public void test1() {
        IndexRequest indexRequest = new IndexRequest();
        transportClient.prepareIndex("soda", "test").get();
    }

    @Test
    public void doc() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        for (int i = 10; i < 100; i = i+10) {
            System.out.println(i);
            Phone xiaomi = new Phone("小米" + i, "小米手机", Math.random() * 10000);
            Phone hongmi = new Phone("红米note" + i / 10, "红米手机", Math.random() * 2000);
            Phone huawei = new Phone("华为mate" + i, "华为手机", Math.random() * 10000);
            String xiaomis = mapper.writeValueAsString(xiaomi);
            String hongmis = mapper.writeValueAsString(hongmi);
            String huaweis = mapper.writeValueAsString(huawei);
            bulkRequestBuilder.add(new IndexRequest("soda", "test").source(xiaomis, XContentType.JSON))
                    .add(new IndexRequest("soda", "test").source(hongmis, XContentType.JSON))
                    .add(new IndexRequest("soda", "test").source(huaweis, XContentType.JSON));
            BulkResponse bulkItemResponses = bulkRequestBuilder.get();
            if (bulkItemResponses.hasFailures()) {
                System.out.println("失败");
            } else {
                bulkItemResponses.getItems();
            }

        }
    }

    @Test
    public void query() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        /*
        SearchResponse soda = transportClient.prepareSearch("soda")
                .setQuery(matchAllQueryBuilder).setFrom(0).setSize(100).get();        //分页配置
        */


        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "小米");
        //SearchResponse soda = transportClient.prepareSearch("soda").setQuery(termQueryBuilder).get();

        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "mate");
        //SearchResponse soda = transportClient.prepareSearch("soda").setQuery(queryBuilder).get();



        //在多个字段上搜索
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("华为", "name", "brand");
        //SearchResponse soda = transportClient.prepareSearch("soda").setQuery(multiMatchQueryBuilder).get();

        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("(小米|华为)")
                .defaultField("brand");
        SearchResponse soda = transportClient.prepareSearch("soda").setQuery(queryStringQueryBuilder)
                .setSize(1000)
                .setFrom(0)
                .get();

        Iterator<SearchHit> iterator = soda.getHits().iterator();
        int sum = 0;
        while (iterator.hasNext()) {
            sum ++;
            SearchHit next = iterator.next();
            String sourceAsMap = next.getSourceAsString();
            Phone phone = mapper.readValue(sourceAsMap, Phone.class);
            System.out.println(phone);
        }
        System.out.println(sum);
    }


    @Test
    public void aggQuery() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        AvgAggregationBuilder price = AggregationBuilders.avg("avgPrice").field("price");
        TermsAggregationBuilder term = AggregationBuilders.terms("term").field("brand");
        term.subAggregation(price);
        SearchResponse soda = transportClient.prepareSearch("soda").setTypes("test")

                .addAggregation(term).get();

        //获取聚合
        Terms terms = soda.getAggregations().get("term");
        //遍历聚合内的bucket
        terms.getBuckets().forEach(( bucket) -> {
            System.out.println(bucket.getDocCount());
            System.out.println(bucket.getKey());
            //获取子桶价格
            Avg price1 = bucket.getAggregations().get("avgPrice");
            System.out.println(price1.getName() + ":" + price1.getValue());

        });


    }

    @Test
    public void deleteIndex() {
        transportClient.admin().indices().prepareDelete("soda").get();
    }
    /**
     * 配置索引
     */
    @Test
    public void configIndex() throws IOException {
        //mapping构造有点麻烦，我居然强迫症的想弄出来，反正就是要构成成和下面一样的json格式数据.
        // 挺傻逼的，应该没有会在代码上创建索引。。。
        /**
         * PUT soda
         * {
         *   "settings": {
         *     "analysis" : {
         *     "analyzer":{
         *       "content":{
         *         "type":"custom",
         *         "tokenizer":"ik_max_word"
         *       }
         *     }
         *   }
         *
         *   },
         *   "mappings":{
         *     "test":{
         *       "properties":{
         *         "name":{
         *           "type":"text",
         *           "analyzer":"ik_max_word"
         *         },
         *         "brand":{
         *           "type": "text",
         *           "analyzer":"ik_max_word"
         *         }
         *       }
         *     }
         *   }
         * }
         */
        Map map = new HashMap<String,Object>();
        Map typeMap = new HashMap<String,String>();
        XContentBuilder properties = jsonBuilder().startObject()
                .field("properties",
                        jsonBuilder().startObject().field("name",
                                jsonBuilder().startObject()
                                        .field("type", "text")
                        .field("analyzer", "ik_max_word").endObject().toString()).endObject().toString()).endObject();

        typeMap.put("type", "text");
        //typeMap.put("analyzer", "ik_max_word");
        map.put("name", typeMap);
        map.put("brand", typeMap);

        IndicesAdminClient indices = transportClient.admin().indices();
        indices.prepareCreate("soda")
                .setSettings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2))
                .get();
        PutMappingRequestBuilder putMappingRequestBuilder = indices.preparePutMapping("soda")
                .setType("test")
                .setSource(properties);


    }
}
