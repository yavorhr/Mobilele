document.addEventListener('DOMContentLoaded', () => {
    const brandEl = document.getElementById('brand');
    const modelSelect = document.getElementById('model');
    const vehicleTypeEl = document.getElementById('vehicleType');

    if (!brandEl || !modelSelect || !vehicleTypeEl) return;

    brandEl.addEventListener('change', () => {
        const brand = brandEl.value;
        const vehicleType = vehicleTypeEl.value;

        modelSelect.innerHTML = '<option value="">-- Select a model --</option>';

        if (brand && vehicleType) {
            fetch(`/models?vehicleType=${encodeURIComponent(vehicleType)}&brand=${encodeURIComponent(brand)}`)
                .then(response => {
                    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                    return response.json();
                })
                .then(data => {
                    modelSelect.innerHTML = '<option value="">-- Select a model --</option>';

                    if (Array.isArray(data) && data.length > 0) {
                        data.forEach(model => {
                            const option = document.createElement('option');
                            option.value = model;
                            option.textContent = model;
                            modelSelect.appendChild(option);
                        });
                    } else {
                        const option = document.createElement('option');
                        option.value = "";
                        option.textContent = "-- No models available --";
                        modelSelect.appendChild(option);
                    }
                })
                .catch(error => {
                    console.error('Error fetching models:', error);
                });
        }
    });

    vehicleTypeEl.addEventListener('change', () => {
        brandEl.value = "";
        modelSelect.innerHTML = '<option value="">-- Select a model --</option>';
    });
});
