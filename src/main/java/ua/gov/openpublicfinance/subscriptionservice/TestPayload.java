package ua.gov.openpublicfinance.subscriptionservice;

import java.util.Iterator;
import java.util.Random;

public class TestPayload {

    final private String[] themes = {
            "states",
            "documents",
            "transactions"
    };

    final private String[] edrpous = {
            "00034022",
            "00032684",
            "00032684",
            "37508596",
            "37552996",
            "00026620",
            "43672853",
            "40446210",
            "37472062",
            "38649881",
            "00034022",
            "38621185",
            "00012925",
            "37471928",
            "37567866",
            "42657144",
            "00013480",
            "43220851",
            "00015622",
            "43733545"};


    public Iterator<String> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < edrpous.length && edrpous[currentIndex] != null;
            }

            @Override
            public String next() {
                String json = withRandomTheme(currentIndex);
                currentIndex++;
                return json;

            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private String withRandomTheme(int index) {
        Random random = new Random();
        return subscribeInJson(edrpous[index],themes[random.nextInt(themes.length)] );
    }
    private String subscribeInJson(String edropu, String theme) {
        return "{\"target\": \""+edropu+"\"," +
                "\"theme\": \""+theme+"\"," +
                "\"subscriber\": \"111111111\"," +
                "\"subscriptionId\": \""+randInt(100000,999999)+"\"" +
                "}";
    }

    private int randInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
