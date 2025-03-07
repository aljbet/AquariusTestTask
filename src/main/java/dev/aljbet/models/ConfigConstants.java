package dev.aljbet.models;

public class ConfigConstants {
    public static final int K_LINES = 3;
    public static final String MODE = "#mode";
    public static final String PATH = "#path";
    public static final String ACTION = "#action";

    public static Mode getModeFromString(String mode) {
        mode = mode.strip().toLowerCase();
        return switch (mode) {
            case "files" -> Mode.FILES;
            case "dir" -> Mode.DIR;
            default -> throw new InvalidConfigFormatException("Invalid mode: " + mode);
        };
    }

    public static Action getActionFromString(String action) {
        action = action.strip().toLowerCase();
        return switch (action) {
            case "string" -> Action.STRING;
            case "count" -> Action.COUNT;
            case "replace" -> Action.REPLACE;
            default -> throw new InvalidConfigFormatException("Invalid action: " + action);
        };
    }
}
