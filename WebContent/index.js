
jQuery("#header").html("Stars: ");


function handleStarResult(resultData) {
	for (var i = 0; i < 20; i++) {
		jQuery("#star_table").append("<tr>")
		jQuery("#star_table").append("<th>" + resultData[i]["star_id"] + "</th>");
		jQuery("#star_table").append("<th>" + resultData[i]["star_name"] + "</th>");
		jQuery("#star_table").append("<th>" + resultData[i]["star_dob"] + "</th>");
		jQuery("#star_table").append("</tr>")
	}
}

jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "http://localhost:8080/project2-sample-1/stars",
	  success: (resultData) => handleStarResult(resultData)
});