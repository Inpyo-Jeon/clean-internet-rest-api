package clean.internet.restapi.common;

public enum PeriodType {

    DAY("day"), WEEK("week"), MONTH("month"), YEAR("year");

    private String type;

    PeriodType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static PeriodType find(String attr) {
        for (PeriodType item : values()) {
            if (item.type.equals(attr)) {
                return item;
            }
        }
        return null;
    }
}
