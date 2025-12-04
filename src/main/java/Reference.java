import java.util.HashMap;
import java.util.Map;

/**
 * Viiteluokka, jonka oliolla pakolliset attribuutit type ja key.
 * Data-attribuuttiin voidaan lisätä avain-arvopareina viitteelle lisätietoja.
 */
public class Reference {
    private String type;                        // "book", "article", jne
    private final String key;                   // BibTeX viiteavain
    private String tag;                         // hakua varten, esim. genre
    private final Map<String, String> data;     // kentät viittelle, "Year", "author" 


    public Reference(String type, String key, String tag, Map<String, String> data) {
        this.type = type;
        this.key = key;
        this.tag = (tag != null) ? tag : "";
        this.data = (data != null) ? new HashMap<>(data) : new HashMap<>();
    }


    public String getKey() {
        return key;
    }


    public String getType() {
        return type;
    }

    public String getTag() {
        return tag;
    }


    public Map<String, String> getData() {
        return new HashMap<>(data);
    }


    public String getField(String field) {
        return data.get(field);
    }


    public String[] information() {
        return null;
    }

    /**
     * Viiteolion muuttaminen merkkijonoksi
     */
    public String toString() {
        StringBuilder referenceString = new StringBuilder();
        StringBuilder dataString = new StringBuilder();

        referenceString.append("Type: " + this.type + "\n");
        referenceString.append("Key: " + this.key + "\n");

        for (Map.Entry<String, String> i : data.entrySet()) {
            dataString.append(i.getKey() + ": " + i.getValue() + "\n");
        }

        referenceString.append(dataString);

        if (tag !=null && !tag.trim().isEmpty()) {
            referenceString.append("Tags: "). append(this.tag).append("\n");
        }

        return referenceString.toString();
    }
}