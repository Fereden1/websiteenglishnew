-- Миграция: добавление поля course_identifier в таблицу applications
-- Это поле будет хранить конкретный идентификатор курса (например, "conversational-basic", "aviation-pilots")
-- вместо хранения его в поле message

-- Добавляем новое поле
ALTER TABLE applications 
ADD COLUMN IF NOT EXISTS course_identifier VARCHAR(50);

-- Для существующих записей можно оставить NULL или заполнить на основе course_type
-- Если нужно заполнить существующие записи, можно использовать:
-- UPDATE applications SET course_identifier = course_type WHERE course_identifier IS NULL;

-- Комментарий к полю
COMMENT ON COLUMN applications.course_identifier IS 'Конкретный идентификатор курса (например, conversational-basic, aviation-pilots)';

