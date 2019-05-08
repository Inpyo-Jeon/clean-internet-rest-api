package clean.internet.restapi.model.raw;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "tokenized_data")
public class TokenizedData {

    private int no;
    private String contentTok;
    private int category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO", columnDefinition = "INT(11)")
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Column(name = "CONTENT_TOK", columnDefinition = "TEXT")
    public String getContentTok() {
        return contentTok;
    }

    public void setContentTok(String contentTok) {
        this.contentTok = contentTok;
    }

    @Column(name = "CATEGORY", columnDefinition = "char(1)")
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
