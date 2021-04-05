# ExcelWebService
REST API Web Service that upload .xlsx files, process and return a response

## Описание сервиса
Cервис получает  файл в формате .xlsx, создает новый файл только с четными числами. При этом четными могут быть только целые числа. Ответ возможно получить для просмотра в формате JSON, либо в формате файла .xlsx.

## Способы подключения

***POST	/api/process*** -
Загружает файл, обрабатывает и возвращает результат обработки в теле ответа в формате JSON

***POST	/api/process?download=true*** -
Загружает файл, обрабатывает и возвращает результат обработки в формате .xlsx


## Технологии
➢	Java 8

➢	Spring Boot 2 (with Spring Web MVC)

➢	Maven 3.6.1

➢	Apache POI 4.1.2

## Способ развертывания 
java -jar exportexcel-0.0.1.jar

## Логирование
Логирование ведется в каталоге запуска jar exportexcel-0.0.1.jar в файле “log.txt”.  В случае если файл не найден, он создается заново.

## Автор
Беляев Алексей Валерьевич (bel.alexeyv@gmail.com)
