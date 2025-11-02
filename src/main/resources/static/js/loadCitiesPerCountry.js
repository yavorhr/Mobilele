document.addEventListener('DOMContentLoaded', () => {
    const countryEl = document.getElementById('country');
    const citySelect = document.getElementById('city');

    if (!countryEl || !citySelect) return;

    const previousCity = citySelect.getAttribute('data-selected-city');

    const populateCities = (country) => {
        citySelect.innerHTML = '';
        const defaultOption = document.createElement('option');
        defaultOption.textContent = "-- Select city --";
        defaultOption.value = "";
        citySelect.appendChild(defaultOption);

        if (!country) return;

        fetch(`/locations/cities?country=${encodeURIComponent(country)}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(city => {
                    const option = document.createElement('option');
                    option.value = city;
                    option.textContent = city;

                    if (previousCity && previousCity.trim().toLowerCase() === city.trim().toLowerCase()) {
                        option.selected = true;
                    }

                    citySelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching cities:', error));
    };

    if (countryEl.value) {
        populateCities(countryEl.value);
    }

    countryEl.addEventListener('change', () => {
        populateCities(countryEl.value);
    });
});
