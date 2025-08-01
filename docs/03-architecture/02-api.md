# API

## Описание сущности BookEdition (карточка издания)

1. Id - Уникальный идентификатор в системе
2. Title - Название
3. ISBN - Код ISBN
4. Annotation - аннотация
5. Year - Год издания

## Описание сущности CustomBook (личная карточка книги)

1. Id - Уникальный идентификатор в системе
2. Place - Местоположение
3. Comment - Комментарий владельца

## Функции (эндпониты)

1. CRUDS (create, read, update, delete, search) для объявлений (BookEdition)


1. Info
    1. Title
    2. Description
    3. Owner
    4. Visibility
2. DealSide: Demand/Proposal
3. ProductType (гаечный ключ, ...)
4. ProductId - идентификатор модели товара