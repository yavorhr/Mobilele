document.addEventListener("DOMContentLoaded", () => {
    const fileInput = document.getElementById("picture-upload");
    const form = document.getElementById("picture-upload-form");
    const uploadSuccess = document.getElementById("uploadSuccess");
    const uploadFail = document.getElementById("uploadFail");

    const allowedTypes = [
        "image/jpeg",
        "image/png",
        "image/jpg",
        "image/gif",
        "image/webp"
    ];

    fileInput.addEventListener("change", () => {
        const files = fileInput.files;
        if (!files || files.length === 0) return;

        hideMessages();

        for (const file of files) {
            if (!allowedTypes.includes(file.type)) {
                show(uploadFail);
                fileInput.value = "";
                return;
            }
        }

        show(uploadSuccess);

        setTimeout(() => {
            form.submit();
        }, 400);
    });

    function show(el) {
        el.classList.add("show");
        setTimeout(() => el.classList.remove("show"), 2000);
    }

    function hideMessages() {
        uploadSuccess.classList.remove("show");
        uploadFail.classList.remove("show");
    }
});
