const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;


document.querySelectorAll(".btn-delete").forEach(btn => {
    btn.addEventListener("click", async () => {
        const id = btn.dataset.offerId;

        if (!confirm("Are you sure you want to delete this offer?")) return;

        await fetch(`/offers/${id}`, {
            method: "DELETE",
            headers: {
                [csrfHeaderName]: csrfHeaderValue,
            }
        })
            .then(res => res.json())
            .then(data => {
                showMessage(data.message, data.success ? "success" : "error");

                if (data.success)
                    setTimeout(() => window.location.href = "/", 200000);
            })
            .catch(
                () => showMessage("⚠️ Network error.", "error"));
    });
});

function showMessage(msg, type) {
    const box = document.getElementById("messageBox");
    box.textContent = msg;
    box.className = `alert ${type === "success" ? "alert-success" : "alert-danger"}`;
    setTimeout(() => (box.textContent = ""), 4000);
}
