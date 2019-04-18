package clean.internet.restapi.common;

public enum LineGraphType {

    DAY("day"), WEEK("week"), MONTH("month"), YEAR("year");

    private String type;

    LineGraphType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static LineGraphType find(String attr) {
        for (LineGraphType item : values()) {
            if (item.type.equals(attr)) {
                return item;
            }
        }
        return null;
    }
}
