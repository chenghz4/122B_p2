/**
 * This example is following frontend and backend separation.
 *
 * Before this .js is loaded, the html skeleton is created.
 *
 * This .js performs two steps:
 *      1. Use jQuery to talk to backend API to get the json data.
 *      2. Populate the data to correct html elements.
 */


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



/**
 * Handles the data returned by the API, read the jsonObject and populate data into html elements
 * @param resultData jsonObject
 */
function handleStarResult(resultData) {
    console.log("handleStarResult: populating star table from resultData");

    // Populate the star table
    // Find the empty table body by id "star_table_body"
    let starTableBodyElement = jQuery("#star_table_body");

    for (let i = 0; i <resultData.length; i++) {

        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<th>" + (i+1) + "</th>";
        rowHTML +=
            "<th>" +
            // Add a link to single-star.html with id passed with GET url parameter
            '<a href="single-movie.html?id=' + resultData[i]['movie_id'] + '">'
            + resultData[i]["movie_title"] +     // display star_name for the link text
            '</a>' +
            "</th>";

        rowHTML += "<th>" + resultData[i]["movie_year"] + "</th>";
        rowHTML += "<th>" + resultData[i]["movie_director"] + "</th>";

       // for(let j=0; j < resultData[i]["list_g"].length;j++)
        rowHTML += "<th>" + resultData[i]["list_g"] + "</th>";

        let n=resultData[i]["s.id"].split(",").length;
        rowHTML +="<th>";
        for (let j = 0; j < n-1; j++) {

            rowHTML += // Add a link to single-star.html with id passed with GET url parameter
                    '<a href="single-star.html?id=' + resultData[i]["s.id"].split(",")[j] + '">'
                    + resultData[i]["list_s"].split(",")[j] + ", "     // display star_name for the link text
                '</a>';

        }

        rowHTML += // Add a link to single-star.html with id passed with GET url parameter
            '<a href="single-star.html?id=' + resultData[i]["s.id"].split(",")[n-1] + '">'
            + resultData[i]["list_s"].split(",")[n-1] + ""     // display star_name for the link text
        '</a>';

        rowHTML +="</th>";


        rowHTML += "<th>" + resultData[i]["rating"] + "</th>";
        rowHTML += "</tr>";

        // Append the row created to the table body, which will refresh the page
        starTableBodyElement.append(rowHTML);


    }
    let space="";
    space +="<tr>";
    space +="<th>";
    space +="</th>";
    space +="</tr>";
    starTableBodyElement.append(space);
    let goback = "";


    goback +="<tr>";
    goback +="<th>";
    goback +="</th>";
    goback +="<th>";
    goback +="</th>";
    goback +="<th>";
    goback +="</th>";
    goback +="<th>";
    goback +="</th>";
    goback +="<th>";
    goback +="</th>";
    goback +="<th>";
    goback +="</th>";
    goback +=
        "<th style='color: crimson'>" +
        // Add a link to single-star.html with id passed with GET url parameter
        '<a href="Main.html" >'
        + "Go Back to Search, Browsing Page" +     // display star_name for the link text
        '</a>' +
        "</th>";
    goback += "</tr>";
    starTableBodyElement.append(goback);



}

let title = getParameterByName('id');
let year= getParameterByName('year');
let director= getParameterByName('director');
let star= getParameterByName('star');
let page=getParameterByName('page');
let number=getParameterByName('number');



function handlenumberResult(resultDataString) {
    resultDataJson = JSON.parse(resultDataString);
    console.log("jump to index");
    // If login succeeds, it will redirect the user to index.html
    window.location.replace(
        "index.html?id="+title+
        "&year="+year +
        "&director="+director+
        "&star="+star+
        "&page="+"1"+
        "&number="+resultDataJson["number"]);

}

function submitLoginForm(formSubmitEvent) {
    console.log("ss");
    formSubmitEvent.preventDefault();

    $.post(
        "api/stars",
        // Serialize the login form to the data sent by POST request
        $("#number").serialize(),
        (resultDataString) => handlenumberResult(resultDataString)
);
}

// Bind the submit action of the form to a handler function
$("#number").submit((event) => submitLoginForm(event));








/**
 * Once this .js is loaded, following scripts will be executed by the browser
 */

// Makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
    dataType: "json", // Setting return data type
    method: "GET", // Setting request method
    url: "api/stars?id="+title+"&year="+year+"&director="+director+"&star="+star+"&page="+page+"&number="+number,
    // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleStarResult(resultData) // Setting callback function to handle data returned successfully by the StarsServlet
});