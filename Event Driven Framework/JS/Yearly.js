function load_graph(){

var margin = {top: 20,right: 40,bottom: 20,left: 100},
    width = document.getElementById('view1').offsetWidth - margin.left - margin.right,
    height = document.getElementById('view1').offsetHeight - margin.top - margin.bottom;

var svg = d3.select("#view1")
    .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
    .append("g")
        .attr("transform", 
              "translate(" + margin.left + "," + margin.top + ")");

var x = d3.time.scale().range([0, width]);

var y = d3.scale.linear().range([height, 0]);

// Define the axes
var xAxis = d3.svg.axis().scale(x)
    .orient("bottom").ticks(10);

var yAxis = d3.svg.axis().scale(y)
    .orient("left").ticks(5);            

var line = d3.svg.line()
    .x(function(d) { return x(d["start_date"]); })
    .y(function(d) { return y(d["paid"]); });

d3.json("/Resources/Data/Yearly_response.json",function(error,data) {

data = data["me"][0]["associates"]; 
data.forEach(function(d){
    d["start_date"] = new Date(d["start_date"]);
    d["paid"] = +d["paid"];
});

x.domain(d3.extent(data, function(d) { return d["start_date"]; }));
y.domain([0, d3.max(data, function(d) { return d["paid"]; })]);

svg.append("path")
    .attr("class", "line")
    .attr("d", line(data));

// Add the X Axis
svg.append("g")
    .attr("class", "x axis")
    .attr("transform", "translate(0," + height + ")")
    .call(xAxis);

// Add the Y Axis
svg.append("g")
    .attr("class", "y axis")
    .call(yAxis);
});
}