package com.soda.configure;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class MyDefaultErrorAttributes extends DefaultErrorAttributes {

    public MyDefaultErrorAttributes() {
        super();
    }

    public MyDefaultErrorAttributes(boolean includeException) {
        super(includeException);
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("name", "libin");
        errorAttributes.put("age", 18);
        errorAttributes.put("score", 98);
        return errorAttributes;
    }

    public static void main(String[] args) {
        new MyDefaultErrorAttributes();
    }
}
