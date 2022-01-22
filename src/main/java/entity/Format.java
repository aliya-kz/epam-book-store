package entity;

public class Format extends Entity {
    private int id;
    private String formatName;
    private String lang;

    public Format () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public int compareTo(Entity o) {
        return this.id - ((Format) o).getId();
    }
}
