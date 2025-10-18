document.addEventListener("DOMContentLoaded", function () {
    let thumbnails = Array.from(document.querySelectorAll(".thumbnail"));
    const mainImage = document.getElementById("modalImage");
    const deleteBtn = document.getElementById("deleteBtn");

    const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
    const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

    let currentIndex = 0;

    // Thumbnails image clicks
    thumbnails.forEach((thumb, index) => {
        thumb.addEventListener("click", () => {
            currentIndex = index;
            updateMainImage();
        });
    });

    // Next / Prev
    document.getElementById("nextBtn").addEventListener("click", () => {
        currentIndex = (currentIndex + 1) % thumbnails.length;
        updateMainImage();
    });

    document.getElementById("prevBtn").addEventListener("click", () => {
        currentIndex = (currentIndex - 1 + thumbnails.length) % thumbnails.length;
        updateMainImage();
    });

    // Update main image
    function updateMainImage() {
        if (thumbnails.length === 0) {
            mainImage.src = "";
            mainImage.removeAttribute("data-public-id");
            deleteBtn.disabled = true;
            return;
        }

        const thumb = thumbnails[currentIndex];
        mainImage.src = thumb.src;
        mainImage.setAttribute("data-public-id", thumb.dataset.publicId);
        deleteBtn.disabled = false;
    }

    // Delete current pic
    deleteBtn.addEventListener("click", async () => {
        const publicId = mainImage.dataset.publicId;
        if (!publicId) return;

        if (!confirm("Are you sure you want to delete this picture?")) return;

        try {
            const response = await fetch(`/pictures?public_id=${encodeURIComponent(publicId)}`, {
                method: "DELETE",
                headers: {
                    [csrfHeaderName]: csrfHeaderValue,
                }
            })

            if (!response.ok) {
                const errorText = await response.text();
                alert("Failed to delete picture: " + errorText);
                return;
            }

            // Remove thumbnail after backend deletion confirmation
            const removed = thumbnails.splice(currentIndex, 1)[0];
            removed.remove();

            // Update currentIndex
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
});