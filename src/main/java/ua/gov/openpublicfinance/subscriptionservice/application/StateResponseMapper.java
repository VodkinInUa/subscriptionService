package ua.gov.openpublicfinance.subscriptionservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class StateResponseMapper implements ResponseMapper {

    private String theme;

    public StateResponseMapper() {
        theme = "state";
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
        return (HashMap<String, String>) data.get("response");
    }
    @Override
    public String getTheme(){
        return theme;
    }

}
