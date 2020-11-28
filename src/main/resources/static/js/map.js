let map, heatmap;

let center = { lat: 52.2297, lng: 21.0122 };
let zoom = 7;

let highlighted;

let observations = []; // markers
let predictions = []; // highlighted areas

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
          success: function(data) {processData(data, map);}
       });

       $.ajax({
          type: "GET",
          url: "/predictions",
          dataType: "text",
          success: function(predictions) {processPredictions(predictions, map);}
       });
  });
}

function processData(allText, map) {
  data = $.csv.toArrays(allText);
  data.forEach(entry => observations.push(new google.maps.Marker({
                            position: { lat: parseFloat(entry[0]), lng: parseFloat(entry[1]) },
                            map,
                          })));


 }
function dataToDictWithSquaresIdsAsKeys(data){
    let orgniazedData = $.csv.toArrays(data);
    let result = orgniazedData.reduce(function(map, obj) {
        map[obj[1]] = obj[2];
        return map;
    }, {});
    return result
}

function processPredictions(predictions, map) {
    let squareToPrediction = dataToDictWithSquaresIdsAsKeys(predictions);

    var southWest = {lng: 14.0745211117, lat: 49.0273953314};
    var northEast = {lng: 24.0299857927, lat: 54.8515359564};

    var numberOfParts = 60;

    var tileWidth = (northEast.lng - southWest.lng) / numberOfParts;
    var tileHeight = (northEast.lat - southWest.lat) / numberOfParts;

    let heatmapPoints = [];

    for (var x = 0; x < numberOfParts; x++) {
      for (var y = 0; y < numberOfParts; y++) {
        let square_id = (x * numberOfParts + y).toString();

        if (squareToPrediction[square_id] == "1"){
          let lat = (southWest.lat + (tileHeight * (y+1)) + southWest.lat + (tileHeight * y)) / 2.0
          let lng = (southWest.lng + (tileWidth * (x+1)) + southWest.lng + (tileWidth * x)) / 2.0
          heatmapPoints.push(new google.maps.LatLng(lat, lng));
        }
      }
    }

    heatmap = new google.maps.visualization.HeatmapLayer({
      data: heatmapPoints,
      map: map,
    });

}

function toggleHeatmap() {
  heatmap.setMap(heatmap.getMap() ? null : map);
}

function toggleObservations(){
  var checkBox = document.getElementById("observationCheckbox");
  observations.forEach(marker => marker.setVisible(checkBox.checked == true));
}


function togglePredictions(){
  var checkBox = document.getElementById("predictionCheckbox");
  predictions.forEach(prediction => prediction.setMap(checkBox.checked == true ? null : map));
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
