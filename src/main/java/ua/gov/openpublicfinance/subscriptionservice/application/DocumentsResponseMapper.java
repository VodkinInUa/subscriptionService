package ua.gov.openpublicfinance.subscriptionservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DocumentsResponseMapper implements ResponseMapper{
    private String theme;

    public DocumentsResponseMapper() {
        theme = "documents";
    }

    @Override
    public HashMap<String,String> mapFromJson (String json){
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference =
                new TypeReference<HashMap<String, Object>>() {};
        Map<String, Object> data = null;
        try {
            data = mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e)
        {
            System.err.println(e);
            //TODO process exception json desirialisation
        }

        Map<String,Object> response = (Map<String, Object>) data.get("response");
        HashMap<String,String> responseItems = new HashMap<String, String>();
        response.forEach((key,value) -> {
            String itemJson = "{}";
            try{
                itemJson = mapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                System.err.println(e);
            }
            System.out.println(key + " -> "+ itemJson);
            responseItems.put(key, itemJson);
        } );

        return responseItems;
    }

    @Override
    public String getTheme() {
        return theme;
    }
}
