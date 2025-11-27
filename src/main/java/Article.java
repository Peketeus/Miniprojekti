public class Article extends Reference {
    // luokka artikkeli oliolle
    private String journal;
    private String volume;
    private String pages;

    public Article(String key, String author, String title, String journal, String year, String volume, String pages) {
        this.key = key;
        this.author = author;
        this.title = title;
        this.journal = journal;
        this.year = year;
        this.volume = volume;
        this.pages = pages;
    }
}