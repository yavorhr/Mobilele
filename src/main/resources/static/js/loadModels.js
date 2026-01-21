document.addEventListener('DOMContentLoaded', () => {
    const brandEl = document.getElementById('brand');
    const modelSelect = document.getElementById('model');
    const vehicleTypeEl = document.getElementById('vehicleType');

    if (!brandEl || !modelSelect || !vehicleTypeEl) return;

    function clearModelSelect() {
        modelSelect.innerHTML = '';
    }

    function addSelectPlaceholder() {
        const placeholder = document.createElement('option');
        placeholder.value = '';
        placeholder.textContent = '-- Select a model --';
        modelSelect.appendChild(placeholder);
    }

    brandEl.addEventListener('change', () => {
        const brand = brandEl.value;
        const vehicleType = vehicleTypeEl.value;

        clearModelSelect();

        if (brand && vehicleType) {
            fetch(`/models?vehicleType=${encodeURIComponent(vehicleType)}&brand=${encodeURIComponent(brand)}`)
                .then(response => {
                    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                    return response.json();
                })
                .then(data => {
                    clearModelSelect();

                    if (Array.isArray(data) && data.length > 0) {
                        addSelectPlaceholder();

                        data.forEach(model => {
                            const option = document.createElement('option');
                            option.value = model;
                            option.textContent = model;
                            modelSelect.appendChild(option);
                        });
                    } else {
                        const option = document.createElement('option');
                        option.textContent = '-- No models available --';
                        option.disabled = true;
                        option.selected = true;
                        modelSelect.appendChild(option);
                    }
                })
                .catch(error => {
                    console.error('Error fetching models:', error);
                });
        }
    });

    vehicleTypeEl.addEventListener('change', () => {
        clearModelSelect();
        addSelectPlaceholder();
    });
});
