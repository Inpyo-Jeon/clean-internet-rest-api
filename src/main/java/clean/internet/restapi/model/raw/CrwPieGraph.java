package clean.internet.restapi.model.raw;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigInteger;

public class CrwPieGraph {
    private String dateTime;
    private BigInteger yes;
    private BigInteger no;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public BigInteger getYes() {
        return yes;
    }

    public void setYes(BigInteger yes) {
        this.yes = yes;
    }

    public BigInteger getNo() {
        return no;
    }

    public void setNo(BigInteger no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
