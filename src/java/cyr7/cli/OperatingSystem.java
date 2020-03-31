package cyr7.cli;

public enum OperatingSystem {
    LINUX, UNSUPPORTED;

    public static OperatingSystem parse(String s) {
        try {
            return OperatingSystem.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNSUPPORTED;
        }
    }
}