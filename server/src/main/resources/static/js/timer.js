var pause = true;
var count = 0;

countTimers();
const toggleTimerBtn = document.getElementById('toggleBtn');
toggleTimerBtn.addEventListener('click', toggleTimer);

function countTimers() {

    setInterval(timer, 1000);

    function timer() {
        if (!pause) {
            count += 1;
            document.getElementById("localTimer").innerHTML = timeFormatter(count);
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

function setCount(oldCount) {
    count = oldCount
}

function getCount() {
    return count;
}

function startTimer() {
    pause = false;
    toggleTimerBtn.value = "일시 정지"
}

function pauseTimer() {
    pause = true;
    document.getElementById("sendCount").value = count;
    toggleTimerBtn.value = "공부 시작"
}


function toggleTimer() {
    if (pause) {
        startTimer()
    } else {
        pauseTimer()
    }
}

function timeFormatter(seconds) {
    return new Date(seconds * 1000).toISOString().slice(11, 19);
}