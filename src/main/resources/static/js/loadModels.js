function loadModels() {
    const brand = document.getElementById('brand').value.toLowerCase();
    const modelSelect = document.getElementById('model');

    modelSelect.innerHTML = '<option value="">-- Select a model --</option>';

    if (brand) {
        fetch(`/models?brand=${brand}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(model => {
                    const option = document.createElement('option');
                    option.value = model;
                    option.textContent = model;
                    modelSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching models:', error));
    }

    console.log("loadModels function called");
}