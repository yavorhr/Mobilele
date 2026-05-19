document.addEventListener("DOMContentLoaded", () => {
    const fileInput = document.getElementById("picture-upload");
    const form = document.getElementById("picture-upload-form");
    const uploadSuccess = document.getElementById("uploadSuccess");
    const uploadFail = document.getElementById("uploadFail");

    if (!fileInput || !form) return;

    const allowedTypes = [
        "image/jpeg",
        "image/png",
        "image/jpg",
        "image/gif",
        "image/webp"
    ];

    const MAX_SIZE = 5 * 1024 * 1024; // 5MB

    fileInput.addEventListener("change", () => {
        const files = fileInput.files;
        if (!files || files.length === 0) return;

        hideMessages();

        for (const file of files) {

            // TYPE VALIDATION
            if (!allowedTypes.includes(file.type)) {
                show(uploadFail, "Invalid image format!");
                fileInput.value = "";
                return;
            }

            // SIZE VALIDATION
            if (file.size > MAX_SIZE) {
                show(uploadFail, `${file.name} exceeds 5MB limit!`);
                fileInput.value = "";
                return;
            }
        }

        show(uploadSuccess);

        setTimeout(() => {
            form.submit();
        }, 400);
    });

    function show(el, message) {

        if (message) {
            el.textContent = message;
        }

        el.classList.add("show");

        setTimeout(() => {
            el.classList.remove("show");
        }, 2000);
    }

    function hideMessages() {
        uploadSuccess.classList.remove("show");
        uploadFail.classList.remove("show");
    }
});
