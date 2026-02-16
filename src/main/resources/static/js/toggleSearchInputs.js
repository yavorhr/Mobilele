document.addEventListener("DOMContentLoaded", () => {

    const comparisonSelects = document.querySelectorAll("select[id$='Comparison']");

    comparisonSelects.forEach(select => {
        const field = select.id.replace("Comparison", "");

        const maxInput = document.getElementById(field + "Max");
        const mainInput = document.getElementById(field);

        if (!maxInput || !mainInput) return;

        const phDefault = mainInput.dataset.phDefault || "";
        const phFrom = mainInput.dataset.phFrom || "";
        const phTo = mainInput.dataset.phTo || "";

        function toggle() {
            if (select.value === "between") {
                maxInput.style.display = "inline-block";
                mainInput.placeholder = phFrom;
                maxInput.placeholder = phTo;
            } else {
                maxInput.style.display = "none";
                mainInput.placeholder = phDefault;
            }
        }

        select.addEventListener("change", toggle);
        toggle();
    });

    const brand = document.getElementById("brand");
    const vehicleType = document.getElementById("vehicleType");
    const model = document.getElementById("model");
    const searchBtn = document.getElementById("searchBtn");

    if (!brand || !vehicleType || !model || !searchBtn) return;

    const validationMsg = searchBtn.dataset.validationMsg || "";

    function isFormValid() {
        return (
            brand.value !== "" &&
            vehicleType.value !== "" &&
            model.value !== ""
        );
    }

    function updateButtonState() {
        if (isFormValid()) {
            searchBtn.classList.remove("disabled-btn");
        } else {
            searchBtn.classList.add("disabled-btn");
        }
    }

    searchBtn.addEventListener("click", (e) => {
        if (!isFormValid()) {
            e.preventDefault();
            alert(validationMsg);
        }
    });

    [brand, vehicleType, model].forEach(select => {
        select.addEventListener("change", updateButtonState);
    });

    // Initial state
    updateButtonState();
});
