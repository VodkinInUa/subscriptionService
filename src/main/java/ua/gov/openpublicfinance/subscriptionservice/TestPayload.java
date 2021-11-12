package ua.gov.openpublicfinance.subscriptionservice;

import java.util.Iterator;
import java.util.Random;

public class TestPayload {

    private String[] themes = {
            "states",
            "documents",
            "transactions"
    };

    private String[] edrpous = {
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
        Iterator<String> iterator = new Iterator<String>() {
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
        return iterator;
    }

    private String withRandomTheme(int index) {
        Random random = new Random();
        return subscribeInJson(edrpous[index],themes[random.nextInt(themes.length)] );
    }
    private String subscribeInJson(String edropu, String theme) {
        String json = "{\"target\": \""+edropu+"\"," +
                "\"theme\": \""+theme+"\"," +
                "\"subscriber\": \"111111111\"," +
                "\"subscriptionId\": \""+randInt(100000,999999)+"\"" +
                "}";
        return json;
    }

    private int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will get not very good / not very random results.
        Random random = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
