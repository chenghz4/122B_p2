

function getParameterByName(target) {
    // Get request URL
    let url = window.location.href;
    // Encode target parameter name to url encoding
    target = target.replace(/[\[\]]/g, "\\$&");

    // Ues regular expression to find matched parameter value
    let regex = new RegExp("[?&]" + target + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    console.log(decodeURIComponent(results[2].replace(/\+/g, " ")));
    // Return the decoded parameter value
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}



function handleSessionData(resultDataString) {

    const resultArray = resultDataString.split(",");
    console.log(resultArray);

    let res = "<ul>";
    for(let i = 0; i < resultArray.length; i++) {
        // each item will be in a bullet point
        res += "<li>" + resultArray[i] + "</li>";
    }
    res += "</ul>";

    // clear the old array and show the new array in the frontend
    $("#movie_list").html("");
    $("#movie_list").append(res);
    // show the session information
    //$("#movie_id").text("Movie id: " + resultDataJson["movie_id"]);
    //$("#movie_title").text("Movie title: " + resultDataJson["movie_title"]);
   // $("#movie_year").text("Movie year: " + resultDataJson["movie_year"]);
    //$("#movie_director").text("Movie director: " + resultDataJson["movie_director"]);

}

/**
 * Handle the items in item list
 * @param resultDataString jsonObject, needs to be parsed to html
 */
function handleCartArray(resultDataString) {
    const resultArray = resultDataString.split(",");
    console.log(resultArray);

    // change it to html list
    let res = "<ul>";
    for(let i = 0; i < resultArray.length; i++) {
        // each item will be in a bullet point
        res += "<li>" + resultArray[i] + "</li>";
    }
    res += "</ul>";

    // clear the old array and show the new array in the frontend
    $("#item_list").html("");
    $("#item_list").append(res);
}

/**
 * Submit form content with POST method
 * @param cartEvent
 */
function handleCartInfo(cartEvent) {
    console.log("submit cart form");
    /**
     * When users click the submit button, the browser will not direct
     * users to the url defined in HTML form. Instead, it will call this
     * event handler when the event is triggered.
     */
    cartEvent.preventDefault();

    $.get(
        "api/index",
        // Serialize the cart form to the data sent by POST request
        $("#cart").serialize(),
        (resultDataString) => handleCartArray(resultDataString)
);
}


let id=getParameterByName("id");


$.ajax({
    type: "POST",
    url: "api/cart?id="+id,
    success: (resultDataString) => handleSessionData(resultDataString)
});

// Bind the submit action of the form to a event handler function
$("#cart").submit((event) => handleCartInfo(event));