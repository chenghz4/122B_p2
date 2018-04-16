function handleStarResult(resultData) {
    console.log("handleStarResult: populating star table from resultData");

    // populate the star table
    let starTableBodyElement = jQuery("#star_table_body");
    for (let i = 0; i < Math.min(10, resultData.length); i++) {
        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<th>" + '<a href="/project2-api-example/single-star.html?id=' + resultData[i]['star_id'] + '">' + resultData[i]["star_name"] + '</a>' + "</th>";
        rowHTML += "<th>" + resultData[i]["star_dob"] + "</th>";
        rowHTML += "</tr>";
        starTableBodyElement.append(rowHTML);
    }
}

// makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
    dataType: "json",
    method: "GET",
    url: "/project2-api-example/stars",
    success: (resultData) => handleStarResult(resultData)
});