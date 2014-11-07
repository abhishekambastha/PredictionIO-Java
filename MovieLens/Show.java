

//import io.prediction.*;

import com.google.common.collect.ImmutableList;

import io.prediction.EngineClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Show {
    public static void main(String[] args)
            throws ExecutionException, InterruptedException, IOException {
        EngineClient client = new EngineClient();

       // System.out.println(client.toString());
        // rank item 1 to 5 for each user
        Map<String, Object> query = new HashMap<>();
        query.put("uid", 100);		//
        query.put("n", 4);
        String res = client.sendQuery(query).toString();
        System.out.println(res);
        client.close();
    }
}