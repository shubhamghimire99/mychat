//for : button
$(document).ready(function () {
    $('#action_menu_btn').click(function () {
        $('.action_menu').toggle();
    });
});

//popup
// Get references to the popup and buttons
let modal = document.querySelector("[data-modal]");
let open = document.querySelector("[data-open-modal]");
let close = document.querySelector("[data-close-modal]");

// Add event listeners
open.addEventListener("click", () => {
    modal.showModal();
});

close.addEventListener("click", () => {
    modal.close();
})

// close popup when clicking outside
modal.addEventListener("click", (e) => {
    if (e.target === modal) {
        modal.close();
    }
});

function scrollToBottom() {
    var scrollableArea = document.getElementById('chat');
    scrollableArea.scrollTop = scrollableArea.scrollHeight;
}

// Scroll to the bottom when the page is loaded or reloaded
window.addEventListener('load', scrollToBottom);


//For Emojies
const message = document.getElementById("message");
const pickerOptions = {
    onEmojiSelect: (e) => {
        message.value += e.native

    }
};

const picker = new EmojiMart.Picker(pickerOptions);
picker.classList.toggle("hide");
picker.classList.add("picker");
document.getElementById("chat-form").appendChild(picker);

function toggleEmote() {
    picker.classList.toggle("hide");
}