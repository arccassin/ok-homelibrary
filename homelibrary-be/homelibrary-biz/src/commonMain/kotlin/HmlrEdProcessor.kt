package com.otus.otuskotlin.homelibrary.biz

import com.otus.otuskotlin.homelibrary.biz.general.initStatus
import com.otus.otuskotlin.homelibrary.biz.general.operation
import com.otus.otuskotlin.homelibrary.biz.general.stubs
import com.otus.otuskotlin.homelibrary.biz.repo.checkLock
import com.otus.otuskotlin.homelibrary.biz.repo.initRepo
import com.otus.otuskotlin.homelibrary.biz.repo.prepareResult
import com.otus.otuskotlin.homelibrary.biz.repo.repoCreate
import com.otus.otuskotlin.homelibrary.biz.repo.repoDelete
import com.otus.otuskotlin.homelibrary.biz.repo.repoPrepareCreate
import com.otus.otuskotlin.homelibrary.biz.repo.repoPrepareDelete
import com.otus.otuskotlin.homelibrary.biz.repo.repoPrepareUpdate
import com.otus.otuskotlin.homelibrary.biz.repo.repoRead
import com.otus.otuskotlin.homelibrary.biz.repo.repoSearch
import com.otus.otuskotlin.homelibrary.biz.repo.repoUpdate
import com.otus.otuskotlin.homelibrary.biz.stubs.stubCanNotDelete
import com.otus.otuskotlin.homelibrary.biz.stubs.stubCreateSuccess
import com.otus.otuskotlin.homelibrary.biz.stubs.stubDbError
import com.otus.otuskotlin.homelibrary.biz.stubs.stubDeleteSuccess
import com.otus.otuskotlin.homelibrary.biz.stubs.stubNoCase
import com.otus.otuskotlin.homelibrary.biz.stubs.stubReadSuccess
import com.otus.otuskotlin.homelibrary.biz.stubs.stubSearchSuccess
import com.otus.otuskotlin.homelibrary.biz.stubs.stubUpdateSuccess
import com.otus.otuskotlin.homelibrary.biz.stubs.stubValidationBadAuthor
import com.otus.otuskotlin.homelibrary.biz.stubs.stubValidationBadId
import com.otus.otuskotlin.homelibrary.biz.stubs.stubValidationBadIsbn
import com.otus.otuskotlin.homelibrary.biz.stubs.stubValidationBadSearchString
import com.otus.otuskotlin.homelibrary.biz.stubs.stubValidationBadTitle
import com.otus.otuskotlin.homelibrary.biz.stubs.stubValidationBadYear
import com.otus.otuskotlin.homelibrary.biz.validation.REG_EXP_CONTENT
import com.otus.otuskotlin.homelibrary.biz.validation.REG_EXP_ID
import com.otus.otuskotlin.homelibrary.biz.validation.validateAuthor
import com.otus.otuskotlin.homelibrary.biz.validation.validateId
import com.otus.otuskotlin.homelibrary.biz.validation.validateIsbn
import com.otus.otuskotlin.homelibrary.biz.validation.validateLock
import com.otus.otuskotlin.homelibrary.biz.validation.validateSearchStringLength
import com.otus.otuskotlin.homelibrary.biz.validation.validateTitle
import com.otus.otuskotlin.homelibrary.biz.validation.validateYear
import com.otus.otuskotlin.homelibrary.biz.validation.validation
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdLock
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.cor.chain
import com.otus.otuskotlin.homelibrary.cor.rootChain
import com.otus.otuskotlin.homelibrary.cor.worker

class HmlrEdProcessor(
    private val corSettings: HmlrCorSettings = HmlrCorSettings.NONE
) {
    suspend fun exec(ctx: HmlrContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<HmlrContext> {
        initStatus("Инициализация статуса")
        initRepo("Инициализация репозитория")

        operation("Создание издания", HmlrCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации названия")
                stubValidationBadAuthor("Имитация ошибки валидации автора")
                stubValidationBadIsbn("Имитация ошибки валидации ISBN")
                stubValidationBadYear("Имитация ошибки валидации года")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                validateTitle("Валидация названия")
                validateAuthor("Валидация автора")
                validateIsbn("Валидация ISBN")
                validateYear("Валидация года")
            }

            chain {
                title = "Логика сохранения"
                repoPrepareCreate("Подготовка объекта для сохранения")
                repoCreate("Создание объявления в БД")
            }
        }
        operation("Получить издание", HmlrCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                validateId("Проверка id")
            }
            chain {
                title = "Логика чтения"
                repoRead("Чтение объявления из БД")
                worker {
                    title = "Подготовка ответа для Read"
                    on { state == HmlrState.RUNNING }
                    handle { edRepoDone = edRepoRead }
                }
            }
        }
        operation("Изменить издание", HmlrCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadTitle("Имитация ошибки валидации названия")
                stubValidationBadAuthor("Имитация ошибки валидации автора")
                stubValidationBadIsbn("Имитация ошибки валидации ISBN")
                stubValidationBadYear("Имитация ошибки валидации года")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                validateTitle("Валидация названия")
                validateAuthor("Валидация автора")
                validateIsbn("Валидация ISBN")
                validateYear("Валидация года")
                validateId("Проверка id")
                validateLock("Проверка lock")
            }
            chain {
                title = "Логика сохранения"
                repoRead("Чтение объявления из БД")
                checkLock("Проверяем консистентность по оптимистичной блокировке")
                repoPrepareUpdate("Подготовка объекта для обновления")
                repoUpdate("Обновление объявления в БД")
            }
        }
        operation("Удалить издание", HmlrCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubCanNotDelete("Имитация ошибки удаления")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                validateId("Проверка id")
                validateLock("Проверка lock")
            }
            chain {
                title = "Логика удаления"
                repoRead("Чтение объявления из БД")
                checkLock("Проверяем консистентность по оптимистичной блокировке")
                repoPrepareDelete("Подготовка объекта для удаления")
                repoDelete("Удаление объявления из БД")
            }
        }
        operation("Поиск изданий", HmlrCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadSearchString("Имитация ошибки валидации поисковой строки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                validateSearchStringLength("Валидация длины строки поиска в фильтре")
            }
            chain {
                title = "Логика поиска"
                repoSearch("Поиск объявления в БД по фильтру")
                prepareResult("Подготовка ответа")
            }
        }
        prepareResult("Подготовка ответа")
    }.build()
}
