# HomeLibrary

Учебный проект Мельникова Ярослава\
Курс [Kotlin Backend Developer](https://otus.ru/lessons/kotlin/).
Поток курса 2025-02.

HomeLibrary -- сервис для библиофилов и букинистов, позволяющий вести учет домашней библиотеки, 
делиться прочитанными книгами, создавать витрины для продажи и поиска книг и оставлять отзывы после прочтения.
Представляет собой справочник изданий, на основе которого можно создавать собственные списки книг различного назначения.

## Визуальная схема фронтенда

![Макет фронта](imgs/design-layout.png)
!TODO!

## Документация

1. Маркетинг и аналитика
    1. [Целевая аудитория](./docs/01-biz/01-target-audience.md)
    2. [Заинтересанты](./docs/01-biz/02-stakeholders.md)
    3. [Пользовательские истории](./docs/01-biz/03-bizreq.md)
2. Аналитика:
    1. [Функциональные требования](./docs/02-analysis/01-functional-requiremens.md)
    2. [Нефункциональные требования](./docs/02-analysis/02-nonfunctional-requirements.md)
3. DevOps
    1. [Файлы сборки](./deploy)
4. Архитектура
    1. [ADR](./docs/03-architecture/01-adrs.md)
    2. [Описание API](./docs/03-architecture/02-api.md)
    3. [Компонентная схема](./docs/03-architecture/03-arch.md)
5. Тесты

# Структура проекта

### Плагины Gradle сборки проекта

1. [build-plugin](build-plugin) Модуль с плагинами
2. [BuildPluginJvm](build-plugin/src/main/kotlin/BuildPluginJvm.kt) Плагин для сборки проектов JVM
2. [BuildPluginMultiplarform](build-plugin/src/main/kotlin/BuildPluginMultiplatform.kt) Плагин для сборки
   мультиплатформенных проектов

## Проектные модули

### Транспортные модели, API

1. [specs](specs) - описание API в форме OpenAPI-спецификаций
2. [homelibrary-api-v1-kmp](homelibrary-be/homelibrary-api-v1-kmp) - Генерация транспортных моделей с KMP
3. [homelibrary-common](homelibrary-be/homelibrary-common) - модуль с общими классами для модулей проекта 
   (внутренние модели и контекст)
4. [homelibrary-api-log](homelibrary-be/homelibrary-api-log) - Маппер между внутренними моделями и
   моделями логирования

### Фреймворки и транспорты

1. [homelibrary-app-ktor](homelibrary-be/homelibrary-app-ktor) - Приложение на Ktor
2. [homelibrary-app-kafka](homelibrary-be/homelibrary-app-kafka) - Микросервис на Kafka

### Модули бизнес-логики

1. [homelibrary-stubs](homelibrary-be/homelibrary-stubs) - Стабы для ответов сервиса
2. [homelibrary-biz](homelibrary-be/homelibrary-biz) - Модуль бизнес-логики приложения: обслуживание стабов,
   валидация, работа с БД

## Библиотеки

### Мониторинг и логирование

1. [deploy](deploy) - Инструменты мониторинга и деплоя
2. [homelibrary-lib-logging-common](homelibrary-libs/homelibrary-lib-logging-common) - Общие объявления для
   логирования
3. [homelibrary-lib-logging-logback](homelibrary-libs/homelibrary-lib-logging-logback) - Библиотека логирования
   на базе библиотеки Logback
4. [homelibrary-lib-logging-kermit](homelibrary-libs/homelibrary-lib-logging-kermit) - Библиотека логирования
   на базе библиотеки Kermit

## Тестирование

### Сквозные/интеграционные тесты

1. [homelibrary-e2e-be](homelibrary-tests/homelibrary-e2e-be) - Сквозные/интеграционные тесты для бэкенда
   системы //TODO