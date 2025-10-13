function loadModels() {
    const brand = document.getElementById('brand').value.toLowerCase();
    const modelSelect = document.getElementById('model');
    const vehicleType = document.getElementById('vehicleType')?.value.toLowerCase();
    const searchButton = document.getElementById('search-btn');

    if (!modelSelect) return;

    modelSelect.innerHTML = '<option value="">-- Select a model --</option>';
    if (searchButton) searchButton.disabled = true;

    if (brand && vehicleType) {
        fetch(`/models?vehicleType=${encodeURIComponent(vehicleType)}&brand=${encodeURIComponent(brand)}`)
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(data => {
                if (Array.isArray(data) && data.length > 0) {
                    data.forEach(model => {
                        const option = document.createElement('option');
                        option.value = model;
                        option.textContent = model;
                        modelSelect.appendChild(option);
                    });
                    if (searchButton) searchButton.disabled = false;
                } else {
                    const option = document.createElement('option');
                    option.value = "";
                    option.textContent = "-- No models available --";
                    modelSelect.appendChild(option);
                    if (searchButton) searchButton.disabled = true;
                }
            })
            .catch(error => {
                console.error('Error fetching models:', error);
                if (searchButton) searchButton.disabled = true;
            });
    } else {
        if (searchButton) searchButton.disabled = true;
    }
}

