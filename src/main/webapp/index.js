document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('applicationModal');
    const closeButton = modal ? modal.querySelector('.close') : null;
    const form = document.getElementById('applicationForm');
    const courseCards = document.querySelectorAll('.course-card');
    const modalTitle = document.getElementById('modalTitle');
    const courseField = document.getElementById('selectedCourseId');
    const phoneField = document.getElementById('userPhone');

    const openModal = (courseKey, courseName) => {
        if (courseField) {
            courseField.value = courseKey;
        }
        if (modalTitle) {
            modalTitle.textContent = `Заявка на курс: ${courseName}`;
        }
        modal?.classList.remove('hidden');
    };

    const closeModal = () => {
        modal?.classList.add('hidden');
    };

    courseCards.forEach(card => {
        card.addEventListener('click', () => {
            const courseKey = card.dataset.course;
            const courseName = card.querySelector('h3')?.textContent ?? '';
            openModal(courseKey, courseName);
        });
    });

    closeButton?.addEventListener('click', closeModal);

    modal?.addEventListener('click', (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });

    form?.addEventListener('submit', (event) => {
        const phone = phoneField?.value.trim() ?? '';
        if (!/^\+7\d{10}$/.test(phone)) {
            event.preventDefault();
            alert('Телефон должен быть в формате +7XXXXXXXXXX');
        }
    });
});

