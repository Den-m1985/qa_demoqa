# Проект по автоматизации тестов на примере сайта [DemoQA](https://demoqa.com)
К проекту подключен Allure  
Прогнать все тесты:
```shell
./gradlew clean test  
```

Прогоняем только API тесты:
```shell
./gradlew clean apiTest  
```

Прогоняем только UI тесты:
```shell
./gradlew clean uiTest  
```

Запуск Allure отчета
```shell
./gradlew allureServe
```
