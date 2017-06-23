var svg = d3.select("#view1"),
    margin = {top: 20,right: 20,bottom: 20,left: 20},
    width = 1000 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom,
    g = svg.append("g").attr("transform","translate("+ margin.left + "," + margin.top +")");

var parseDate = d3.timeParse("%Y-%m-%d");

var x = d3.scaleTime().rangeRound([0,width]);

var y = d3.scaleLinear().rangeRound([height, 0]);

var line = d3.line()
    .x(function(d) { return x(d["start_date"]); })
    .y(function(d) { return y(d["paid"]); });

d3.json("/Resources/Data/Yearly_response.json", function(d) {
    console.log(d);
});