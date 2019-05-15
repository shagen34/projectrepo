function searchByZip() {
    // convert zip code to latitude and longitude
    var zipCode = document.getElementById("zipBox").value;
    var apiKey = "uxnY22Y9CAXVIWVPpRlzaIbmaioOOrvF";
    var apiCall = "http://open.mapquestapi.com/geocoding/v1/address?key="+ apiKey + "&location=" + zipCode;
    //console.log(apiCall);

    //var zipData = $.get(apiCall);
    //var dataObj = jQuery.parseJSON(zipData);
    //console.log(zipData);
    
    fetch(apiCall)
        .then(res => res.json())
        .then(function(data) {
            var info = data.results;
            //console.log(info);
            var locationData = data.results[0].locations;
            // get lag/long for US zip code
            var coords = getLatLong(locationData);
            //console.log(coords);
            // get weather data 
            getWeatherData(coords);
        })
        .catch(e => console.log(e))
}

function getLatLong(infoArray){
    for(i = 0; i < infoArray.length; i++){
        if(infoArray[i].adminArea1 == "US"){
            //console.log("Found US zip code");
            return infoArray[i].latLng;
        }
        else {
            //console.log("looking for US zipcode")
        }
    }
    console.log("No US ZipCode found!")
}

function getWeatherData(coords){
    var lat = coords.lat;
    var long = coords.lng;
    var apiCall = "https://api.weather.gov/points/" + lat + "," + long;
    //console.log(apiCall);

    fetch(apiCall)
        .then(res => res.json())
        //.then(res => console.log(res))
        .then(function(data) {
            var grid = data.properties.forecastGridData;
            getForecast(grid);
            //console.log(grid);
        })
        .catch(e => console.log(e))
}

function getForecast(gridCall){
    fetch(gridCall)
    .then(res => res.json())
    //.then(res => console.log(res))
    .then(function(data) {
        var gridData = data.properties;
        //console.log(gridData);
        displayData(gridData);
    })
    .catch(e => console.log(e))
}

function displayData(data){
    // get current dates and min temps
    var dates = [];
    var minTemps = [];
    for(i=0; i < data.minTemperature.values.length; i++){
        var roundDeg = Math.round(data.minTemperature.values[i].value)
        var date = data.minTemperature.values[i].validTime;
        date = date.slice(5, 10);
        //console.log(date);
        minTemps.push(roundDeg);
        dates.push(date);
    }
    // get max temps
    var maxTemps = [];
    for(i=0; i < data.maxTemperature.values.length; i++){
        var roundDeg = Math.round(data.maxTemperature.values[i].value)
        maxTemps.push(roundDeg);
    }
    // get weather
    /*
    var weather = [];
    for(i=0; i < data.weather.values.length; i++){
        let day = data.weather[i].validTime;
        day = day.slice(5,10);
        if(day == date[i]){
            weather[i] = data.weather[i]
        }
    }
    */
    for(i=0; i < 7; i++){
        var currentDay = "day" + (i+1);
        var newCard = document.createElement("div");
        newCard.setAttribute("class", "card blue-grey darken-1");
        var cardContent = document.createElement("div");
        cardContent.setAttribute("class", "card-content white-text");
        var cardTitle = document.createElement("span");
        cardTitle.setAttribute("class", "card-title");
        var titleText = document.createTextNode(dates[i]);
        var cardText = document.createTextNode("Low: " + minTemps[i] + "°C" + '\n' + "High: " + maxTemps[i] + "°C");
        cardTitle.appendChild(titleText);
        cardContent.appendChild(cardTitle);
        cardContent.appendChild(cardText);
        newCard.appendChild(cardContent);
        var currDay = document.getElementById(currentDay)
        while(currDay.firstChild){
            currDay.removeChild(currDay.firstChild);
        }
        currDay.appendChild(newCard);
    }

    //document.getElementById()
    document.getElementById("resultCanvas").style.visibility = "visible";
}

function listenForEnter(e){
    e = e || window.event;
    if(e.keyCode == 13){
        document.getElementById('searchZipBtn').click();
        return false;
    }
    return true;
}