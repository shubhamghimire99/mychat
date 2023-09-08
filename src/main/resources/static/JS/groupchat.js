//for : button
$(document).ready(function () {
	$('#action_menu_btn').click(function () {
		$('.action_menu').toggle();
	});
});

function scrollToBottom() {
    var scrollableArea = document.getElementById('chat');
    scrollableArea.scrollTop = scrollableArea.scrollHeight;
}

// Scroll to the bottom when the page is loaded or reloaded
window.addEventListener('load', scrollToBottom);


//For Emojies
const message=document.getElementById("message");
const pickerOptions = { 
    onEmojiSelect: (e)=>{
        message.value+=e.native

    }
};   

const picker = new EmojiMart.Picker(pickerOptions);
picker.classList.toggle("hide");
picker.classList.add("picker");
document.getElementById("chat-form").appendChild(picker);

function toggleEmote() {
    picker.classList.toggle("hide");
}


//popup
// Get references to the popup and buttons
const popup = document.getElementById("popup");
const showPopupBtn = document.getElementById("showPopupBtn");
const closePopupBtn = document.getElementById("closePopupBtn");

// Function to show the popup
function showPopup() {
    popup.style.display = "block";
}

// Function to hide the popup
function hidePopup() {
    popup.style.display = "none";
}

// Event listeners to trigger the popup
showPopupBtn.addEventListener("click", showPopup);
closePopupBtn.addEventListener("click", hidePopup);
popup.addEventListener("click", hidePopup);

// Prevent clicks within the popup from closing it
popup.querySelector(".popup-content").addEventListener("click", function (event) {
    event.stopPropagation();
});
