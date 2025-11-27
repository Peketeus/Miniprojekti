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

    @Override
    public void edit(String atribute, String value) {
        switch (atribute) {
            case "key":
                this.key = value;
                break;
            case "author" :
                this.author = value;
                break;
            case "title" :
                this.title = value;
                break;
            case "journal" :
                this.journal = value;
                break;
            case "year" :
                this.year = value;
                break;
            case "volume" :
                this.volume = value;
                break;
            case "pages" :
                this.pages = value;
                break;
            default:
                break;
        }
    }

    @Override
    public String[] information() {
        return new String[] {this.key, this.author, this.title, this.journal, this.year, this.volume, this.pages};
    }
}