document.addEventListener('DOMContentLoaded', () => {
    const countryEl = document.getElementById('country');
    const citySelect = document.getElementById('city');

    if (!countryEl || !citySelect) return;

    countryEl.addEventListener('change', () => {
        const country = countryEl.value;

        citySelect.innerHTML = '';

        const option = document.createElement('option');
        option.textContent = "-- Please select city --";
        citySelect.appendChild(option);

        if (country) {
            fetch(`/locations/cities?country=${encodeURIComponent(country)}`)
                .then(response => {
                    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                    return response.json();
                })
                .then(data => {
                    if (Array.isArray(data) && data.length > 0) {
                        data.forEach(city => {
                            const option = document.createElement('option');
                            option.value = city;
                            option.textContent = city;
                            citySelect.appendChild(option);
                        });
                    } else {
                        const option = document.createElement('option');
                        option.textContent = "-- No cities available --";
                        citySelect.appendChild(option);
                    }
                })
                .catch(error => console.error('Error fetching cities:', error));
        }
    });
});
