package dev.aljbet.models;

import dev.aljbet.services.linehandlers.*;

public class Mapper {
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

    public static ILineHandler getLineHandlerFromAction(Action action) {
        return switch (action) {
            case STRING -> new StringLineHandler();
            case COUNT -> new CountLineHandler();
            case REPLACE -> new ReplaceLineHandler();
        };
    }
}
