document.addEventListener("DOMContentLoaded", () => {
    const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
    const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

    const editBtn = document.getElementById("edit-btn");
    const saveBtn = document.getElementById("save-btn");
    const cancelBtn = document.getElementById("cancel-btn");

    const editableFields = ["firstName", "lastName", "phoneNumber"];

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
    }

    editBtn.addEventListener("click", () => toggleEditMode(true));
    cancelBtn.addEventListener("click", () => toggleEditMode(false));

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
                const updated = await response.json();
                // Update the displayed values
                for (let key in updated) {
                    const span = document.getElementById(`${key}-display`);
                    if (span) span.textContent = updated[key];
                }
                toggleEditMode(false);
            } else {
                alert("Failed to update profile. Try again.");
            }
        } catch (err) {
            console.error("Error updating profile:", err);
        }
    });
});