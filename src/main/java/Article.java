
/*public class Article extends Reference {
    private String journal;
    private String volume;
    private String pages;

    public Article(String key, String author, String title, String journal, String year, String volume, String pages) {
        super(key, author, title, year);
        this.journal = journal;
        this.volume = volume;
        this.pages = pages;
    }

    @Override
    public void edit(String attribute, String value) {
        switch (attribute) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Article\n");
        sb.append("key: " + key + "\n");
        if (!author.isEmpty()) { sb.append("author: " + author + "\n"); }
        if (!title.isEmpty()) { sb.append("title: " + title + "\n"); }
        if (!journal.isEmpty()) { sb.append("journal: " + journal + "\n"); }
        if (!year.isEmpty()) { sb.append("year: " + year + "\n"); }
        if (!volume.isEmpty()) { sb.append("volume: " + volume + "\n"); }
        if (!pages.isEmpty()) { sb.append("pages: " + pages + "\n"); }
        sb.append("\n");

        return sb.toString();
    }
} */