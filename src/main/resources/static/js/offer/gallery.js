document.addEventListener("DOMContentLoaded", () => {
    const mainImage = document.getElementById("modalImage");
    const thumbnails = document.querySelectorAll(".thumbnail");
    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");

    let currentIndex = 0;

    function showImage(index) {
        const selectedThumb = thumbnails[index];
        if (!selectedThumb) return;
        mainImage.src = selectedThumb.src;
        currentIndex = index;
    }

    thumbnails.forEach((thumb, index) => {
        thumb.addEventListener("click", () => showImage(index));
    });

    prevBtn.addEventListener("click", () => {
        currentIndex = (currentIndex - 1 + thumbnails.length) % thumbnails.length;
        showImage(currentIndex);
    });

    nextBtn.addEventListener("click", () => {
        currentIndex = (currentIndex + 1) % thumbnails.length;
        showImage(currentIndex);
    });

    // Initialize
    showImage(0);
});
