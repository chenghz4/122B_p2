function handleStarResult(resultData) {
    console.log("handleStarResult: populating star table from resultData");
    // populate the star info h3
    var starInfoElement = jQuery("#star_info");
    starInfoElement.append("<p>Star Name: " + resultData[0]["star_name"] + "</p>" +
        "<p>Date Of Birth: " + resultData[0]["star_dob"] + "</p>");


    // populate the star table
    var movieTableBodyElement = jQuery("#movie_table_body");
    for (var i = 0; i < Math.min(10, resultData.length); i++) {
        var rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<th>" + resultData[i]["movie_title"] + "</th>";
        rowHTML += "<th>" + resultData[i]["movie_year"] + "</th>";
        rowHTML += "<th>" + resultData[i]["movie_director"] + "</th>";
        rowHTML += "</tr>"
        movieTableBodyElement.append(rowHTML);
    }
}


// Retrieve parameter from request URL, matching by parameter name
function getParameterByName(name, url) {
    // Get request URL
    if (!url) url = window.location.href;


    name = name.replace(/[\[\]]/g, "\\$&");

    // Ues regular expression to find matched parameter
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';

    // Return the decoded parameter
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}



// Get id from URL
var starId = getParameterByName('id');

// makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
    dataType: "json",
    method: "GET",
    url: "/project2-api-example/aStar?id=" + starId,
    success: (resultData) => handleStarResult(resultData)
})
;
;