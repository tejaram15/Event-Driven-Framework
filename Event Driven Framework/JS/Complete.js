var margin = {top: 20,right: 40,bottom: 20,left: 100},
    width = document.getElementById('tab1').offsetWidth - margin.left - margin.right,
    height = document.getElementById('tab1').offsetHeight - margin.top - margin.bottom;

var svg = d3.select("#tab1")
    .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
    .append("g")
        .attr("transform", 
              "translate(" + margin.left + "," + margin.top + ")");

var x = d3.time.scale().range([0, width]);

var y = d3.scale.linear().range([height, 0]);

var bisectDate = d3.bisector(function(d) { return d["start_date"]; }).left;

// Define the axes
var xAxis = d3.svg.axis().scale(x)
    .orient("bottom").ticks(10);

var yAxis = d3.svg.axis().scale(y)
    .orient("left").ticks(5);            

var line = d3.svg.line()
    .x(function(d) { return x(d["start_date"]); })
    .y(function(d) { return y(d["paid"]); });

d3.json("/Resources/Data/send.json",function(error,data) {

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
//Tooltips
var focus = svg.append("g")
  .attr("class", "focus")
  .style("display", "none");

//Adds circle to focus point on line
focus.append("circle").attr("r", 4);

//Adds text to focus point on line    
focus.append("text")
  .attr("x", 9)
  .attr("dy", ".35em");    

//Creates larger area for tooltip   
var overlay = svg.append("rect")
  .attr("fill","none")
  .attr("class", "overlay")
  .attr("width", width)
  .attr("height", height)
  .on("mouseover", function() { focus.style("display", null); })
  .on("mouseout", function() { focus.style("display", "none"); })
  .on("mousemove", mousemove);

function mousemove() {
var x0 = x.invert(d3.mouse(this)[0]),
    i = bisectDate(data, x0, 1),
    d0 = data[i - 1],
    d1 = data[i],
    d = x0 - d0["start_date"] > d1["start_date"] - x0 ? d1 : d0;
focus.attr("transform", "translate(" + x(d["start_date"]) + "," + y(d["paid"]) + ")");
focus.select("text").text(d["paid"]);
}

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