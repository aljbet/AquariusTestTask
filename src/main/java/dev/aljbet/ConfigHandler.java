package dev.aljbet;

import dev.aljbet.services.FilesHandler;
import dev.aljbet.services.InputHandler;
import dev.aljbet.services.JsonFormatter;

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
     *
     * @param pathToConfig путь до конфиг-файла
     * @param id порядковый номер конфигурации
     * @return путь к json-файлу с результатом
     */
    public String handle(String pathToConfig, int id) {
        // передает эти самые параметры в обработчик, который проверяет валидность ссылки,
        // может бросить исключение, а если все ок, то
        // проходит по файлу, находит нужный ид, возвращает рекорд Configuration
        var config = inputHandler.handle(pathToConfig, id);

        // этот рекорд передается следующему обработчику, который читает поля,
        // проходится по всем файлам и для каждой строки из него вызывает очередной обработчик,
        // который зависит от поля action. тот возвращает строку, из которых составляем массив.
        var linksFromFiles = fileHandler.handle(config);

        // передаем этот массив строк и данные о конфиге, полученные раньше, в JSONhandler.
        // он форматирует, засовывает все в файл, путь к которому возвращается. done
        return jsonFormatter.format(config, linksFromFiles);
    }
}
