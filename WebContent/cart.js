

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

    console.log(resultArray.length);
    console.log(resultArray);
    let n=resultArray.length;

    if(n>1) {
        let res = "<ul>";
        for (let i = 1; i < n; i++) {
            // each item will be in a bullet point
            res += "<li>" + resultArray[i];//even number is id odd is title
            res += "<input type='text' placeholder='Enter number of movie here' name=" + i + ">";


            res += "</li>";
        }
        res += "<input type='submit' value='Go'>";
        res += "</ul>";

        // clear the old array and show the new array in the frontend
        $("#movie_list").html("");
        $("#movie_list").append(res);

    }
    else{
        let res = "<ul>";
        res += "<li>" ;//even number is id odd is title
        res += "The shopping cart is Empty!";


        res += "</li>";
        res += "</ul>";
        $("#movie_list").html("");
        $("#movie_list").append(res);
    }
}

/**
 * Handle the items in item list
 * @param resultDataString jsonObject, needs to be parsed to html
 */




let id=getParameterByName("id");


$.ajax({
    type: "POST",
    url: "api/cart?id="+id,
    success: (resultDataString) => handleSessionData(resultDataString)
});

// Bind the submit action of the form to a event handler function
