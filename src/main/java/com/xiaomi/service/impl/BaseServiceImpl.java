package com.xiaomi.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiaomi.bean.BaseEntity;
import com.xiaomi.bean.Usermsg;
import com.xiaomi.service.BaseService;
import com.xiaomi.test.TestJunit;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @Author: xiaomi
 * @Description:
 * @Date:下午 1:17 2018/7/10 0010
 * @Email:a1205938863@gmail.com
 **/

@SuppressWarnings({"unchecked", "unused", "rawtypes"})
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {
    private static Logger logger = Logger.getLogger(BaseServiceImpl.class);

    @Autowired
    private TransportClient client;
    private final static String article = "xiaomi";
    private Class<T> entityClass;

    public BaseServiceImpl() {
        setEntityClass((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public int createIndex(Map<String, Boolean> data) {
        String content = entityClass.getSimpleName();
        Field[] declaredFields = entityClass.getDeclaredFields();

        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        param.put("type", "text");
        param.put("store", "false");
        param.put("term_vector", "with_positions_offsets");
        param.put("analyzer", "ik_pinyin_analyzer");
        param.put("boost", "10");
        map.put("pinyin", param);
        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(article);
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .startObject("properties");
            for (int i = 0; i < declaredFields.length; i++) {
                Field declaredField = declaredFields[i];
                String name = declaredField.getName();
                Boolean aBoolean = data.get(name);
                if (aBoolean != null) {
                    if (aBoolean) {
                        mapping.startObject(name)
                                .field("type", "text")
                                .endObject();
                    } else {
                        mapping.startObject(name)
                                .field("type", "text")
                                .startObject("fields").startObject("pinyin")
                                .field("type", "text")
                                .field("store", "false")
                                .field("term_vector", "with_positions_offsets")
                                .field("analyzer", "ik_pinyin_analyzer")
                                .field("boost", "10")
                                .endObject().endObject()
                                .endObject();
                    }
                }
            }
            mapping.endObject().endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        XContentBuilder field = null;
        try {
            field = jsonBuilder().startObject()
                    .startObject("index")
                    .startObject("analysis")
                    .startObject("filter").startObject("my_pinyin")
                    .field("type", "pinyin")
                    .field("first_letter", "prefix")
                    .field("padding_char", " ")
                    .endObject()
                    .endObject()
                    .startObject("analyzer")
                    .startObject("ik_pinyin_analyzer")
                    .field("type", "custom")
                    .field("tokenizer", "ik_smart")
                    .field("filter", "my_pinyin, word_delimiter")
                    .endObject().endObject()
                    .endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            cib.addMapping(content, mapping);
            cib.setSource(field);
            CreateIndexResponse res = cib.execute().actionGet();
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.info("添加失败");
        }
        return 1;
    }

    @Override
    public T getEnter(T t) {
        String content = entityClass.getSimpleName();
        GetResponse response = client.prepareGet(article, content, "PVMJiGQBll1KIoRT48Yr").execute()
                .actionGet();
        String json = response.getSourceAsString();
        if (null != json) {
            T t1 = JSON.parseObject(json, entityClass);
            System.out.println(json);
            return t1;
        } else {
            System.out.println("未查询到任何结果！");
        }
        return null;
    }

    @Override
    public List<T> queryList(String reason) {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        TermQueryBuilder w = QueryBuilders.termQuery("reason.pinyin", reason);
        SearchResponse response = client.prepareSearch(article).setQuery(w).get();
        List<T> arrayList=new ArrayList<>();
        for (SearchHit searchHit : response.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
           T t = JSON.parseObject(sourceAsString, entityClass);
            arrayList.add(t);
        }
        return arrayList;
    }
}
