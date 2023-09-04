const popup = document.getElementById("popup");
const popupButton = document.getElementById("popupButton");
const closePopup = document.getElementById("closePopup");

popupButton.addEventListener("click", () => {
    popup.style.display = "block";
});

closePopup.addEventListener("click", () => {
    popup.style.display = "none";
});

// Close the popup when clicking outside of it
window.addEventListener("click", (event) => {
    if (event.target === popup) {
        popup.style.display = "none";
    }
});
