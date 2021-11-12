package ua.gov.openpublicfinance.subscriptionservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DocumentsResponseMapper implements ResponseMapper{
    private final Logger logger = LoggerFactory.getLogger(DocumentsResponseMapper.class);
    private final String theme;

    public DocumentsResponseMapper() {
        theme = "documents";
    }

    @Override
    public HashMap<String,String> mapFromJson (String json){
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference =
                new TypeReference<>() {};
        Map<String, Object> data = null;
        try {
            data = mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e)
        {
            logger.error("Cant`t map json \""+json+"\"",e);
            //TODO process exception json desirialisation
        }

        Map<String,Object> response = (Map<String, Object>) data.get("response");
        HashMap<String,String> responseItems = new HashMap<>();
        response.forEach((key,value) -> {
            String itemJson = "{}";
            try{
                itemJson = mapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                logger.error("Cant`t map itemJson \""+itemJson+"\"",e);
            }
            responseItems.put(key, itemJson);
        } );

        return responseItems;
    }

    @Override
    public String getTheme() {
        return theme;
    }
}
