package data;

public enum PagesLinks {
    REGISTRATION_FORM("/automation-practice-form", "Practice Form"),
    LOGIN("/login", "Login"),
    BOOK_STORE("/books", "Book Store"),
    PROFILE("/profile", "Profile");

    private final String link,
            header;

    PagesLinks(String link, String header) {
        this.link = link;
        this.header = header;
    }

    public String getLink() {
        return link;
    }

    public String getHeader() {
        return header;
    }
}
