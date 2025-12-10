document.addEventListener('DOMContentLoaded', () => {

    const deleteBtn = document.getElementById('delete-offer-btn');
    const deleteForm = document.getElementById('delete-offer-form');
    const soldInput = document.getElementById('soldOffer');

    const overlay = document.getElementById('delete-confirm-box');
    const yesBtn = document.getElementById('confirm-yes');
    const noBtn = document.getElementById('confirm-no');
    const cancelBtn = document.getElementById('confirm-cancel');

    deleteBtn.addEventListener('click', () => {
        overlay.classList.remove('hidden');
    });

    cancelBtn.addEventListener('click', () => {
        overlay.classList.add('hidden');
    });

    noBtn.addEventListener('click', () => {
        soldInput.value = 'false';
        overlay.classList.add('hidden');
        deleteForm.submit();
    });

    yesBtn.addEventListener('click', () => {
        soldInput.value = 'true';
        overlay.classList.add('hidden');
        deleteForm.submit();
    });
});
