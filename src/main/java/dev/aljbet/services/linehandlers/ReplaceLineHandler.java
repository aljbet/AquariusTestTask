package dev.aljbet.services.linehandlers;

public class ReplaceLineHandler implements ILineHandler {
    @Override
    public String handleLine(String line, int fileNumber) {
        return line
                .replaceAll("a", Integer.toString(1 + fileNumber))
                .replaceAll("b", Integer.toString(2 + fileNumber))
                .replaceAll("c", Integer.toString(3 + fileNumber));
    }
}
