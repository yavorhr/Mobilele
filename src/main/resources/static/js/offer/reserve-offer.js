document.addEventListener('DOMContentLoaded', () => {
    const reserveBtn = document.querySelector('.reserve-btn');
    const mainContainer = document.querySelector('.main-image-container');

    if (!reserveBtn || !mainContainer) return;

    const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
    const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

    const textReserve = reserveBtn.dataset.textReserve;
    const textCancel = reserveBtn.dataset.textCancel;
    const textBanner = reserveBtn.dataset.textBanner;
    const textError = reserveBtn.dataset.textError;

    const icon = reserveBtn.querySelector('.icon');
    const text = reserveBtn.querySelector('.text');

    const initialReserved = reserveBtn.dataset.reserved === 'true';

    if (initialReserved) {
        icon.textContent = '🔒';
        text.textContent = textCancel;
        reserveBtn.classList.add('reserved');
        mainContainer.classList.add('reserved');
    } else {
        icon.textContent = '🟢';
        text.textContent = textReserve;
        reserveBtn.classList.remove('reserved');
        mainContainer.classList.remove('reserved');
    }

    reserveBtn.addEventListener('click', async () => {
        const offerId = reserveBtn.dataset.offerId;
        const currentState = reserveBtn.dataset.reserved === 'true';

        try {
            const response = await fetch(`/offers/${offerId}/toggle-reservation`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfHeaderValue,
                }
            });

            if (!response.ok) throw new Error('Request failed');

            const data = await response.json();

            reserveBtn.dataset.reserved = data.reserved;
            reserveBtn.classList.toggle('reserved', data.reserved);

            if (data.reserved) {
                icon.textContent = '🔒';
                text.textContent = textCancel;
                mainContainer.classList.add('reserved');

                if (!mainContainer.querySelector('.reserved-banner')) {
                    const banner = document.createElement('div');
                    banner.className = 'reserved-banner';
                    banner.innerHTML = `<span>${textBanner}</span>`;
                    mainContainer.appendChild(banner);
                }

            } else {
                icon.textContent = '🟢';
                text.textContent = textReserve;
                mainContainer.classList.remove('reserved');

                const banner = mainContainer.querySelector('.reserved-banner');
                if (banner) banner.remove();
            }

        } catch (err) {
            console.error('Error toggling reservation:', err);
            alert(textError);
        }
    });
});