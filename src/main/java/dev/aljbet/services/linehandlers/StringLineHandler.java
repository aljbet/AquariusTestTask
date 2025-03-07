package dev.aljbet.services.linehandlers;

public class StringLineHandler implements ILineHandler {
    @Override
    public String handleLine(String line, int fileNumber) {
        return line;
    }
}
