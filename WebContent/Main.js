function handleLoginResult(resultDataString) {
    resultDataJson = JSON.parse(resultDataString);
    console.log("jump to index");
    // If login succeeds, it will redirect the user to index.html
    window.location.replace("index.html?id="+resultDataJson["title"]);

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