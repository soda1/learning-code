package com.soda.learn.controller;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author soda
 * @date 2021/5/9
 */
@RestController
@RequestMapping("/es")
public class ESController {

    @Autowired
    TransportClient transportClient;

    @PostMapping("/index/{index}")
    public String createIndex(@PathVariable("index") String index) {

        Map map = new HashMap<String, String>();
        map.put("name", "李斌");
        map.put("age", 18);
        transportClient.prepareIndex(index, "test").setSource(map).get();
        return "success";

    }



}
