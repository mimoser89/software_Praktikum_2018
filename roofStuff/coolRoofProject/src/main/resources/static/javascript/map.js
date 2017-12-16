
    var map;
    
    
    window.onload = function () {
    	
    	alert("TEst");
    }
    
    
    
    
    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            center: { lat: -34.397, lng: 150.644 },
            zoom: 4,
            // only show roadmap type of map, and disable ability to switch to other type
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            mapTypeControl: false
        });
        map.data.setControls(['Polygon']);
        map.data.setStyle({
            editable: true,
            draggable: true
        });
        bindDataLayerListeners(map.data);
       
        loadPolygons(map);  //load saved data
        
        
    }
    // Apply listeners to refresh the GeoJson display on a given data layer.
    function bindDataLayerListeners(dataLayer) {
        dataLayer.addListener('addfeature', savePolygon);
        dataLayer.addListener('removefeature', savePolygon);
        //dataLayer.addListener('setgeometry', savePolygon);
    }
    function loadPolygons(map) {
        var data = JSON.parse(localStorage.getItem('geoData'));
        map.data.forEach(function (f) {
            map.data.remove(f);
        });
        map.data.addGeoJson(data)
    }
    function savePolygon() {
        map.data.toGeoJson(function (json) {
            localStorage.setItem('geoData', JSON.stringify(json));//Modify to save in db
            initControls(json);
        });
    }
    function initControls(data)
    {
        var sel = document.getElementById('polyList');
        sel.options.length = 0;
        for (var i = 0; i < data.features.length; i++) {
            var opt = document.createElement('option');
            opt.innerHTML = data.features[i].geometry.type;                        
            opt.value = i;
            sel.appendChild(opt);
        }
        document.getElementById("btnDelete").disabled = (data.features.length === 0); 
        document.getElementById("btnDelete").onclick = function(){
        	
//         	var area = data.features[i].geometry.spherical.computeArea(data.features[i].getPath());
//             document.getElementById("area").innerHTML = "Area =" + area.toFixed(2);
            var selIdx = sel.options[sel.selectedIndex].value; //get poly index 
            data.features.splice(parseInt(selIdx), 1);   
            //reload 
            localStorage.setItem('geoData', JSON.stringify(data));
            initControls(data);  
            loadPolygons(map);   
        };
    }
    
    function calcar() {
        var area = google.maps.geometry.spherical.computeArea(selectedShape.getPath());
        document.getElementById("test3").innerHTML = "Area =" + area.toFixed(2);
    }
    function createPolygons() {
    	var data = JSON.parse(localStorage.getItem('geoData'));
        for (var y = 0; y < data.features.length; y++) {
            var points = data.features[y];
            document.getElementById("test1").innerHTML = "How many Arrays? " + data.features.length.toFixed(2);
            document.getElementById("test2").innerHTML = "geometry " + points.getId;
            for (var z = 0; points.geometry.length; z++) {
                var pointsMore = points.geometry[z];
               
			}   
        }
    }
    
    initMap();
