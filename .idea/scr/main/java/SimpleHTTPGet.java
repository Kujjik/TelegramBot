import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class SimpleHTTPGet {
    private static final String URL="https://google.by";
    public String getJson () throws IOException {
        String text=null;
        URL url = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.connect();
        BufferedReader r=new BufferedReader(new InputStreamReader((InputStream) con.getContent()));
        while (r.ready()){
            System.out.println(r.readLine());
            text+=r;
        }
        con.disconnect();
        return text;
    }

}
