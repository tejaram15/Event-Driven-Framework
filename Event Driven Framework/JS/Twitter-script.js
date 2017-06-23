// Set the dimensions of the canvas / graph
var margin = {top: 30, right: 20, bottom: 70, left: 50},
    width = 500 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

var labels = ["iphone","iPhone6","photography","dance","tablet","apple","ios","AppleNews","Apps","iPad","youthvote"];
//d3.json("/Resources/Data/data.json", function(error, data) {
//    data["iphone"][0]["hash"].forEach(function(d){
//        labels.push(d["name"].toString());
//    });
//});
console.log(labels);
var jsonCircles = [
   { "x_axis": 10, "y_axis": 10, "radius": 20, "color" : "green" , "label" : labels[0]},
   { "x_axis": 90, "y_axis": 90, "radius": 20, "color" : "purple", "label" : labels[1]},
   { "x_axis": 200, "y_axis": 200, "radius": 20, "color" : "red", "label" : labels[2]},
   { "x_axis": 90, "y_axis": 150, "radius": 20, "color" : "green", "label" : labels[3] },
//   { "x_axis": 50, "y_axis": 50, "radius": 20, "color" : "purple", "label" : labels[4]},
//   { "x_axis": 60, "y_axis": 60, "radius": 20, "color" : "red", "label" : labels[5]},
//   { "x_axis": 70, "y_axis": 70, "radius": 20, "color" : "green" , "label" : labels[6]},
//   { "x_axis": 80, "y_axis": 80, "radius": 20, "color" : "purple", "label" : labels[7]},
//   { "x_axis": 90, "y_axis": 90, "radius": 20, "color" : "red", "label" : labels[8]},
//   { "x_axis": 100, "y_axis": 100, "radius": 20, "color" : "green" , "label" : labels[9]},
//   { "x_axis": 110, "y_axis": 110, "radius": 20, "color" : "purple", "label" : labels[10]},
//   { "x_axis": 120, "y_axis": 120, "radius": 20, "color" : "red", "label" : labels[11]}
];

function draw() {
    // Adds the svg canvas
    var svg = d3.select("#chart")
        .append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
        .append("g")
            .attr("transform", 
                  "translate(" + margin.left + "," + margin.top + ")");
    
    var elem = svg.selectAll("g myCircleText").data(jsonCircles);
    
    /*Create and place the "blocks" containing the circle and the text */  
    var elemEnter = elem.enter()
	    .append("g")
	    .attr("transform", function(d){return "translate("+d.x_axis+",80)"})
        .attr("transform", function(d){return "translate("+d.y_axis+",80)"})
 
    /*Create the circle for each block */
    var circle = elemEnter.append("circle")
	    .attr("r", function(d){return d.radius} )
	    .attr("stroke","black")
	    .attr("fill", function(d){return d.color} )
 
    /* Create the text for each block */
    elemEnter.append("text")
	    .attr("dx", function(d){return -20})
        .attr("dy", function(d){return -20})                               
	    .text(function(d){return d.label})
}
              
draw();