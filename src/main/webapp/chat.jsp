<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Отзывы студентов</title>
    <link rel="stylesheet" href="chat.css">
</head>
<body>

<h1>Отзывы студентов</h1>
<p>Выберите курс, чтобы открыть чат и прочитать отзывы.</p>

<div class="course-select">
    <button class="course-btn" data-course="basic">🗣️ Разговорный — Базовый</button>
    <button class="course-btn" data-course="advanced">💬 Разговорный — Продвинутый</button>
    <button class="course-btn" data-course="pilots">✈️ Английский для пилотов</button>
    <button class="course-btn" data-course="dispatchers">🛫 Английский для диспетчеров</button>
</div>

<div id="chat-section" class="hidden chat-box">
    <h2 id="chat-title"></h2>

    <div id="messages" class="messages-box"></div>

    <form id="chatForm" class="chat-input">
        <textarea id="chatInput" placeholder="Напишите сообщение..." required></textarea>
        <button type="submit">Отправить</button>
    </form>
</div>

<a href="index.jsp" class="back-link">← На главную</a>

<script>
    const messagesDiv = document.getElementById("messages");
    const chatSection = document.getElementById("chat-section");
    const chatTitle = document.getElementById("chat-title");
    const chatForm = document.getElementById("chatForm");
    const chatInput = document.getElementById("chatInput");

    let selectedCourse = null;
    let allMessages = [];

    // Выбор курса
    document.querySelectorAll(".course-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            selectedCourse = btn.dataset.course;
            chatTitle.textContent = btn.textContent;
            chatSection.classList.remove("hidden");
            loadMessages();
        });
    });

    // Загрузка сообщений
    async function loadMessages() {
        try {
            const resp = await fetch("chat/list");
            if (!resp.ok) throw new Error("Ошибка загрузки");
            const data = await resp.json();

            // ✅ фильтруем по курсу
            allMessages = data.filter(m => m.course === selectedCourse);
            renderMessages();
        } catch (e) {
            messagesDiv.innerHTML = `<p class="error">Не удалось загрузить сообщения.</p>`;
            console.error("Ошибка:", e);
        }
    }

    // Отображение
    function renderMessages() {
        if (!allMessages.length) {
            messagesDiv.innerHTML = `<p class="no-messages">Пока нет сообщений. Будьте первым!</p>`;
            return;
        }

        messagesDiv.innerHTML = allMessages.map(m => {
            const date = new Date(m.time).toLocaleString();
            return `
                <div class="message">
                    <div class="author">${m.name}</div>
                    <div class="text">${m.text}</div>
                    <div class="time">${date}</div>
                </div>
            `;
        }).join("");

        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    }

    // Отправка сообщения
    chatForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const text = chatInput.value.trim();
        if (!text || !selectedCourse) return;

        try {
            const resp = await fetch("chat/send", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: new URLSearchParams({ text, course: selectedCourse })
            });

            if (resp.ok) {
                chatInput.value = "";
                await loadMessages();
            } else if (resp.status === 401) {
                alert("Чтобы писать — войдите в систему.");
            } else {
                alert("Ошибка при отправке");
            }
        } catch (err) {
            console.error(err);
            alert("Ошибка сети");
        }
    });

    // Автообновление
    setInterval(() => {
        if (!chatSection.classList.contains("hidden")) {
            loadMessages();
        }
    }, 8000);
</script>

</body>
</html>
