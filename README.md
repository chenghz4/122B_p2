## CS 122B Project 2 API example

This example shows how frontend and backend are separated by implementing a star list page.

### To run this example: 
1. clone this repository using `git clone https://github.com/UCI-Chenli-teaching/project2-api-example.git`
2. open Eclipse -> File -> import -> under "Maven" -> "Existing Maven Projects" -> Click "Finish".
3. For "Root Directory", click "Browse" and select this repository's folder. Click "Finish".
4. In "Java Resources" folder, open `ShowStars.java`. Change the mysql username and password and make sure you have the `moviedb` database.
5. You can run this project on Tomcat now.

### Brief Explanation
`ShowStars.java` is a Java servlet that talks to the database and get the stars. It returns a list of stars in the JSON format. 

`index.js` is the main Javascript file that initiates an HTTP GET request to the `Movie.java` servlet. After the response is returned, `index.js` populates the table using the data it gets.

`index.html` is the main HTML file that imports jQuery, Bootstrap, and `index.js`. It also contains the intial skeleton for the table.

### Separating frontend and backend
For project 2, you are recommended to separate frontend and backend. Backend Java Servlet only provides API in JSON format. Frontend Javascript code fetches the data through HTTP (ajax) requests and then display the data on the webpage. 

This approach is **not** required, but we recommend it because it's considered a better practice compared to `JSP`, a very outdated technology.

This example uses `jQuery` for making HTTP requests and manipulate DOM. jQuery is relatively easy to learn compared to other frameworks. This example also includes `Bootstrap`, a popular UI framework to let you easily make your webpage look fancy. 

You are free to use any technology/tool/framework you want. The backend language must be Java.
