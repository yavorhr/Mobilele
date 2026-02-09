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

    // 2. Submit feedback
    let selectedRating = 0;

    // 2.1 Open/ Close modal
    openBtn?.addEventListener("click", () => {
        modal.style.display = "flex";
    });

    closeBtn.addEventListener("click", () => modal.style.display = "none");

    window.addEventListener("click", e => {
        if (e.target === modal) modal.style.display = "none";
    });

    // 2.2 Select stars
    stars.forEach(star => {
        star.addEventListener("click", e => {
            selectedRating = parseInt(e.target.dataset.value);
            stars.forEach(s => {
                s.classList.toggle("filled", parseInt(s.dataset.value) <= selectedRating);
            });
        });
    });

    // 2.3 Submit feedback
    feedbackForm.addEventListener("submit", async e => {
        e.preventDefault();

        const comment = document.getElementById("feedbackComment").value.trim();

        if (!selectedRating) {
            messageBox.textContent = "⭐ Please select a rating!";
            messageBox.style.color = "#ff6b6b";
            return;
        }

        if (comment.length < 5) {
            messageBox.textContent = "Comment must be at least 5 characters long.";
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

    // 3. Admin Stats submenu
    const adminDropdownMenu = document.querySelector("[data-admin-dropdown]");
    const statsToggle = document.querySelector(".stats-toggle");

    if (statsToggle && adminDropdownMenu) {

        // 3.1 Toggle Stats submenu (keep Admin open)
        statsToggle.addEventListener("click", (e) => {
            e.preventDefault();
            e.stopPropagation();

            const submenu = statsToggle.closest(".dropdown-submenu");
            submenu.classList.toggle("open");
        });

        // Close submenu when clicking a real link
        adminDropdownMenu
            .querySelectorAll("a.dropdown-item:not(.stats-toggle)")
            .forEach(link => {
                link.addEventListener("click", () => {
                    adminDropdownMenu
                        .querySelector(".dropdown-submenu.open")
                        ?.classList.remove("open");
                });
            });

        // Close submenu on outside click
        document.addEventListener("click", (e) => {
            if (!adminDropdownMenu.contains(e.target)) {
                adminDropdownMenu
                    .querySelector(".dropdown-submenu.open")
                    ?.classList.remove("open");
            }
        });
    }

// 3.2 Reset Stats submenu when navbar collapses (MOBILE)
    const navbarCollapse = document.getElementById("navbarSupportedContent");

    if (navbarCollapse) {
        navbarCollapse.addEventListener("hidden.bs.collapse", () => {
            document
                .querySelector(".dropdown-submenu.open")
                ?.classList.remove("open");
        });
    }

// 3.3 Reset Stats submenu when Admin dropdown closes
    const adminDropdownToggle = document.getElementById("adminDropdown");

    if (adminDropdownToggle) {
        adminDropdownToggle.addEventListener("hidden.bs.dropdown", () => {
            document
                .querySelector(".dropdown-submenu.open")
                ?.classList.remove("open");
        });
    }

// 4. Close navbar when clicking outside of it (MOBILE)
    const navbar = document.querySelector(".navbar");

    document.addEventListener("click", (e) => {
        if (!navbar || !navbarCollapse) return;

        const isClickInsideNavbar = navbar.contains(e.target);
        const isNavbarOpen = navbarCollapse.classList.contains("show");

        if (!isClickInsideNavbar && isNavbarOpen) {
            // Bootstrap collapse API (Bootstrap 4)
            $(navbarCollapse).collapse("hide");
        }
    });
});