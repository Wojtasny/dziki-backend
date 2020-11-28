let map, heatmap;

let center = { lat: 52.2297, lng: 21.0122 };
let zoom = 8;

let highlighted;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    zoom: zoom,
    center: center
  });

  $(document).ready(function() {
      $.ajax({
          type: "GET",
          url: "/file/boars_clean",
          dataType: "text",
          success: function(data) {processData(data);}
       });
  });

  areas = drawAndReturnAreas(map);

  areas.forEach(area => google.maps.event.addListener(area, 'mouseover', function(){highlight(this);}))
  areas.forEach(area => google.maps.event.addListener(area, 'mouseout', function(){clearHighlight();}))

}

function processData(allText) {
  heatmap = new google.maps.visualization.HeatmapLayer({
      data: getEnetWildData(allText),
      map: map,
  });
 }

function toggleHeatmap() {
  heatmap.setMap(heatmap.getMap() ? null : map);
}

function changeGradient() {
  const gradient = [
    "rgba(0, 255, 255, 0)",
    "rgba(0, 255, 255, 1)",
    "rgba(0, 191, 255, 1)",
    "rgba(0, 127, 255, 1)",
    "rgba(0, 63, 255, 1)",
    "rgba(0, 0, 255, 1)",
    "rgba(0, 0, 223, 1)",
    "rgba(0, 0, 191, 1)",
    "rgba(0, 0, 159, 1)",
    "rgba(0, 0, 127, 1)",
    "rgba(63, 0, 91, 1)",
    "rgba(127, 0, 63, 1)",
    "rgba(191, 0, 31, 1)",
    "rgba(255, 0, 0, 1)",
  ];
  heatmap.set("gradient", heatmap.get("gradient") ? null : gradient);
}

function changeRadius() {
  heatmap.set("radius", heatmap.get("radius") ? null : 20);
}

function changeOpacity() {
  heatmap.set("opacity", heatmap.get("opacity") ? null : 0.2);
}

function getEnetWildData(csvInString) {
    data = $.csv.toObjects(csvInString);
    return data.map(data => new google.maps.LatLng(data.lat, data.long));
}

function drawAndReturnAreas(map) {
  var southWest = {lng: 14.0745211117, lat: 49.0273953314};
  var northEast = {lng: 24.0299857927, lat: 54.8515359564};

  var numberOfParts = 60;

  var tileWidth = (northEast.lng - southWest.lng) / numberOfParts;
  var tileHeight = (northEast.lat - southWest.lat) / numberOfParts;

  areas = [];

  for (var x = 0; x < numberOfParts; x++) {
    for (var y = 0; y < numberOfParts; y++) {
      var areaBounds = {
        north: southWest.lat + (tileHeight * (y+1)),
        south: southWest.lat + (tileHeight * y),
        east: southWest.lng + (tileWidth * (x+1)),
        west: southWest.lng + (tileWidth * x)
      };

      var area = new google.maps.Rectangle({
        fillColor: "#FF0000",
        fillOpacity: 0.35,
        strokeWeight: 0,
        map: map,
        bounds: areaBounds,
      });

      areas.push(area);
    }
  }
  return areas;
}

function highlight(area){
    area.setOptions({fillOpacity: 0.7});
    highlighted = area;
}

function clearHighlight(){
    highlighted.setOptions({fillOpacity: 0.35});
}