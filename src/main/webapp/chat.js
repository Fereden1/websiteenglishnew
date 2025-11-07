document.addEventListener('DOMContentLoaded', () => {
    const messagesDiv = document.getElementById('messages');
    const chatSection = document.getElementById('chat-section');
    const chatTitle = document.getElementById('chat-title');
    const chatForm = document.getElementById('chatForm');
    const chatInput = document.getElementById('chatInput');
    const userFilterSelect = document.getElementById('userFilter');
    const chatFilter = document.getElementById('chat-filter');
    const closeChatButton = document.querySelector('.close-chat');

    let selectedCourse = null;
    let allMessages = [];
    let refreshInterval = null;
    let selectedUserFilter = 'ALL';

    const resetChatState = () => {
        selectedCourse = null;
        allMessages = [];
        selectedUserFilter = 'ALL';
        if (userFilterSelect) {
            userFilterSelect.value = 'ALL';
        }
        if (chatFilter) {
            chatFilter.classList.add('hidden');
        }
        if (refreshInterval) {
            clearInterval(refreshInterval);
            refreshInterval = null;
        }
    };

    const closeChatSection = () => {
        chatSection?.classList.add('hidden');
        resetChatState();
    };

    const escapeHtml = (text) => {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    };

    const updateUserFilterOptions = () => {
        if (!userFilterSelect || !chatFilter) {
            return;
        }

        const names = Array.from(new Set(allMessages.map((message) => {
            if (message.name && message.name.trim()) {
                return message.name.trim();
            }
            return 'Аноним';
        })));

        names.sort((a, b) => a.localeCompare(b, 'ru'));

        const previousValue = selectedUserFilter;

        userFilterSelect.innerHTML = '<option value="ALL">Все участники</option>';
        names.forEach((name) => {
            const option = document.createElement('option');
            option.value = name;
            option.textContent = name;
            userFilterSelect.appendChild(option);
        });

        if (previousValue !== 'ALL' && names.includes(previousValue)) {
            selectedUserFilter = previousValue;
        } else {
            selectedUserFilter = 'ALL';
        }

        userFilterSelect.value = selectedUserFilter;

        if (names.length > 0) {
            chatFilter.classList.remove('hidden');
        } else {
            chatFilter.classList.add('hidden');
        }
    };

    const renderMessages = () => {
        if (!messagesDiv) {
            return;
        }

        if (!allMessages.length) {
            messagesDiv.innerHTML = '<p class="no-messages">Пока нет отзывов по этому курсу. Будьте первым, кто оставит отзыв!</p>';
            return;
        }

        const filteredMessages = selectedUserFilter === 'ALL'
            ? allMessages
            : allMessages.filter((message) => {
                const name = message.name && message.name.trim() ? message.name.trim() : 'Аноним';
                return name === selectedUserFilter;
            });

        if (!filteredMessages.length) {
            messagesDiv.innerHTML = '<p class="no-messages">Для выбранного участника пока нет отзывов.</p>';
            return;
        }

        messagesDiv.innerHTML = filteredMessages.map((message) => {
            const date = new Date(message.time).toLocaleString('ru-RU', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit'
            });
            const name = escapeHtml(message.name || 'Аноним');
            const text = escapeHtml(message.text);
            return `
                <div class="message">
                    <div class="message-header">
                        <div class="author">${name}</div>
                        <div class="time">${date}</div>
                    </div>
                    <div class="text">${text}</div>
                </div>
            `;
        }).join('');

        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    };

    const loadMessages = async () => {
        if (!selectedCourse) {
            return;
        }

        try {
            const encodedCourse = encodeURIComponent(selectedCourse);
            const response = await fetch(`chat/list?course=${encodedCourse}`);
            if (!response.ok) {
                if (response.status === 400) {
                    allMessages = [];
                    updateUserFilterOptions();
                    renderMessages();
                    return;
                }
                throw new Error('Ошибка загрузки');
            }

            const data = await response.json();
            allMessages = Array.isArray(data) ? data : [];
            updateUserFilterOptions();
            renderMessages();
        } catch (error) {
            console.error('Ошибка загрузки:', error);
            if (messagesDiv) {
                messagesDiv.innerHTML = '<p class="error">Не удалось загрузить отзывы. Попробуйте обновить страницу.</p>';
            }
        }
    };

    const startAutoRefresh = () => {
        if (refreshInterval) {
            clearInterval(refreshInterval);
        }
        refreshInterval = setInterval(() => {
            if (selectedCourse && chatSection && !chatSection.classList.contains('hidden')) {
                loadMessages();
            }
        }, 5000);
    };

    document.querySelectorAll('.course-card').forEach(card => {
        card.addEventListener('click', () => {
            selectedCourse = card.dataset.course;
            const courseName = card.querySelector('h3')?.textContent ?? '';
            if (chatTitle) {
                chatTitle.textContent = courseName;
            }
            if (chatSection) {
                chatSection.classList.remove('hidden');
                chatSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
            loadMessages();
            startAutoRefresh();
        });
    });

    closeChatButton?.addEventListener('click', closeChatSection);

    window.addEventListener('beforeunload', () => {
        if (refreshInterval) {
            clearInterval(refreshInterval);
        }
    });

    userFilterSelect?.addEventListener('change', () => {
        selectedUserFilter = userFilterSelect.value;
        renderMessages();
    });

    chatForm?.addEventListener('submit', async (event) => {
        event.preventDefault();
        const text = chatInput?.value.trim();
        if (!text || !selectedCourse) {
            return;
        }

        try {
            const params = new URLSearchParams();
            params.append('text', text);
            params.append('course', selectedCourse);

            const response = await fetch('chat/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                body: params
            });

            if (response.ok) {
                if (chatInput) {
                    chatInput.value = '';
                }
                await loadMessages();
            } else if (response.status === 401) {
                alert('Чтобы оставить отзыв, войдите в систему.');
                window.location.href = 'login';
            } else {
                const errorText = await response.text();
                alert('Ошибка при отправке отзыва: ' + errorText);
            }
        } catch (error) {
            console.error(error);
            alert('Ошибка сети. Проверьте подключение к интернету.');
        }
    });
});

