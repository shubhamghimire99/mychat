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

// get the id of otherusers in the chat


// using jQuery to print a message when the document is ready
// sending the url of dynamic chat room to the server

var socket = new WebSocket("ws://" + window.location.host + "/chat");

socket.onmessage = function (event) {

    var message = JSON.parse(event.data);

    var msgArea = document.createElement("div");
    var messageElement = document.createElement("div");

    messageElement.className = "d-flex justify-content-start mb-4";

    messageElement.innerHTML = "<div class=\"img_cont_msg\">" +
        "<img th:src=\"${message.imageUrl}\" class=\"rounded-circle user_img_msg\">" +
        "</div>" +
        "<div class=\"msg_cotainer\">" + "<p>" + message.message + "</p>" +
        "<span class=\"msg_time\">" + message.sender + "</span>"
        + "</div>";

    msgArea.appendChild(messageElement);
    $("#chat").append(msgArea);



    console.log("Received message: " + event.data);
};

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



// function sendMessage() {
//     var message = $("#message").val();
//     var json = JSON.stringify({
//         "text": message
//     });
//     socket.send(json);
//     $("#message").val("");
// }

