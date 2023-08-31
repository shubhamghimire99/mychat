// using jQuery to print a message when the document is ready

var socket = new WebSocket("ws://" + window.location.host + "/cheat");

socket.onmessage = function(event) {
    console.log(event.data.id)
    var message = JSON.parse(event.data);
    var messageElement = document.createElement("div");
    messageElement.className = "message-box";
    messageElement.innerHTML = "<p>" + message.sender + " says: " + message.message + "</p>" +
                               "<p>" + message.timestamp + "</p>";
    $("#chat").append(messageElement);
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

