document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('a[href^="#"]').forEach(link => {
        link.addEventListener('click', (event) => {
            const targetSelector = link.getAttribute('href');
            if (!targetSelector || targetSelector === '#') {
                return;
            }
            const target = document.querySelector(targetSelector);
            if (target) {
                event.preventDefault();
                target.scrollIntoView({ behavior: 'smooth' });
            }
        });
    });

    const messageContainer = document.getElementById('messageContainer');
    if (messageContainer) {
        const closeButton = messageContainer.querySelector('.message-close');
        closeButton?.addEventListener('click', () => {
            messageContainer.style.display = 'none';
        });

        setTimeout(() => {
            messageContainer.style.display = 'none';
        }, 5000);
    }
});

