package dev.aljbet.services;

/**
 * Основной сервис, отвечающий за логику,
 * контролирует порядок вызова сервисов-обработчиков,
 * чтобы было проще добавить новый по необходимости.
 */
public class ConfigHandler {
    InputHandler inputHandler = new InputHandler();
    FilesHandler fileHandler = new FilesHandler();
    JsonFormatter jsonFormatter = new JsonFormatter();

    /**
     * @param pathToConfig путь до конфиг-файла
     * @param id           порядковый номер конфигурации
     * @return путь к json-файлу с результатом
     */
    public String handle(String pathToConfig, int id) throws Exception {
        var config = inputHandler.handle(pathToConfig, id);
        var linksFromFiles = fileHandler.handle(config);
        return jsonFormatter.format(config, linksFromFiles);
    }
}
