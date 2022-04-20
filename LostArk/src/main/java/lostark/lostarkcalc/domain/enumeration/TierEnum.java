package lostark.lostarkcalc.domain.enumeration;

/**
 * The TierEnum enumeration.
 */
public enum TierEnum {
    Tier1("Tier 1"),
    Tier2("Tier 2"),
    Tier3Low("Tier 3 Low"),
    Tier3Mid("Tier 3 Mid"),
    Tier3High("Tier 3 High");

    private final String value;

    TierEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
