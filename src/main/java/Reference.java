public abstract class Reference {
    protected String key;
    protected String author;
    protected String title;
    protected String year;

    public String getKey() {
        return key;
    }

    public void edit(String a, String b) {
        //tyhjä funktio joka overridataan
    }
    public String[] information() {
        return null;
    }
}