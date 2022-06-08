// the link to your model provided by Teachable Machine export panel

const URL = "https://teachablemachine.withgoogle.com/models/Aq3LV8H1M/";

let model, webcam, labelContainer, maxPredictions;

// Load the image model and setup the webcam
async function init() {
    const modelURL = URL + "model.json";
    const metadataURL = URL + "metadata.json";

    // load the model and metadata
    // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
    // or files from your local hard drive
    // Note: the pose library adds "tmImage" object to your window (window.tmImage)
    model = await tmImage.load(modelURL, metadataURL);
    maxPredictions = model.getTotalClasses();

    // Convenience function to setup a webcam
    const flip = true; // whether to flip the webcam
    webcam = new tmImage.Webcam(640, 480, flip); // width, height, flip
    webcam = new tmImage.Webcam(); // width, height, flip

    await webcam.setup(); // request access to the webcam
    await webcam.play();
    window.requestAnimationFrame(loop);

    // append elements to the DOM
    //document.getElementById("localVideoContainer").appendChild(webcam.canvas);
    document.getElementById("localVideoCanvas").replaceWith(webcam.canvas);


    labelContainer = document.getElementById("labelContainer");
    for (let i = 0; i < maxPredictions; i++) { // and class labels
        labelContainer.appendChild(document.createElement("div"));
    }
}

async function loop() {
    webcam.update(); // update the webcam frame
    await predict();
    window.requestAnimationFrame(loop);
}

// run the webcam image through the image model
async function predict() {
    // predict can take in an image, video or canvas html element
    // const prediction = await model.predict(webcam.canvas);
    // for (let i = 0; i < maxPredictions; i++) {
    //     const classPrediction =
    //         prediction[i].className + ": " + prediction[i].probability.toFixed(2);
    //     labelContainer.childNodes[i].innerHTML = classPrediction;
    // }
    const prediction = await model.predict(webcam.canvas);
    if(prediction[1].probability > 0.8){
        //TODO: 눈 감았을때 인식률이 threshold 이상일때 처리
    }
}