package clean.internet.restapi.model.raw;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "api_token")
public class ApiTokenData {

    private int apiTokenUid;
    private String apiToken;
    private int deleteFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "API_TOKEN_UID", columnDefinition = "INT(11)")
    public int getApiTokenUid() {
        return apiTokenUid;
    }

    public void setApiTokenUid(int apiTokenUid) {
        this.apiTokenUid = apiTokenUid;
    }

    @Column(name = "API_TOKEN", columnDefinition = "VARCHAR(200)")
    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @Column(name = "DELETE_FLAG", columnDefinition = "INT(11)")
    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}