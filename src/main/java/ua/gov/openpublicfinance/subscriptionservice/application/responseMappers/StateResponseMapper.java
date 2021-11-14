package ua.gov.openpublicfinance.subscriptionservice.application.responseMappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StateResponseMapper implements ResponseMapper {
    private final Logger logger = LoggerFactory.getLogger(StateResponseMapper.class);

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
        return (HashMap<String, String>) data.get("response");
    }

}
