package cyr7.cli;

public enum Phase {
    INITIAL,
    FINAL,
    UNSUPPORTED;

    public static Phase parse(String s) {
        try {
            return Phase.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNSUPPORTED;
        }
    }
}
