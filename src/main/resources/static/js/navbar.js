document.addEventListener("DOMContentLoaded", () => {
    const csrfHeader = document.querySelector("[name=_csrf_header]").content;
    const csrfToken = document.querySelector("[name=_csrf]").content;

    // Delete DOM
    const deleteLink = document.getElementById("deleteAccountLink");
    const deleteForm = document.getElementById("deleteForm");

    // Submit form DOM
    const modal = document.getElementById("feedbackModal");
    const openBtn = document.getElementById("feedbackBtn");
    const closeBtn = document.getElementById("closeFeedback");
    const stars = document.querySelectorAll(".star");
    const messageBox = document.getElementById("feedbackMessage");
    const feedbackForm = document.getElementById("feedbackForm");

    // 1. Submit delete form
    if (deleteLink && deleteForm) {
        deleteLink.addEventListener("click", e => {
            e.preventDefault();
            if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
                deleteForm.submit();
            }
        });
    }

    // 2. Submit feedback form
    let selectedRating = 0;

    // Open modal
    openBtn?.addEventListener("click", () => {
        modal.style.display = "flex";
    });

    // Close modal (X or outside click)
    closeBtn.addEventListener("click", () => modal.style.display = "none");

    window.addEventListener("click", e => {
        if (e.target === modal) modal.style.display = "none";
    });

    // Select stars
    stars.forEach(star => {
        star.addEventListener("click", e => {
            selectedRating = parseInt(e.target.dataset.value);
            stars.forEach(s => {
                s.classList.toggle("filled", parseInt(s.dataset.value) <= selectedRating);
            });
        });
    });

    // Submit feedback
    feedbackForm.addEventListener("submit", async e => {
        e.preventDefault();

        const comment = document.getElementById("feedbackComment").value.trim();

        if (!selectedRating) {
            messageBox.textContent = "⭐ Please select a rating!";
            messageBox.style.color = "#ff6b6b";
            return;
        }

        const res = await fetch("/users/submit-feedback", {
            method: "POST",
            headers: {
                [csrfHeader]: csrfToken,
                "Content-Type": "application/x-www-form-urlencoded"
            },

            body: new URLSearchParams({
                rating: String(selectedRating),
                comment: comment
            })
        });
        const data = await res.json();
        messageBox.textContent = data.message;
        messageBox.style.color = data.success ? "#4CAF50" : "#ff6b6b";

        if (data.success) {
            setTimeout(() => {
                modal.style.display = "none";
                messageBox.textContent = "";
                document.getElementById("feedbackComment").value = "";
                stars.forEach(s => s.classList.remove("filled"));
            }, 1800);
        }
    });
});

