package dev.aljbet;

import dev.aljbet.services.ConfigHandler;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Main <path-to-config> <id-in-config>");
            return;
        }
        try {
            var id = Integer.parseInt(args[1]);
            ConfigHandler configHandler = new ConfigHandler();
            System.out.println(configHandler.handle(args[0], id));
        }
        catch (NumberFormatException e) {
            System.out.println("Usage: java Main <path-to-config> <id-in-config>");
            System.out.println("<id-in-config> must be integer");
        } catch (UnsupportedOperationException e) {
            System.out.println("приложение требует доработки");
        } catch (FileNotFoundException e) {
            System.out.println("Неизвестное имя файла: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}