<#assign pageTitle = "Отзывы студентов" />
<#assign extraStyles = ["chat.css"] />
<#assign extraScripts = ["chat.js"] />
<#include "fragments/header.ftl" />

<section class="section">
    <div class="wrap center">
        <h1>Отзывы студентов</h1>
        <p>Выберите курс, чтобы прочитать отзывы и оставить свой комментарий</p>

        <div class="course-grid">
            <div class="course-card" data-course="basic">
                <div class="emoji">🗣️</div>
                <h3>Разговорный — Базовый</h3>
                <p>Отзывы о базовом курсе разговорного английского</p>
            </div>

            <div class="course-card" data-course="advanced">
                <div class="emoji">💬</div>
                <h3>Разговорный — Продвинутый</h3>
                <p>Отзывы о продвинутом курсе разговорного английского</p>
            </div>

            <div class="course-card" data-course="pilots">
                <div class="emoji">✈️</div>
                <h3>Английский для пилотов</h3>
                <p>Отзывы о курсе авиационного английского для пилотов</p>
            </div>

            <div class="course-card" data-course="dispatchers">
                <div class="emoji">🛫</div>
                <h3>Английский для диспетчеров</h3>
                <p>Отзывы о курсе английского для диспетчеров</p>
            </div>
        </div>

        <div id="chat-section" class="hidden chat-box">
            <div class="chat-header">
                <h2 id="chat-title"></h2>
                <button class="close-chat" type="button" aria-label="Закрыть отзывы">&times;</button>
            </div>

            <div id="chat-filter" class="chat-filter hidden">
                <label for="userFilter">Показывать отзывы от:</label>
                <select id="userFilter">
                    <option value="ALL">Все участники</option>
                </select>
            </div>

            <div id="messages" class="messages-box"></div>

            <form id="chatForm" class="chat-input">
                <textarea id="chatInput" placeholder="Напишите ваш отзыв о курсе..." required></textarea>
                <button type="submit">Отправить</button>
            </form>
        </div>
    </div>
</section>

<#include "fragments/footer.ftl" />


