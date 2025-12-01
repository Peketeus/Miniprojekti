import java.util.HashMap;
import java.util.Map;

public class Reference {
    private String type;                        // "book", "article", jne
    private final String key;                   // BibTeX viiteavain
    private final Map<String, String> data;     // kentät viittelle, "Year", "author" 

    public Reference(String type, String key, Map<String, String> data) {
        this.type = type;
        this.key = key;
        this.data = (data != null) ? new HashMap<>(data) : new HashMap<>();
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getData() {
        return new HashMap<>(data);
    }

    public String getField(String field) {
        return data.get(field);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setField(String field, String value) {
        if (field == null) return;
        if (value == null) data.remove(field);
        else data.put(field, value);
    }

    public void edit(String a, String b) {
        setField(a, b);
    }

    public String[] information() {
        return null;
    }
}