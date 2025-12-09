document.addEventListener('DOMContentLoaded', function () {
    const deleteForm = document.getElementById('delete-offer-form');
    const soldInput = document.getElementById('soldOffer');

    if (!deleteForm || !soldInput) {
        return;
    }

    deleteForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const didSell = window.confirm('Did you manage to sell your car in Mobilele?');

        if (didSell) {
            soldInput.value = 'true';
        } else {
            soldInput.value = 'false';
        }

        deleteForm.submit();
    });
});
