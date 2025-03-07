package dev.aljbet.services.linehandlers;

public class CountLineHandler implements ILineHandler {
    @Override
    public String handleLine(String line, int fileNumber) {
        return Integer.toString(line.split(" ").length);
    }
}
