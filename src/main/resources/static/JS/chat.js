//for : button
$(document).ready(function () {
  $("#action_menu_btn").click(function () {
    $(".action_menu").toggle();
  });
});

function scrollToBottom() {
  var scrollableArea = document.getElementById("chat");
  scrollableArea.scrollTop = scrollableArea.scrollHeight;
}

// Scroll to the bottom when the page is loaded or reloaded
window.addEventListener("load", scrollToBottom);

// using jQuery to print a message when the document is ready
// sending the url of dynamic chat room to the server
//stop auto reload of page


var socket = new WebSocket("ws://" + window.location.host + "/chat{room_id}");

socket.onmessage = function (event) {
  var imageUrl = document.getElementById("image");
  var message = JSON.parse(event.data);
  const url = new URLSearchParams(location.search);
  const currentDM = url.get("userId");
  if (currentDM != message.senderId) return;
  var msgArea = document.createElement("div");
  var messageElement = document.createElement("div");
  messageElement.className = "d-flex justify-content-start mb-4";

  messageElement.innerHTML =
    '<div class="img_cont_msg">' +
    '<img th:src="${message.imageUrl}" class="rounded-circle user_img_msg">' +
    "</div>" +
    '<div class="msg_cotainer">' +
    "<p>" +
    message.message +
    "</p>" +
    '<span class="msg_time">' +
    message.sender +
    "</span>" +
    "</div>";
  msgArea.appendChild(messageElement);
  $("#chat").append(msgArea);
  console.log("Received message: " + event.data);

};

//For Emojies
const message = document.getElementById("message");
const pickerOptions = {
  onEmojiSelect: (e) => {
    message.value += e.native;
  },
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
