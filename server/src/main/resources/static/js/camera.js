navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

var constraints = {audio: false, video: true};

function startCam() {
    var video = document.querySelector("#videoElement");

    if (navigator.mediaDevices.getUserMedia) {
        console.log(navigator.mediaDevices.getUserMedia)
        navigator.mediaDevices.getUserMedia({video: true})
            .then(function (stream) {
                video.srcObject = stream;
            })
            .catch(function (error) {
                console.log("Something went wrong!" + error);
            });
    }

    video.addEventListener( "loadedmetadata", () => {
        console.log(video.videoWidth);
        console.log(video.videoHeight);
    });
}

function pauseCam() {
    const video = document.getElementById("videoInput");
    video.pause();
}