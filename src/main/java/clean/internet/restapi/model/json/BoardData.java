package clean.internet.restapi.model.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BoardData {
    @JsonIgnore
    private String title;

    @JsonIgnore
    private String content;

    @JsonProperty("content")
    private String concatContent;
    private String category;
    private long no;

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getConcatContent() {
        return concatContent;
    }

    public void setConcatContent(String concatContent) {
        this.concatContent = concatContent;
    }
}
