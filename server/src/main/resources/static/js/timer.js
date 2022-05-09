var pause = true;
var count = 0;
var id = 0;

countTimers();

function countTimers() {

    setInterval(timer, 1000);

    function timer() {
        if (!pause) {
            count = count + 1;

            var hours = Math.floor((count % (60 * 60 * 24)) / (60 * 60));
            var minutes = Math.floor((count % (60 * 60)) / 60);
            var seconds = Math.floor(count % 60);

            document.getElementById("timer").innerHTML = hours + ": "
                + minutes + ": " + seconds;

        }
    }

}

function dataSend() {
    var messageDTO = {second: count};
    $.ajax({url: "/room/" + id, data: messageDTO, type: "POST",}).done(function (fragment) {
        $("#resultDiv").replaceWith(fragment);
    });
}

function setId(roomId) {
    id = roomId
}

function setCount(oldCount){
    count = oldCount
}

function getCount() {
    return count;
}

function startTimer() {
    pause = false;
}

function pauseTimer() {
    pause = true;
    document.getElementById("sendCount").value = count;
}