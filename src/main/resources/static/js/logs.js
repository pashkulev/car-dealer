$(() => {
    let tableBody = $("#log-rows");
    let usernameInput = $("#username");
    let noSuchUserMessage = $("#noSuchUserMessage");

    $("#search-btn").on("click", loadLogsByUsername);
    $("#clear-all-btn").on("click", function () {
        return confirm(`Are you sure you want delete all Logs?`);
    });

    function loadLogsByUsername() {
        let username = usernameInput.val();

        let request = {
            method: "GET",
            url: `http://localhost:8080/logs/${username}`
        };

        $.ajax(request).then(displayLogsByUsername);

        function displayLogsByUsername(logs) {
            tableBody.hide();
            tableBody.empty();

            if (logs.length === 0) {
                noSuchUserMessage.text(`No log records for user '${username}'`);
                noSuchUserMessage.fadeIn();
                setTimeout(() => noSuchUserMessage.fadeOut(), 5000);
                return;
            }

            for (let i = 0; i < logs.length; i++) {
                let log = logs[i];
                tableBody.append($("<tr>")
                    .append($(`<td>${i + 1}</td>`))
                    .append($(`<td>${log.userUsername}</td>`))
                    .append($(`<td>${log.operation}</td>`))
                    .append($(`<td>${log.modifiedTable}</td>`))
                    .append($(`<td>${log.time}</td>`)));
            }

            usernameInput.val("");
            tableBody.fadeIn();
        }
    }
});