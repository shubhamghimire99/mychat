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

// function sendMessage() {
//     var message = $("#message").val();
//     var json = JSON.stringify({
//         "text": message
//     });
//     socket.send(json);
//     $("#message").val("");
// }

