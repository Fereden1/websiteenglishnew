document.addEventListener('DOMContentLoaded', () => {
    const deleteLink = document.querySelector('.js-delete-account');
    deleteLink?.addEventListener('click', (event) => {
        const confirmed = confirm('Удалить аккаунт?');
        if (!confirmed) {
            event.preventDefault();
        }
    });
});

