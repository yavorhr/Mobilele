document.addEventListener('DOMContentLoaded', () => {
    const reserveBtn = document.querySelector('.reserve-btn');
    const mainContainer = document.querySelector('.main-image-container');

    if (!reserveBtn || !mainContainer) return;

    const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
    const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

    reserveBtn.addEventListener('click', async () => {
        const offerId = reserveBtn.dataset.offerId;
        const currentState = reserveBtn.dataset.reserved === 'true';

        try {
            const response = await fetch(`/offers/${offerId}/toggle-reservation`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfHeaderValue,
                },
                body: JSON.stringify({ reserved: !currentState }),
            });

            if (!response.ok) throw new Error('Request failed');
            const data = await response.json(); // expects { reserved: true/false }

            reserveBtn.dataset.reserved = data.reserved;
            reserveBtn.classList.toggle('reserved', data.reserved);

            const icon = reserveBtn.querySelector('.icon');
            const text = reserveBtn.querySelector('.text');

            if (data.reserved) {
                icon.textContent = '🔒';
                text.textContent = 'Cancel reservation';
                mainContainer.classList.add('reserved');

                if (!mainContainer.querySelector('.reserved-banner')) {
                    const banner = document.createElement('div');
                    banner.className = 'reserved-banner';
                    banner.innerHTML = '<span>Reserved</span>';
                    mainContainer.appendChild(banner);
                }
            } else {
                icon.textContent = '🟢';
                text.textContent = 'Mark as reserved';
                mainContainer.classList.remove('reserved');

                const banner = mainContainer.querySelector('.reserved-banner');
                if (banner) banner.remove();
            }
        } catch (err) {
            console.error('Error toggling reservation:', err);
            alert('Could not update reservation status. Try again.');
        }
    });
});