

function handleStarResult(resultData) {
	var starTableElement = jQuery("#star_table");
	var tableHeaderHtml = 			
		"<thead>" + 
			"<tr>" +
				"<th>Name</th>" +
				"<th>Birth year</th>" +
			"</tr>" +
		"</thead>";
	
	starTableElement.append(tableHeaderHtml);
	
	var tableBodyHtml = "";
	
	tableBodyHtml += "<tbody>";
	for (var i = 0; i < 10; i++) {
		tableBodyHtml +=
				"<tr>" +
				  "<th>" + resultData[i]["star_name"] + "</th>" +
				  "<th>" + resultData[i]["star_dob"] + "</th>" +
				"</tr>"
	}
	tableBodyHtml += "</tbody>";
	
	starTableElement.append(tableBodyHtml);
}

jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/project2-api-example/stars",
	  success: (resultData) => handleStarResult(resultData)
});

