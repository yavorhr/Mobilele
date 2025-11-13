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
});