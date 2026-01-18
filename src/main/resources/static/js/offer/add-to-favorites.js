document.addEventListener("DOMContentLoaded", () => {
    const btn = document.querySelector(".favorites-btn");
    const messageBox = document.getElementById("favorite-message");
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    const csrfHeaderValue = document.querySelector('meta[name="_csrf"]').content;

    if (btn) {
        btn.addEventListener("click", async () => {
            const offerId = btn.dataset.offerId;

            try {
                const res = await fetch(`/users/favorites/${offerId}/toggle`, {
                    method: "POST",
                    headers: { [csrfHeaderName]: csrfHeaderValue }
                });
                const data = await res.json();

                if (data.success) {
                    const message = data.message || "Favorites updated!";

                    btn.classList.add("animated");

                    const isAddedToFavorites = btn.classList.toggle("added");

                    btn.querySelector(".btn-text").textContent = isAddedToFavorites
                        ? "Remove from Favorites"
                        : "Save to Favorites";

                    messageBox.textContent = message;
                    messageBox.classList.add("show");

                    setTimeout(() => {
                        btn.classList.remove("animated");
                        messageBox.classList.remove("show");
                    }, 2500);
                } else {
                    const errorMessage = data.message || "Something went wrong!";
                    messageBox.textContent = `⚠️ ${errorMessage}`;
                    messageBox.classList.add("show");
                    setTimeout(() => messageBox.classList.remove("show"), 2500);
                }
            } catch (err) {
                messageBox.textContent = "❌ Network error!";
                messageBox.classList.add("show");
                setTimeout(() => messageBox.classList.remove("show"), 2500);
            }
        });
    }
});