function handleLoginResult(resultDataString) {
    resultDataJson = JSON.parse(resultDataString);
    console.log("jump to index");
    // If login succeeds, it will redirect the user to index.html
    window.location.replace(
        "index.html?id="+resultDataJson["title"]+
        "&year="+resultDataJson["year"] +
        "&director="+resultDataJson["director"]+
        "&star="+resultDataJson["star"]+
        "&page="+"1"+
        "&number="+"20"+
        "&sort="+"a.rating desc"+
        "&genres="+""
    );

}
function submitLoginForm(formSubmitEvent) {
    console.log("search_form");
    formSubmitEvent.preventDefault();

    $.post(
        "api/Main",
        // Serialize the login form to the data sent by POST request
        $("#search_form").serialize(),
        (resultDataString) => handleLoginResult(resultDataString)
);
}

// Bind the submit action of the form to a handler function
$("#search_form").submit((event) => submitLoginForm(event));





function handlebrowsing_gen(resultDataString) {

    resultDataJson = JSON.parse(resultDataString);
    console.log(resultDataJson["genres"]);
    // If login succeeds, it will redirect the user to index.html
    window.location.replace(
        "index.html?id="+""+
        "&year="+"" +
        "&director="+""+
        "&star="+""+
        "&page="+"1"+
        "&number="+"20"+
        "&sort="+"a.rating desc"+
        "&genres="+resultDataJson["genres"]
    );

}



function handlegenre(formSubmitEvent) {
    console.log("submit cart form");
    /**
     * When users click the submit button, the browser will not direct
     * users to the url defined in HTML form. Instead, it will call this
     * event handler when the event is triggered.
     */
    formSubmitEvent.preventDefault();

    $.post(
        "api/Main",
        // Serialize the cart form to the data sent by POST request
        $("#browsing_genres").serialize(),
        (resultDataString) => handlebrowsing_gen(resultDataString)
);
}

$("#browsing_genres").submit((event) => handlegenre(event));

