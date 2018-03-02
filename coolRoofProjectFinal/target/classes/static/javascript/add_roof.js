var map;
    var coordinates;

    function initMap() {
      map = new google.maps.Map(document.getElementById('googleMapsStyle'), {
        center: {
          lat: 47.78,
          lng: 13.05
        },
        zoom: 16,
        tilt: 0,
        // only show roadmap type of map, and disable ability to switch to other type
        mapTypeId: google.maps.MapTypeId.SATELLITE,
        mapTypeControl: false,
        panControl: false,
        rotateControl: false,
        streetViewControl: false
      });
      map.data.setControls(['Polygon']);
      map.data.setStyle({
        editable: true,
        draggable: true
      });
      bindDataLayerListeners(map.data);

      var input = document.getElementById('pac-input');
      var searchBox = new google.maps.places.SearchBox(input);
      map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

      // Bias the SearchBox results towards current map's viewport.
      map.addListener('bounds_changed', function() {
        searchBox.setBounds(map.getBounds());
      });

      var markers = [];
      // Listen for the event fired when the user selects a prediction and retrieve
      // more details for that place.
      searchBox.addListener('places_changed', function() {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
          return;
        }

        // Clear out the old markers.
        markers.forEach(function(marker) {
          marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function(place) {
          if (!place.geometry) {
            console.log("Returned place contains no geometry");
            return;
          }
          var icon = {
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(25, 25)
          };

          // Create a marker for each place.
          markers.push(new google.maps.Marker({
            map: map,
            icon: icon,
            title: place.name,
            position: place.geometry.location
          }));

          if (place.geometry.viewport) {
            // Only geocodes have viewport.
            bounds.union(place.geometry.viewport);
          } else {
            bounds.extend(place.geometry.location);
          }
        });
        map.fitBounds(bounds);
      });
      //loadPolygons(map);  //load saved data */
    }

    // Apply listeners to refresh the GeoJson display on a given data layer.
    function bindDataLayerListeners(dataLayer) {
      dataLayer.addListener('addfeature', savePolygon);
      dataLayer.addListener('removefeature', savePolygon);
    }

    function loadPolygons(map) {
      var data = JSON.parse(localStorage.getItem('geoData')); // this will be needed
      //alert(data);
      map.data.forEach(function(f) {
        map.data.remove(f);
      });
      map.data.addGeoJson(data) // and this
    }

    function savePolygon() {

      map.data.toGeoJson(function(json) {
        localStorage.setItem('geoData', JSON.stringify(json));
        initControls(json);
      });

      calcar();
    }

    function setProperties() {
      map.data.toGeoJson(function(json) {
        document.getElementById('roofPolygon').value = JSON.stringify(json);
        //alert(JSON.stringify(json));

        var split = coordinates.toString().split(",");

        geocodeLatLng(coordinates);
        
        document.getElementById('longitude').value = split[1].substring(1, split[1].length-1);
        document.getElementById('latitude').value = split[0].substring(1, split[0].length);
        document.getElementById('zoomFactor').value = map.getZoom();        
      });
    }

    function initControls(data) {
      var sel = document.getElementById('polyList');
      sel.options.length = 0;
      for (var i = 0; i < data.features.length; i++) {
        var opt = document.createElement('option');
        opt.innerHTML = data.features[i].geometry.type;

        opt.value = i;
        sel.appendChild(opt);
      }

      document.getElementById("btnDelete").disabled = (data.features.length === 0); //original content
      document.getElementById("btnDeleteAll").disabled = (data.features.length === 0);

      document.getElementById("btnDelete").onclick = function() {

        var selIdx = sel.options[sel.selectedIndex].value; //get poly index
        data.features.splice(parseInt(selIdx), 1);
        //reload
        localStorage.setItem('geoData', JSON.stringify(data));
        initControls(data);
        loadPolygons(map);
      };
      document.getElementById("btnDeleteAll").onclick = function() {
        localStorage.setItem('geoData', null);
        loadPolygons(map);
      };
    }

    function calcar() {
      var areasum = 0;
      //         var area = google.maps.geometry.spherical.computeArea(selectedShape.getPath());
      //         document.getElementById("test3").innerHTML = "Area =" + area.toFixed(2);
      map.data.forEach(function(feature) {
        if (feature.getGeometry().getType() == "Polygon") {
          var bounds = [];
          var polyBnds = new google.maps.LatLngBounds();

          feature.getGeometry().forEachLatLng(function(path) {
            bounds.push(path);

            latlngforDB = path;

            polyBnds.extend(path);
            coordinates = path;
          });
          
          var area = google.maps.geometry.spherical.computeArea(bounds);
 
          area = Math.round(area);
          areasum += area;
        }

      });
      document.getElementById('area').value = areasum;
      setProperties();

    }
    
    /** SRC:
      * https://gist.github.com/AmirHossein/92a0597b5f723b19c648 besser als meine :(
    */ 
    
    //Funktion zum herrausfinden des Landes aus einer Koordinate
    // @latlng zusammengesetzte koordinate (siehe oben)
    // keine rückgabe parameter, schreibt derzeit ergebnis in .innerHTML
    // ich würde "countrycode" weiter verwenden, der sollte international sein 
    function geocodeLatLng(latlng){

      new google.maps.Geocoder().geocode({'latLng' : latlng}, function(results, status) {
      
        if (status == google.maps.GeocoderStatus.OK) {
          var countryCode = null;
          
          if (results[1]) { 
            for (var r = 0; r < results.length; r += 1) {
              var result = results[r];
              if (result.types[0] === 'country') {
                countryCode = result.address_components[0].short_name;
              }
            }
          }
          document.getElementById('region').value = countryCode;
        }
      });
    }
    
