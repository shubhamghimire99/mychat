
$(document).ready(function () {
	$('#action_menu_btn').click(function () {
		$('.action_menu').toggle();
	});
});
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
