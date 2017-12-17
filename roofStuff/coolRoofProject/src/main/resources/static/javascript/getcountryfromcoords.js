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


// this function returns the continent for the @input countryCode of a country
function getContinentFromCountryCode(countryCode){
	// country to continent mapping from https://dev.maxmind.com/geoip/legacy/codes/country_continent/
	
	var cc = countryCode;
	var continent;
// generator http://jsfiddle.net/walkerneo/3NTpU/3/
	//EU,europe AS,Asia NA,Namerica AF,Africa oc,oceania
switch (cc) {
case 'A1':
	continent='--';
	break;
case 'A2':
	continent='--';
	break;
case 'AD':
	continent='EU';
	break;
case 'AE':
	continent='AS';
	break;
case 'AF':
	continent='AS';
	break;
case 'AG':
	continent='NA';
	break;
case 'AI':
	continent='NA';
	break;
case 'AL':
	continent='EU';
	break;
case 'AM':
	continent='AS';
	break;
case 'AN':
	continent='NA';
	break;
case 'AO':
	continent='AF';
	break;
case 'AP':
	continent='AS';
	break;
case 'AQ':
	continent='AN';
	break;
case 'AR':
	continent='SA';
	break;
case 'AS':
	continent='OC';
	break;
case 'AT':
	continent='EU';
	break;
case 'AU':
	continent='OC';
	break;
case 'AW':
	continent='NA';
	break;
case 'AX':
	continent='EU';
	break;
case 'AZ':
	continent='AS';
	break;
case 'BA':
	continent='EU';
	break;
case 'BB':
	continent='NA';
	break;
case 'BD':
	continent='AS';
	break;
case 'BE':
	continent='EU';
	break;
case 'BF':
	continent='AF';
	break;
case 'BG':
	continent='EU';
	break;
case 'BH':
	continent='AS';
	break;
case 'BI':
	continent='AF';
	break;
case 'BJ':
	continent='AF';
	break;
case 'BL':
	continent='NA';
	break;
case 'BM':
	continent='NA';
	break;
case 'BN':
	continent='AS';
	break;
case 'BO':
	continent='SA';
	break;
case 'BR':
	continent='SA';
	break;
case 'BS':
	continent='NA';
	break;
case 'BT':
	continent='AS';
	break;
case 'BV':
	continent='AN';
	break;
case 'BW':
	continent='AF';
	break;
case 'BY':
	continent='EU';
	break;
case 'BZ':
	continent='NA';
	break;
case 'CA':
	continent='NA';
	break;
case 'CC':
	continent='AS';
	break;
case 'CD':
	continent='AF';
	break;
case 'CF':
	continent='AF';
	break;
case 'CG':
	continent='AF';
	break;
case 'CH':
	continent='EU';
	break;
case 'CI':
	continent='AF';
	break;
case 'CK':
	continent='OC';
	break;
case 'CL':
	continent='SA';
	break;
case 'CM':
	continent='AF';
	break;
case 'CN':
	continent='AS';
	break;
case 'CO':
	continent='SA';
	break;
case 'CR':
	continent='NA';
	break;
case 'CU':
	continent='NA';
	break;
case 'CV':
	continent='AF';
	break;
case 'CX':
	continent='AS';
	break;
case 'CY':
	continent='AS';
	break;
case 'CZ':
	continent='EU';
	break;
case 'DE':
	continent='EU';
	break;
case 'DJ':
	continent='AF';
	break;
case 'DK':
	continent='EU';
	break;
case 'DM':
	continent='NA';
	break;
case 'DO':
	continent='NA';
	break;
case 'DZ':
	continent='AF';
	break;
case 'EC':
	continent='SA';
	break;
case 'EE':
	continent='EU';
	break;
case 'EG':
	continent='AF';
	break;
case 'EH':
	continent='AF';
	break;
case 'ER':
	continent='AF';
	break;
case 'ES':
	continent='EU';
	break;
case 'ET':
	continent='AF';
	break;
case 'EU':
	continent='EU';
	break;
case 'FI':
	continent='EU';
	break;
case 'FJ':
	continent='OC';
	break;
case 'FK':
	continent='SA';
	break;
case 'FM':
	continent='OC';
	break;
case 'FO':
	continent='EU';
	break;
case 'FR':
	continent='EU';
	break;
case 'FX':
	continent='EU';
	break;
case 'GA':
	continent='AF';
	break;
case 'GB':
	continent='EU';
	break;
case 'GD':
	continent='NA';
	break;
case 'GE':
	continent='AS';
	break;
case 'GF':
	continent='SA';
	break;
case 'GG':
	continent='EU';
	break;
case 'GH':
	continent='AF';
	break;
case 'GI':
	continent='EU';
	break;
case 'GL':
	continent='NA';
	break;
case 'GM':
	continent='AF';
	break;
case 'GN':
	continent='AF';
	break;
case 'GP':
	continent='NA';
	break;
case 'GQ':
	continent='AF';
	break;
case 'GR':
	continent='EU';
	break;
case 'GS':
	continent='AN';
	break;
case 'GT':
	continent='NA';
	break;
case 'GU':
	continent='OC';
	break;
case 'GW':
	continent='AF';
	break;
case 'GY':
	continent='SA';
	break;
case 'HK':
	continent='AS';
	break;
case 'HM':
	continent='AN';
	break;
case 'HN':
	continent='NA';
	break;
case 'HR':
	continent='EU';
	break;
case 'HT':
	continent='NA';
	break;
case 'HU':
	continent='EU';
	break;
case 'ID':
	continent='AS';
	break;
case 'IE':
	continent='EU';
	break;
case 'IL':
	continent='AS';
	break;
case 'IM':
	continent='EU';
	break;
case 'IN':
	continent='AS';
	break;
case 'IO':
	continent='AS';
	break;
case 'IQ':
	continent='AS';
	break;
case 'IR':
	continent='AS';
	break;
case 'IS':
	continent='EU';
	break;
case 'IT':
	continent='EU';
	break;
case 'JE':
	continent='EU';
	break;
case 'JM':
	continent='NA';
	break;
case 'JO':
	continent='AS';
	break;
case 'JP':
	continent='AS';
	break;
case 'KE':
	continent='AF';
	break;
case 'KG':
	continent='AS';
	break;
case 'KH':
	continent='AS';
	break;
case 'KI':
	continent='OC';
	break;
case 'KM':
	continent='AF';
	break;
case 'KN':
	continent='NA';
	break;
case 'KP':
	continent='AS';
	break;
case 'KR':
	continent='AS';
	break;
case 'KW':
	continent='AS';
	break;
case 'KY':
	continent='NA';
	break;
case 'KZ':
	continent='AS';
	break;
case 'LA':
	continent='AS';
	break;
case 'LB':
	continent='AS';
	break;
case 'LC':
	continent='NA';
	break;
case 'LI':
	continent='EU';
	break;
case 'LK':
	continent='AS';
	break;
case 'LR':
	continent='AF';
	break;
case 'LS':
	continent='AF';
	break;
case 'LT':
	continent='EU';
	break;
case 'LU':
	continent='EU';
	break;
case 'LV':
	continent='EU';
	break;
case 'LY':
	continent='AF';
	break;
case 'MA':
	continent='AF';
	break;
case 'MC':
	continent='EU';
	break;
case 'MD':
	continent='EU';
	break;
case 'ME':
	continent='EU';
	break;
case 'MF':
	continent='NA';
	break;
case 'MG':
	continent='AF';
	break;
case 'MH':
	continent='OC';
	break;
case 'MK':
	continent='EU';
	break;
case 'ML':
	continent='AF';
	break;
case 'MM':
	continent='AS';
	break;
case 'MN':
	continent='AS';
	break;
case 'MO':
	continent='AS';
	break;
case 'MP':
	continent='OC';
	break;
case 'MQ':
	continent='NA';
	break;
case 'MR':
	continent='AF';
	break;
case 'MS':
	continent='NA';
	break;
case 'MT':
	continent='EU';
	break;
case 'MU':
	continent='AF';
	break;
case 'MV':
	continent='AS';
	break;
case 'MW':
	continent='AF';
	break;
case 'MX':
	continent='NA';
	break;
case 'MY':
	continent='AS';
	break;
case 'MZ':
	continent='AF';
	break;
case 'NA':
	continent='AF';
	break;
case 'NC':
	continent='OC';
	break;
case 'NE':
	continent='AF';
	break;
case 'NF':
	continent='OC';
	break;
case 'NG':
	continent='AF';
	break;
case 'NI':
	continent='NA';
	break;
case 'NL':
	continent='EU';
	break;
case 'NO':
	continent='EU';
	break;
case 'NP':
	continent='AS';
	break;
case 'NR':
	continent='OC';
	break;
case 'NU':
	continent='OC';
	break;
case 'NZ':
	continent='OC';
	break;
case 'O1':
	continent='--';
	break;
case 'OM':
	continent='AS';
	break;
case 'PA':
	continent='NA';
	break;
case 'PE':
	continent='SA';
	break;
case 'PF':
	continent='OC';
	break;
case 'PG':
	continent='OC';
	break;
case 'PH':
	continent='AS';
	break;
case 'PK':
	continent='AS';
	break;
case 'PL':
	continent='EU';
	break;
case 'PM':
	continent='NA';
	break;
case 'PN':
	continent='OC';
	break;
case 'PR':
	continent='NA';
	break;
case 'PS':
	continent='AS';
	break;
case 'PT':
	continent='EU';
	break;
case 'PW':
	continent='OC';
	break;
case 'PY':
	continent='SA';
	break;
case 'QA':
	continent='AS';
	break;
case 'RE':
	continent='AF';
	break;
case 'RO':
	continent='EU';
	break;
case 'RS':
	continent='EU';
	break;
case 'RU':
	continent='EU';
	break;
case 'RW':
	continent='AF';
	break;
case 'SA':
	continent='AS';
	break;
case 'SB':
	continent='OC';
	break;
case 'SC':
	continent='AF';
	break;
case 'SD':
	continent='AF';
	break;
case 'SE':
	continent='EU';
	break;
case 'SG':
	continent='AS';
	break;
case 'SH':
	continent='AF';
	break;
case 'SI':
	continent='EU';
	break;
case 'SJ':
	continent='EU';
	break;
case 'SK':
	continent='EU';
	break;
case 'SL':
	continent='AF';
	break;
case 'SM':
	continent='EU';
	break;
case 'SN':
	continent='AF';
	break;
case 'SO':
	continent='AF';
	break;
case 'SR':
	continent='SA';
	break;
case 'ST':
	continent='AF';
	break;
case 'SV':
	continent='NA';
	break;
case 'SY':
	continent='AS';
	break;
case 'SZ':
	continent='AF';
	break;
case 'TC':
	continent='NA';
	break;
case 'TD':
	continent='AF';
	break;
case 'TF':
	continent='AN';
	break;
case 'TG':
	continent='AF';
	break;
case 'TH':
	continent='AS';
	break;
case 'TJ':
	continent='AS';
	break;
case 'TK':
	continent='OC';
	break;
case 'TL':
	continent='AS';
	break;
case 'TM':
	continent='AS';
	break;
case 'TN':
	continent='AF';
	break;
case 'TO':
	continent='OC';
	break;
case 'TR':
	continent='EU';
	break;
case 'TT':
	continent='NA';
	break;
case 'TV':
	continent='OC';
	break;
case 'TW':
	continent='AS';
	break;
case 'TZ':
	continent='AF';
	break;
case 'UA':
	continent='EU';
	break;
case 'UG':
	continent='AF';
	break;
case 'UM':
	continent='OC';
	break;
case 'US':
	continent='NA';
	break;
case 'UY':
	continent='SA';
	break;
case 'UZ':
	continent='AS';
	break;
case 'VA':
	continent='EU';
	break;
case 'VC':
	continent='NA';
	break;
case 'VE':
	continent='SA';
	break;
case 'VG':
	continent='NA';
	break;
case 'VI':
	continent='NA';
	break;
case 'VN':
	continent='AS';
	break;
case 'VU':
	continent='OC';
	break;
case 'WF':
	continent='OC';
	break;
case 'WS':
	continent='OC';
	break;
case 'YE':
	continent='AS';
	break;
case 'YT':
	continent='AF';
	break;
case 'ZA':
	continent='AF';
	break;
case 'ZM':
	continent='AF';
	break;
case 'ZW':
	continent='AF';
	break;
default:
	break;
}

return continent;
	
}