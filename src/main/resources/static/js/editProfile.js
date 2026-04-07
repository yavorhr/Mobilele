document.addEventListener("DOMContentLoaded", () => {
    const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
    const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

    const editBtn = document.getElementById("edit-btn");
    const saveBtn = document.getElementById("save-btn");
    const cancelBtn = document.getElementById("cancel-btn");
    const errorBox = document.getElementById("phoneNumber-error");

    const editableFields = ["firstName", "lastName", "phoneNumber"];

    const originalData = {
        firstName: document.getElementById("firstName-input").value,
        lastName: document.getElementById("lastName-input").value,
        phoneNumber: document.getElementById("phoneNumber-input").value
    };

    function toggleEditMode(editing) {
        editableFields.forEach(field => {
            const span = document.getElementById(`${field}-display`);
            const input = document.getElementById(`${field}-input`);

            span.classList.toggle("hidden", editing);
            input.classList.toggle("hidden", !editing);
        });

        editBtn.classList.toggle("hidden", editing);
        saveBtn.classList.toggle("hidden", !editing);
        cancelBtn.classList.toggle("hidden", !editing);

        updateSaveButtonState();
    }

    function hasChanges() {
        return editableFields.some(field => {
            const currentValue = document.getElementById(`${field}-input`).value;
            return currentValue !== originalData[field];
        });
    }

    function updateSaveButtonState() {
        saveBtn.disabled = !hasChanges();
    }

    editableFields.forEach(field => {
        const input = document.getElementById(`${field}-input`);

        input.addEventListener("input", () => {
            const errorElement = document.getElementById(`${field}-error`);
            if (errorElement) {
                errorElement.textContent = "";
                errorElement.classList.add("hidden");
            }

            updateSaveButtonState();
        });
    });

    editBtn.addEventListener("click", () => toggleEditMode(true));

    cancelBtn.addEventListener("click", () => {
        editableFields.forEach(field => {
            document.getElementById(`${field}-input`).value = originalData[field];
        });

        errorBox.classList.add("hidden");
        toggleEditMode(false);
    });

    saveBtn.addEventListener("click", async () => {
        const data = {
            firstName: document.getElementById("firstName-input").value,
            lastName: document.getElementById("lastName-input").value,
            phoneNumber: document.getElementById("phoneNumber-input").value
        };

        try {
            const response = await fetch("/users/profile", {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                    [csrfHeaderName]: csrfHeaderValue
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                errorBox.classList.add("hidden");

                const updated = await response.json();

                for (let key in updated) {
                    const span = document.getElementById(`${key}-display`);
                    const input = document.getElementById(`${key}-input`);

                    if (span) span.textContent = updated[key];
                    if (input) input.value = updated[key];
                }

                Object.assign(originalData, data);

                toggleEditMode(false);

            } else if (response.status === 400 || response.status === 409) {

                document.querySelectorAll(".error-msg").forEach(e => {
                    e.textContent = "";
                    e.classList.add("hidden");
                });

                const errors = await response.json();
                console.log("ERRORS:", errors);

                Object.keys(errors).forEach(field => {
                    const errorElement = document.getElementById(`${field}-error`);
                    if (errorElement) {
                        errorElement.textContent = errors[field];
                        errorElement.classList.remove("hidden");
                    }
                });
            } else {
                alert("Failed to update profile.");
            }
        } catch (err) {
            console.error("Error updating profile:", err);
        }
    });

    updateSaveButtonState();
});