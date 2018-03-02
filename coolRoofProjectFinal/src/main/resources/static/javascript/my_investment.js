//this function finds roof by ID
function findRoofById(id) {
	for(var i=0;i < roofList.length; i++) {
		if(id === roofList[i].roofId) {
			return roofList[i];
		}
	}
};

//this function updates the fields 
function updateFields() {
	
	for(var i = 0; i < investmentList.length; i++) {
		
		//get investment id
		var id = "investment_" + i;
		
		//select roof by id
		var selectedRoof = document.getElementById(id).value;
		
		//temp roof object
		var tempRoof = findRoofById(parseInt(selectedRoof));
		
		var idRegion = "region" + i;
		var idLongitude = "longitude" + i;
		var idLatitude = "latitude" + i;
		var idAge = "age" + i;
		var idCO2saved = "co2saved" + i;
		var idRtnOfInterests = "rtnOfInterests" + i;
		
		region[i] = tempRoof.region;
		longitude[i] = tempRoof.longitude;
		latitude[i] = tempRoof.latitude;
		age[i] = tempRoof.age;

		document.getElementById(idRegion).value = region[i];
		document.getElementById(idLongitude).value= parseFloat(longitude[i]);
		document.getElementById(idLatitude).value = parseFloat(latitude[i]);
		document.getElementById(idAge).value = parseFloat(age[i]);

	}
};
	
//this function inits the map to show selected investment
function initMap() {
		
	for(var i = 0; i < investmentList.length; i++) {
		
		var mapName = 'maps' + i;
		
		var investmentId = "investment_" + i;
		
		var selectedRoof = document.getElementById(investmentId).value;
		
		//get attributes for selectedMap
		longitude[i] = findRoofById(parseInt(selectedRoof)).longitude;
		latitude[i] = findRoofById(parseInt(selectedRoof)).latitude;
		zoomfactor[i] = findRoofById(parseInt(selectedRoof)).zoomFactor;
		jsonString[i] = findRoofById(parseInt(selectedRoof)).roofPolygon;
			
		map[i] = new google.maps.Map(document.getElementById(mapName), {
			center: { lat: parseFloat(latitude[i]), lng: parseFloat(longitude[i]) },
		    zoom: parseInt(zoomfactor[i]),
		    // only show roadmap type of map, and disable ability to switch to other type
		    mapTypeId: google.maps.MapTypeId.SATELLITE,
		    mapTypeControl: false,
		    tilt:0, 
	        streetViewControl: false
		    });
		
			//disable edit functions
			map[i].data.setStyle({
		        editable: false,
		        draggable: false
		    });
		
		//loads polygon
		loadPolygons(i, jsonString[i]);
	}
	      
};

//this function updates the map after each changed investment
function changeMap(changedMap) {
	
	var selectedRoof = document.getElementById(changedMap.id).value;
	
	//split to get map index
	var mapIndex = parseInt(changedMap.id.split("_")[1]);
	var mapName = 'maps' + mapIndex;
	
	//get attributes for selectedMap
	longitude[mapIndex] = findRoofById(parseInt(selectedRoof)).longitude;
	latitude[mapIndex] = findRoofById(parseInt(selectedRoof)).latitude;
	zoomfactor[mapIndex] = findRoofById(parseInt(selectedRoof)).zoomFactor;
	jsonString[mapIndex] = findRoofById(parseInt(selectedRoof)).roofPolygon;
	
	
	map[mapIndex] = new google.maps.Map(document.getElementById(mapName), {
        center: { lat: parseFloat(latitude[mapIndex]), lng: parseFloat(longitude[mapIndex]) },
        zoom: parseInt(zoomfactor[mapIndex]),
        // only show roadmap type of map, and disable ability to switch to other type
        mapTypeId: google.maps.MapTypeId.SATELLITE,
        mapTypeControl: false,
        tilt:0, 
        streetViewControl: false
    });
	
	//disable edit functions
	map[mapIndex].data.setStyle({
        editable: false,
        draggable: false
    });
    
	//loads polygon
    loadPolygons(mapIndex,jsonString[mapIndex]);
	
  	//update fields
    updateFields();
};

function loadPolygons(i, jsonString) {
	var data = JSON.parse(jsonString); 
	map[i].data.addGeoJson(data) 
};