document.addEventListener("DOMContentLoaded", () => {
    // Delete DOM
    const deleteLink = document.getElementById("deleteAccountLink");
    const deleteForm = document.getElementById("deleteForm");

    // Feedback DOM
    const feedbackBtn = document.getElementById("feedbackBtn");
    const feedbackForm = document.getElementById("submitFeedback")
    const stars = document.querySelectorAll(".star")
    const csrfHeader = document.querySelector("[name=_csrf_header]").content;
    const csrfToken = document.querySelector("[name=_csrf]").content;

    // Submit delete form
    if (deleteLink && deleteForm) {
        deleteLink.addEventListener("click", e => {
            e.preventDefault();
            if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
                deleteForm.submit();
            }
        });
    }

    // Submit feedback form
    feedbackBtn.addEventListener("click", () => {
        document.getElementById("feedbackModal").style.display = "block";
    });


    stars.forEach(star => {
        star.addEventListener("click", e => {
            document.querySelectorAll(".star")
                .forEach(s => s.classList.remove("selected"));
            e.target.classList.add("selected");
        });
    });

    feedbackForm.addEventListener("click", async () => {
        const rating = document.querySelector(".star.selected")?.dataset.value;
        const comment = document.getElementById("feedbackComment").value;

        const res = await fetch("/users/submit-feedback", {
            method: "POST",
            headers: {[csrfHeader]: csrfToken},
            body: new URLSearchParams({rating, comment})
        });

        const data = await res.json();
        const msg = document.getElementById("feedbackMessage");
        msg.textContent = data.message;
        msg.style.color = data.success ? "green" : "red";
    });
});