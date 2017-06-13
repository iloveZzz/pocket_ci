package com.xianbei.pocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/6/12.
 */
public class PocketCusTest {
    public static void main(String[] args) {
        Map a =  new HashMap();

        System.out.println(a.get("aaa"));
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValueAsString("{}");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
