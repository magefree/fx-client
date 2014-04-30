function logPane(containerId) {
    var container = document.getElementById(containerId);

    var createMessagePartNode = function(messagePart) {
        var messageNode = document.createElement("span");
        messageNode.classList.add("messagePart");
        messageNode.style.color = messagePart.color;

        var textLines = messagePart.text.split("\n");
        for (var i = 0; i < textLines.length; i++) {
            var textNode = document.createTextNode(textLines[i]);
            messageNode.appendChild(textNode);

            if (i < textLines.length - 1) {
                messageNode.appendChild(document.createElement("br"));
            }
        }


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
