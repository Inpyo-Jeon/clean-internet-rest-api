package clean.internet.restapi.model.raw;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "crw_dc_data")
public class CrwDcData {

    private long no;
    private String title;
    private Date date;
    private String content;
    private String contentTok;
    private String category;
    private String board;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "content_tok")
    @Type(type = "text")
    public String getContentTok() {
        return contentTok;
    }

    public void setContentTok(String contentTok) {
        this.contentTok = contentTok;
    }

    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Column(name = "board")
    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}




