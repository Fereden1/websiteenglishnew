<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Отзывы студентов" scope="request" />
<jsp:include page="/WEB-INF/fragments/header.jsp" />
<link rel="stylesheet" href="chat.css">

<!-- ===== MAIN CONTENT ===== -->
<section class="section">
    <div class="wrap center">
        <h1>Отзывы студентов</h1>
        <p>Выберите курс, чтобы прочитать отзывы и оставить свой комментарий</p>

        <!-- Курсы в виде карточек -->
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

        <!-- Секция с комментариями -->
        <div id="chat-section" class="hidden chat-box">
            <div class="chat-header">
                <h2 id="chat-title"></h2>
                <button class="close-chat" onclick="closeChatSection()">&times;</button>
            </div>

            <div id="messages" class="messages-box"></div>

            <form id="chatForm" class="chat-input">
                <textarea id="chatInput" placeholder="Напишите ваш отзыв о курсе..." required></textarea>
                <button type="submit">Отправить</button>
            </form>
        </div>
    </div>
</section>

<script>
    const messagesDiv = document.getElementById("messages");
    const chatSection = document.getElementById("chat-section");
    const chatTitle = document.getElementById("chat-title");
    const chatForm = document.getElementById("chatForm");
    const chatInput = document.getElementById("chatInput");

    let selectedCourse = null;
    let allMessages = [];
    let refreshInterval = null;

    // Маппинг курсов для отображения
    const courseNames = {
        'basic': '🗣️ Разговорный — Базовый',
        'advanced': '💬 Разговорный — Продвинутый',
        'pilots': '✈️ Английский для пилотов',
        'dispatchers': '🛫 Английский для диспетчеров'
    };

    // Выбор курса при клике на карточку
    document.querySelectorAll(".course-card").forEach(card => {
        card.addEventListener("click", () => {
            selectedCourse = card.dataset.course;
            const courseName = card.querySelector("h3").textContent;
            chatTitle.textContent = courseName;
            chatSection.classList.remove("hidden");

            // Прокрутка к секции с комментариями
            chatSection.scrollIntoView({ behavior: "smooth", block: "start" });

            // Запускаем загрузку и автообновление
            loadMessages();
            startAutoRefresh();
        });
    });

    // Закрытие секции с комментариями
    function closeChatSection() {
        chatSection.classList.add("hidden");
        selectedCourse = null;
        allMessages = [];
        if (refreshInterval) {
            clearInterval(refreshInterval);
            refreshInterval = null;
        }
    }

    // Загрузка сообщений
    async function loadMessages() {
        if (!selectedCourse) return;

        try {
            // Правильное кодирование параметра в URL
            const encodedCourse = encodeURIComponent(selectedCourse);
            const resp = await fetch('chat/list?course=' + encodedCourse);
            if (!resp.ok) {
                if (resp.status === 400) {
                    // Если нет комментариев, показываем пустое состояние
                    allMessages = [];
                    renderMessages();
                    return;
                }
                throw new Error("Ошибка загрузки");
            }
            const data = await resp.json();

            // Сохраняем все сообщения
            allMessages = Array.isArray(data) ? data : [];
            renderMessages();
        } catch (e) {
            console.error("Ошибка загрузки:", e);
            messagesDiv.innerHTML = '<p class="error">Не удалось загрузить отзывы. Попробуйте обновить страницу.</p>';
        }
    }

    // Отображение сообщений
    function renderMessages() {
        if (!allMessages || allMessages.length === 0) {
            messagesDiv.innerHTML = '<p class="no-messages">Пока нет отзывов по этому курсу. Будьте первым, кто оставит отзыв!</p>';
            return;
        }

        messagesDiv.innerHTML = allMessages.map(m => {
            const date = new Date(m.time).toLocaleString('ru-RU', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit'
            });
            const name = escapeHtml(m.name || 'Аноним');
            const text = escapeHtml(m.text);
            return '<div class="message">' +
                '<div class="message-header">' +
                '<div class="author">' + name + '</div>' +
                '<div class="time">' + date + '</div>' +
                '</div>' +
                '<div class="text">' + text + '</div>' +
                '</div>';
        }).join("");

        // Автоматическая прокрутка к последнему сообщению
        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    }

    // Экранирование HTML для безопасности
    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    // Отправка сообщения
    chatForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const text = chatInput.value.trim();
        if (!text || !selectedCourse) return;

        try {
            // Создаем URLSearchParams с правильной кодировкой UTF-8
            const params = new URLSearchParams();
            params.append('text', text);
            params.append('course', selectedCourse);

            const resp = await fetch("chat/send", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
                },
                body: params
            });

            if (resp.ok) {
                chatInput.value = "";
                // Загружаем обновленные сообщения
                await loadMessages();
            } else if (resp.status === 401) {
                alert("Чтобы оставить отзыв, войдите в систему.");
                window.location.href = 'login.jsp';
            } else {
                const errorText = await resp.text();
                alert("Ошибка при отправке отзыва: " + errorText);
            }
        } catch (err) {
            console.error(err);
            alert("Ошибка сети. Проверьте подключение к интернету.");
        }
    });

    // Автообновление каждые 5 секунд
    function startAutoRefresh() {
        if (refreshInterval) {
            clearInterval(refreshInterval);
        }
        refreshInterval = setInterval(() => {
            if (!chatSection.classList.contains("hidden") && selectedCourse) {
                loadMessages();
            }
        }, 5000);
    }

    // Остановка автообновления при закрытии страницы
    window.addEventListener("beforeunload", () => {
        if (refreshInterval) {
            clearInterval(refreshInterval);
        }
    });
</script>

<jsp:include page="/WEB-INF/fragments/footer.jsp" />
