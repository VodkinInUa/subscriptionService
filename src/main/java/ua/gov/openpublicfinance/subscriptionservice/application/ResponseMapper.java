package ua.gov.openpublicfinance.subscriptionservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public interface ResponseMapper {

    public HashMap<String,String> mapFromJson (String json);
    public String getTheme();

}
