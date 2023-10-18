# UnoSoft-entry-task

Это проект для выполнения тестового задания.

## Сборка проекта

Для сборки проекта используйте Apache Maven. Перейдите в корневую директорию проекта и выполните следующую команду:

```bash
mvn package
```

После успешной сборки, в директории target будет создан исполняемый JAR-файл UnoSoftEntryTask-1.0.jar.

## Запуск программы

Положите текстовый файл (например, lng-4.txt) в директорию target, где находится JAR-файл.
Запустите программу с помощью команды java с опцией для ограничения памяти -Xmx1G (для проверки ограничения по памяти)  и указанием имени JAR-файла и имени текстового файла, например:

```bash
java -Xmx1G -jar UnoSoftEntryTask-1.0.jar lng-4.txt
```
Программа выполнит анализ и группировку данных из текстового файла. В консоль будет выведено количество полученных групп с более чем одним элементом и время выполнения программы.

Количество групп с более чем одним элементом и группы будут записаны в файл result.txt в директории target.
