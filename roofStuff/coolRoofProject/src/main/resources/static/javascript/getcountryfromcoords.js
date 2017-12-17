/** SRC:
 * https://gist.github.com/AmirHossein/92a0597b5f723b19c648 besser als meine :(
 */ 
// umrechnen von latlng auf coords
var latlng;
//latlng = new google.maps.LatLng(40.730885, -73.997383); // New York, US
latlng = new google.maps.LatLng(47.7666667,13.05); // Salzburg. AT
geocodeLatLng(latlng);



//Funktion zum herrausfinden des Landes aus einer Koordinate
// @latlng zusammengesetzte koordinate (siehe oben)
// keine rückgabe parameter, schreibt derzeit ergebnis in .innerHTML
// ich würde "countrycode" weiter verwenden, der sollte international sein 
function geocodeLatLng(latlng){

new google.maps.Geocoder().geocode({'latLng' : latlng}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
        if (results[1]) {
            var country = null, countryCode = null, city = null, cityAlt = null;
            var c, lc, component;
            for (var r = 0, rl = results.length; r < rl; r += 1) {
                var result = results[r];

                if (!city && result.types[0] === 'locality') {
                    for (c = 0, lc = result.address_components.length; c < lc; c += 1) {
                        component = result.address_components[c];

                        if (component.types[0] === 'locality') {
                            city = component.long_name;
                            break;
                        }
                    }
                }
                else if (!city && !cityAlt && result.types[0] === 'administrative_area_level_1') {
                    for (c = 0, lc = result.address_components.length; c < lc; c += 1) {
                        component = result.address_components[c];

                        if (component.types[0] === 'administrative_area_level_1') {
                            cityAlt = component.long_name;
                            break;
                        }
                    }
                } else if (!country && result.types[0] === 'country') {
                    country = result.address_components[0].long_name;
                    countryCode = result.address_components[0].short_name;
                }

                if (city && country) {
                    break;
                }
            }

            // todo Ausgabeformat/Ziel ändern
            console.log("City: " + city + ", City2: " + cityAlt + ", Country: " + country + ", Country Code: " + countryCode);
            document.getElementById('city').innerHTML = city;
            document.getElementById('cityAlt').innerHTML = cityAlt;
            document.getElementById('country').innerHTML = country;
            document.getElementById('countryCode').innerHTML = countryCode;
        }
    }
});
}