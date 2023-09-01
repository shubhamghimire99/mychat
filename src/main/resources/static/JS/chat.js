
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
// using jQuery to print a message when the document is ready
var socket = new WebSocket("ws://" + window.location.host + "/cheat");

socket.onmessage = function (event) {

    var message = JSON.parse(event.data);

    var msgArea = document.createElement("div");
    var messageElement = document.createElement("div");

    messageElement.className = "d-flex justify-content-start mb-4";

    messageElement.innerHTML = "<div class=\"img_cont_msg\">" +
        "<img src=\"https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg\" class=\"rounded-circle user_img_msg\">" +
        "</div>" +
        "<div class=\"msg_cotainer\">"+"<p>"+ message.message+"</p>" +
        "<span class=\"msg_time\">" + message.sender + "</span>"
        + "</div>";

    msgArea.appendChild(messageElement);
    $("#chat").append(msgArea);

    console.log("Received message: " + event.data);
};
//For Emojies
console.log("emoji")
const input = document.getElementById('message');
const emojiPicker = new EmojiPicker({
    emojiable_selector: '[data-emojiable=true]',
    assetsPath: 'https://cdnjs.cloudflare.com/ajax/libs/emoji-picker/3.0.0/img',
    popupButtonClasses: 'fa fa-smile-o'
});
emojiPicker.discover();

document.getElementById('emoji-picker').addEventListener('emoji-click', function(event) {
    input.value += event.detail.emoji;
});

document.getElementById('send-button').addEventListener('click', () => {
    const messageInput = input.value;
    const displayMessage = document.getElementById('display-message');
    displayMessage.textContent = messageInput;
});

// function sendMessage() {
//     var message = $("#message").val();
//     var json = JSON.stringify({
//         "text": message
//     });
//     socket.send(json);
//     $("#message").val("");
// }

