document.addEventListener("DOMContentLoaded", () => {
    // Find any <select> whose id ends with "Comparison"
    const comparisonSelects = document.querySelectorAll("select[id$='Comparison']");

    comparisonSelects.forEach(select => {
        const field = select.id.replace("Comparison", "");

        const maxInput = document.getElementById(field + "Max");
        const mainInput = document.getElementById(field);

        // If the matching elements don't exist → skip
        if (!select || !maxInput || !mainInput) {
            return;
        }

        function toggle() {
            if (select.value === "between") {
                maxInput.style.display = "inline-block";

                if (field === "price") {
                    mainInput.placeholder = "Start Price";
                    maxInput.placeholder = "Max Price";
                }
                if (field === "year") {
                    mainInput.placeholder = "From Year";
                    maxInput.placeholder = "To Year";
                }
            } else {
                maxInput.style.display = "none";

                if (field === "price") {
                    mainInput.placeholder = "Suggested Price";
                }
                if (field === "year") {
                    mainInput.placeholder = "Manufacturing Year";
                }
            }
        }

        // Bind behavior + run once
        select.addEventListener("change", toggle);
        toggle();
    });

    // Check if mandatory options are selected
    const brand = document.getElementById('brand');
    const vehicleType = document.getElementById('vehicleType');
    const model = document.getElementById('model');
    const searchBtn = document.getElementById('searchBtn');

    function isFormValid() {
        return (
            brand.value !== '' &&
            vehicleType.value !== '' &&
            model.value !== ''
        );
    }

    function updateButtonState() {
        if (isFormValid()) {
            searchBtn.classList.remove('disabled-btn');
        } else {
            searchBtn.classList.add('disabled-btn');
        }
    }
    
    searchBtn.addEventListener('click', (e) => {
        if (!isFormValid()) {
            e.preventDefault();
            alert("Please select at least Brand, Vehicle type and Model")
        }
    });

    [brand, vehicleType, model].forEach(select => {
        select.addEventListener('change', updateButtonState);
    });

// Initial state
    updateButtonState();
});
