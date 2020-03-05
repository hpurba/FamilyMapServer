package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

/**
 * This class houses some generic functions the Handlers use
 * They include: serialize - readString - writeString
 *    - serialize: Type T (object) to Json String
 *    - readString: Converts an InputStream to a String
 *    - writeString:
 */
public class HandlerGeneric {
    /**
     * Converts a given object to a json String
     * @param input
     * @param <T>
     * @return
     */
    public static <T> String serialize(T input) {
        // JSON is a format for storing any kind of data in a tree structure
        // Gson is a tool google made to translate objects to/from json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(input);
    }

    /**
     * Converts a InputStream to a String
     * @param is
     * @return
     * @throws IOException
     */
    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * This takes the JsonString I generated from the response object AND the outputStream for which I will insert the json into. then close it
     * @param str
     * @param os
     * @throws IOException
     */
    public void writeString(String str, OutputStream os) throws IOException {
        // writer vs stream, writer is better for strings and characters, stream is for bytes (like 1s and 0s)
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);  // This is where we put the jsonstring into the outputstream(but is really a buffered writer)
        bw.flush();     // sends the buffered writer
    }
}
