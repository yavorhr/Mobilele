document.addEventListener("DOMContentLoaded", () => {
    const fileInput = document.getElementById("picture-upload");
    const form = document.getElementById("picture-upload-form");
    const uploadSuccess = document.getElementById("uploadSuccess");
    const uploadFail = document.getElementById("uploadFail");
    const allowedTypes = ["image/jpeg", "image/png", "image/jpg", "image/gif", "image/webp"];

    fileInput.addEventListener("change", () => {
        const files = fileInput.files;
        if (!files || files.length === 0) return;

        for (const file of files) {
            if (!allowedTypes.includes(file.type)) {
                showMessage(uploadFail, "Only image files are allowed");
                fileInput.value = "";
                return;
            }
        }

        setTimeout(() => form.submit(), 200);

        if (uploadSuccess) {
            uploadSuccess.style.opacity = 1;
            setTimeout(() => (uploadSuccess.style.opacity = 0), 2000);
        }
    });

    function showMessage(element, message) {
        if (!element) return;

        element.textContent = message;
        element.style.opacity = 1;
        setTimeout(() => (element.style.opacity = 0), 2000);
    }
});
