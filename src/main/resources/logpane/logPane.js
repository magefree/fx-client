function logPane(containerId) {
    var container = document.getElementById(containerId);

    var createMessagePartNode = function(messagePart) {
        var messageNode = document.createElement("span");
        var textNode = document.createTextNode(messagePart.text);

        messageNode.classList.add("messagePart");
        messageNode.style.color = messagePart.color;
        messageNode.appendChild(textNode);

        return messageNode;
    };

    var appendMessage = function(messageParts) {
        var messageNode = document.createElement("div");
        messageNode.classList.add("message");

        for (var i = 0; i < messageParts.length; i++) {
            messageNode.appendChild(createMessagePartNode(messageParts[i]));
            messageNode.appendChild(document.createTextNode(" "));
        }

        container.appendChild(messageNode);

        window.scrollTo(0, document.body.scrollHeight);
    };

    return {
        appendMessage: appendMessage
    };
}
