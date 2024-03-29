package com.soda.learn.web.controller;


import com.soda.learn.web.config.Properties;
import com.soda.learn.web.pojo.People;
import com.soda.learn.web.mapper.PeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@EnableConfigurationProperties(Properties.class)
public class HelloController {

    @Autowired
    PeopleMapper peopleMapper;

    private Properties properties;

    @Autowired
    JdbcTemplate jdbcTemplate;


    public HelloController(Properties properties) {
        this.properties = properties;
    }


    @GetMapping("/home")
    public String printHello(String name, String password) {
        return "hello: " + new Date();
    }


    @GetMapping("/config")
    public String getConfig() {
        return properties.name + ":" + properties.age;
    }


    @GetMapping("/people")
    public List<People> getTest() {

        RowMapper<People> peopleRowMapper =  (resultSet, i) ->{

            People people = new People();
            people.setName(resultSet.getString("name"));
            people.setAge(resultSet.getInt("age"));
            return people;
        };


//        jdbcTemplate.setMaxRows(100);

        List<People> lisi = jdbcTemplate.query("select * from people where name = ?", peopleRowMapper, "libin");
        System.out.println(lisi);
        return lisi;

}

    @GetMapping("/insertPeople")
    public String insertPeople() {
        People people = new People();
        people.setAge(18);
        people.setName("shengjingbin");
        peopleMapper.insertPeople(people);
        return "插入成功";
    }

    @PostMapping("/charset")
    public String charsetTest(@RequestParam("se-key") String name) {
        System.out.println(name);
        return name;
    }


}
