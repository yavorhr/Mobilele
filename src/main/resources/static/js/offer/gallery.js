document.addEventListener("DOMContentLoaded", () => {

    // DOM
    const mainImage = document.getElementById("modalImage");
    let thumbnails = Array.from(document.querySelectorAll(".thumbnail"));
    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");
    const deleteBtn = document.getElementById("deleteBtn");

    // CSRF
    const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
    const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

    // I. Main image
    let currentIndex = 0;

    // 1. Main image render
    function updateMainImage() {

        // No images left → reset UI
        if (thumbnails.length === 0) {
            mainImage.src = "";
            mainImage.removeAttribute("data-public-id");
            mainImage.removeAttribute("data-deletable");
            deleteBtn.style.display = "none";
            return;
        }

        const thumb = thumbnails[currentIndex];

        // Update main image
        mainImage.src = thumb.src;
        mainImage.dataset.publicId = thumb.dataset.publicId;
        mainImage.dataset.deletable = thumb.dataset.deletable;

        // Update delete button visibility
        updateDeleteButtonVisibility();
    }

    // 2. Thumbs click
    thumbnails.forEach((thumb, index) => {
        thumb.addEventListener("click", () => {
            currentIndex = index;
            updateMainImage();
        });
    });


    // 3. Next / Prev
    nextBtn.addEventListener("click", () => {
        currentIndex = (currentIndex + 1) % thumbnails.length;
        updateMainImage();
    });

    prevBtn.addEventListener("click", () => {
        currentIndex = (currentIndex - 1 + thumbnails.length) % thumbnails.length;
        updateMainImage();
    });

    // II. Delete current image
    deleteBtn.addEventListener("click", async () => {
        const publicId = mainImage.dataset.publicId;
        const offerId = deleteBtn.dataset.offerId;

        if (!publicId) return;
        if (!confirm("Are you sure you want to delete this picture?")) return;

        // 🔒 lock UI
        deleteBtn.classList.add("disabled");

        // auto unlock after 4s
        setTimeout(() => {
            deleteBtn.classList.remove("disabled");
        }, 6000);

        try {
            const response = await fetch(
                `/pictures?public_id=${encodeURIComponent(publicId)}&offer_id=${offerId}`,
                {
                    method: "DELETE",
                    headers: {
                        [csrfHeaderName]: csrfHeaderValue,
                    }
                }
            );

            if (!response.ok) {
                const errorText = await response.text();
                alert("Failed to delete picture: " + errorText);
                return;
            }

            // Remove thumbnail from DOM + state
            const removed = thumbnails.splice(currentIndex, 1)[0];
            removed.remove();

            if (currentIndex >= thumbnails.length) {
                currentIndex = Math.max(0, thumbnails.length - 1);
            }

            updateMainImage();
            alert("Picture deleted successfully!");

        } catch (e) {
            console.error(e);
            alert("Error deleting picture.");
        }
    });

    // Delete Button Visibility (removed for "Default" pictures)
    function updateDeleteButtonVisibility() {
        const canDelete = mainImage.dataset.deletable === "true";
        deleteBtn.style.display = canDelete ? "block" : "none";
    }

    // Init load of Main image
    updateMainImage();
});
