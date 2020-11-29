var locError = document.getElementById("localizationError");
var lat = document.getElementById("lat");
var lon = document.getElementById("lon");
var address = document.getElementById("address");
var getCoordinatesBtn = document.getElementById("getCoordinatesBtn");


function getLocationFromAddress(){
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address.value}, function(results, status) {

    if (status == google.maps.GeocoderStatus.OK) {
        var geoLat = results[0].geometry.location.lat()
        var geoLot = results[0].geometry.location.lng()
        lat.value = geoLat
        lon.value = geoLot
        }
    });
}

function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition, showError);
  } else {
    locError.innerHTML = "Geolocation is not supported by this browser.";
  }
}

function getReverseGeocodingData(lat, lng) {
    var latlng = new google.maps.LatLng(lat, lng);
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({ 'latLng': latlng }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            console.log(results);
            $("#address").val(results[0].formatted_address)
        }
    });
}

function showPosition(position) {
  lat.value = position.coords.latitude
  lon.value = position.coords.longitude

  getReverseGeocodingData(position.coords.latitude, position.coords.longitude)
}

function showError(error) {
  switch(error.code) {
    case error.PERMISSION_DENIED:
      locError = "User denied the request for Geolocation."
      break;
    case error.POSITION_UNAVAILABLE:
      locError = "Location information is unavailable."
      break;
    case error.TIMEOUT:
      locError = "The request to get user location timed out."
      break;
    case error.UNKNOWN_ERROR:
      locError = "An unknown error occurred."
      break;
  }
}

$(".amountslider").slider();
$(".amountslider").on("slide", function (slideEvt) {
    $("#" + slideEvt.target.id + "Val").text(slideEvt.value);
});
$(".amountslider").on("change", function (slideEvt) {
    $("#" + slideEvt.target.id + "Val").text(slideEvt.value.newValue);
});

$("#getCoordinatesBtn").on("click", function(event) {
  event.preventDefault();
  getLocation();
});

$("#report-button").on("click", function(event) {
  event.preventDefault();

  if (lat.value == "" || lon.value == "") {
     getLocationFromAddress()

     if (lat.value == "") {
        $("#error-adress").text("Proszę wprowadź poprawny adres lub pozwól aby aplikacja zlokalizowała Cię automatycznie")
     }

     console.log("Lat: " + lat.value)
  }
  else {
    $("#error-adress").empty()
    var $form = $("#report-form");
  var data = getFormData($form);

  var jsonData = {
      "geoLat": parseFloat(data['lat']),
      "geoLong": parseFloat(data['lon']),
      "source": 1,
      "timestamp": $.now(),
      "quantity": parseInt(data['amount']),
  }

  $.ajax({
    type: "POST",
    url: '/reports',
    data: JSON.stringify(jsonData),
    success: function (data) {
        $("#report-form").hide()
        $("#thank-you-report").show()
    },
    dataType: 'json',
    contentType: 'application/json'
  });
  }

});

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}